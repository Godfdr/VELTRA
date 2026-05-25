package ledger

import (
	"context"
	"errors"
	"github.com/jackc/pgx/v5"
	"github.com/jackc/pgx/v5/pgxpool"
)

type Repository struct {
	db *pgxpool.Pool
}

func NewRepository(db *pgxpool.Pool) *Repository {
	return &Repository{db: db}
}

// ExecuteNFCTransfer moves funds between two accounts atomically
func (r *Repository) ExecuteNFCTransfer(ctx context.Context, senderID, receiverID string, amount int64) error {
	// Begin transactional context
	tx, err := r.db.Begin(ctx)
	if err != nil {
		return err
	}
	// Automatic rollback handler if function exits early with an error
	defer tx.Rollback(ctx)

	// 1. Debit the sender (with balance check validation)
	const debitQuery = `UPDATE accounts SET balance = balance - $1 WHERE id = $2 AND balance >= $1`
	cmdTag, err := tx.Exec(ctx, debitQuery, amount, senderID)
	if err != nil {
		return err
	}
	if cmdTag.RowsAffected() == 0 {
		return errors.New("insufficient funds or invalid sender")
	}

	// 2. Credit the receiver
	const creditQuery = `UPDATE accounts SET balance = balance + $1 WHERE id = $2`
	cmdTag, err = tx.Exec(ctx, creditQuery, amount, receiverID)
	if err != nil {
		return err
	}
	if cmdTag.RowsAffected() == 0 {
		return errors.New("invalid receiver account")
	}

	// 3. Log the transaction
	const logQuery = `INSERT INTO transactions (id, from_account_id, to_account_id, amount, type, status) VALUES (gen_random_uuid(), $1, $2, $3, 'NFC_TRANSFER', 'COMPLETED')`
	_, err = tx.Exec(ctx, logQuery, senderID, receiverID, amount)
	if err != nil {
		return err
	}

	// 4. Commit cleanly if all actions succeeded
	return tx.Commit(ctx)
}
