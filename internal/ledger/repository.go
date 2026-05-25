package ledger

import (
	"context"
	"errors"
	"github.com/jackc/pgx/v5/pgxpool"
)

type Repository struct {
	db *pgxpool.Pool
}

func NewRepository(db *pgxpool.Pool) *Repository {
	return &Repository{db: db}
}

// ProcessNFCPaymentAtomic moves funds between two accounts atomically inside the SQL engine
func (r *Repository) ProcessNFCPaymentAtomic(ctx context.Context, txRef, senderID, receiverID string, amount int64) error {
	tx, err := r.db.Begin(ctx)
	if err != nil {
		return err
	}
	// Automatic rollback handler if function exits early with an error
	defer tx.Rollback(ctx)

	// Single statement mutation checks balance limits atomically inside SQL engine
	const debitQuery = `UPDATE accounts SET balance = balance - $1 WHERE id = $2 AND balance >= $1`
	cmdTag, err := tx.Exec(ctx, debitQuery, amount, senderID)
	if err != nil {
		return err
	}
	if cmdTag.RowsAffected() == 0 {
		return errors.New("declined: insufficient funds or invalid account tracking")
	}

	// 2. Credit the receiver
	const creditQuery = `UPDATE accounts SET balance = balance + $1 WHERE id = $2`
	cmdTag, err = tx.Exec(ctx, creditQuery, amount, receiverID)
	if err != nil {
		return err
	}
	if cmdTag.RowsAffected() == 0 {
		return errors.New("failed to process destination credit entry")
	}

	// 3. Log the transaction (audit trail)
	const logQuery = `
		INSERT INTO transaction_ledgers (transaction_reference, sender_account_id, receiver_account_id, amount, currency, channel)
		VALUES ($1, $2, $3, $4, 'NGN', 'NFC_TAP')`
	_, err = tx.Exec(ctx, logQuery, txRef, senderID, receiverID, amount)
	if err != nil {
		return errors.New("failed to commit audit trail signature")
	}

	// 4. Commit cleanly if all actions succeeded
	return tx.Commit(ctx)
}
