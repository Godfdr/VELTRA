package ledger

import (
	"context"
	"errors"
	"fmt"
)

type OfflineReceipt struct {
	TransactionRef    string `json:"tx_ref"`
	UserID            string `json:"user_id"`
	MerchantID        string `json:"merchant_id"`
	Amount            int64  `json:"amount"`
	HardwareCounter   int64  `json:"hardware_counter"`
	PlatformCertID    string `json:"cert_id"`
	DeviceSignature   string `json:"device_sig"`
	PreviousBlockHash string `json:"prev_hash"`
}

// ReconcileOfflineReceipt processes a cryptographically signed offline transaction
func (r *Repository) ReconcileOfflineReceipt(ctx context.Context, receipt OfflineReceipt) error {
	tx, err := r.db.Begin(ctx)
	if err != nil {
		return err
	}
	defer tx.Rollback(ctx)

	// 1. Idempotency Check
	const insertAudit = `
		INSERT INTO transaction_ledgers (transaction_reference, sender_account_id, receiver_account_id, amount, currency, channel, status)
		VALUES ($1, $2, $3, $4, 'NGN', 'OFFLINE_TAP', 'SUCCESS')
		ON CONFLICT (transaction_reference) DO NOTHING
		RETURNING id;`

	var ledgerID string
	err = tx.QueryRow(ctx, insertAudit, receipt.TransactionRef, receipt.UserID, receipt.MerchantID, receipt.Amount).Scan(&ledgerID)
	if err != nil {
		// If conflict occurs, it means the merchant or user already uploaded this receipt.
		return nil
	}

	// 2. Monotonic Counter Sequence Validation
	const checkCounterQuery = `SELECT last_processed_counter FROM user_offline_certificates WHERE id = $1 FOR UPDATE`
	var lastCounter int64
	err = tx.QueryRow(ctx, checkCounterQuery, receipt.PlatformCertID).Scan(&lastCounter)
	if err != nil {
		return fmt.Errorf("invalid offline certificate: %w", err)
	}

	if receipt.HardwareCounter <= lastCounter {
		return errors.New("security anomaly: rollback or replay attack detected (counter regression)")
	}

	// 3. Balance Clearance and State Update
	const updateBalances = `
		UPDATE accounts SET balance = balance - $1 WHERE user_id = $2;
		UPDATE accounts SET balance = balance + $1 WHERE user_id = $3;
		UPDATE user_offline_certificates SET last_processed_counter = $4, current_spent = current_spent + $1 WHERE id = $5;`

	_, err = tx.Exec(ctx, updateBalances, receipt.Amount, receipt.UserID, receipt.MerchantID, receipt.HardwareCounter, receipt.PlatformCertID)
	if err != nil {
		return fmt.Errorf("failed to settle offline funds: %w", err)
	}

	return tx.Commit(ctx)
}
