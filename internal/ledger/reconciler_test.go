package ledger

import (
	"context"
	"golang.org/x/sync/errgroup"
	"testing"
	"time"

	"github.com/jackc/pgx/v5/pgxpool"
)

// TestConcurrency_ZeroRaceDeadlocks simulates a high-intensity transaction flood
// where a device attempts to fire multiple offline settlements concurrently.
func TestConcurrency_ZeroRaceDeadlocks(t *testing.T) {
	ctx := context.Background()
	// Initialize a local test pool pointing to your Docker Compose instance
	pool, err := pgxpool.New(ctx, "postgres://veltra_admin:production_db_secure_password_2026@localhost:5432/veltra_ledger")
	if err != nil {
		t.Skip("Postgres container offline")
	}
	defer pool.Close()

	if err := pool.Ping(ctx); err != nil {
		t.Skip("Postgres container offline (ping failed)")
	}

	// Seed variables for isolated test context execution
	userID := "00000000-0000-0000-0000-000000000001"
	merchantID := "00000000-0000-0000-0000-000000000002"
	certID := "00000000-0000-0000-0000-000000000003"

	// Reset state for validation loop tracking
	_, _ = pool.Exec(ctx, "INSERT INTO users (id, email, phone_number) VALUES ($1, 'test_user@veltra.io', '+2348000000001') ON CONFLICT DO NOTHING", userID)
	_, _ = pool.Exec(ctx, "INSERT INTO users (id, email, phone_number) VALUES ($1, 'test_merch@veltra.io', '+2348000000002') ON CONFLICT DO NOTHING", merchantID)
	_, _ = pool.Exec(ctx, "INSERT INTO accounts (user_id, balance, currency) VALUES ($1, 0, 'NGN') ON CONFLICT DO NOTHING", userID)
	_, _ = pool.Exec(ctx, "INSERT INTO accounts (user_id, balance, currency) VALUES ($1, 0, 'NGN') ON CONFLICT DO NOTHING", merchantID)

	_, _ = pool.Exec(ctx, "UPDATE accounts SET balance = 500000 WHERE user_id = $1", userID)
	_, _ = pool.Exec(ctx, "UPDATE accounts SET balance = 0 WHERE user_id = $1", merchantID)
	_, _ = pool.Exec(ctx, "INSERT INTO user_offline_certificates (id, user_id, max_limit, device_public_key, expires_at) VALUES ($1, $2, 20000, 'test_key', '2026-12-31') ON CONFLICT DO NOTHING", certID, userID)
	_, _ = pool.Exec(ctx, "UPDATE user_offline_certificates SET last_processed_counter = 0 WHERE id = $1", certID)

	var g errgroup.Group
	concurrentWorkers := 50 // 50 separate execution streams attempting concurrent mutation

	// Chaos Engine Loop
	for i := 1; i <= concurrentWorkers; i++ {
		workerCounter := int64(i)
		// Use a high-precision timestamp to prevent reference collision in rapid loop
		txRef := "chaos_tx_ref_" + time.Now().Format("150405.000000") + "_" + string(rune(65+i))

		g.Go(func() error {
			tx, err := pool.Begin(ctx)
			if err != nil {
				return err
			}
			defer tx.Rollback(ctx)

			// 1. Enforce Atomic Ledger Check via unique reference key paths
			const query = `
				INSERT INTO transaction_ledgers (transaction_reference, sender_account_id, receiver_account_id, amount, currency, channel, status)
				VALUES ($1, $2, $3, 1000, 'NGN', 'OFFLINE_TAP', 'SUCCESS') ON CONFLICT DO NOTHING RETURNING id;`

			var ledgerID string
			err = tx.QueryRow(ctx, query, txRef, userID, merchantID).Scan(&ledgerID)
			if err != nil {
				return nil // Safely ignored if collision occurred
			}

			// 2. Validate counter increments to isolate step sequence regressions
			var lastCounter int64
			err = tx.QueryRow(ctx, "SELECT last_processed_counter FROM user_offline_certificates WHERE id = $1 FOR UPDATE", certID).Scan(&lastCounter)
			if err != nil {
				return err
			}

			if workerCounter <= lastCounter {
				return nil // Correctly intercepted sequencing out-of-order blocks
			}

			// 3. Mutate balances inline atomically
			_, err = tx.Exec(ctx, "UPDATE accounts SET balance = balance - 1000 WHERE user_id = $1", userID)
			_, err = tx.Exec(ctx, "UPDATE accounts SET balance = balance + 1000 WHERE user_id = $1", merchantID)
			_, err = tx.Exec(ctx, "UPDATE user_offline_certificates SET last_processed_counter = $1 WHERE id = $2", workerCounter, certID)

			return tx.Commit(ctx)
		})
	}

	// Wait for all processing routines to clear memory execution tracks
	if err := g.Wait(); err != nil {
		t.Fatalf("Chaos engine failed with error: %v", err)
	}

	// Verify ledger state audit consistency values match execution expectations exactly
	var finalSenderBalance int64
	var finalMerchBalance int64
	_ = pool.QueryRow(ctx, "SELECT balance FROM accounts WHERE user_id = $1", userID).Scan(&finalSenderBalance)
	_ = pool.QueryRow(ctx, "SELECT balance FROM accounts WHERE user_id = $1", merchantID).Scan(&finalMerchBalance)

	expectedRemaining := int64(500000 - (50 * 1000))
	if finalSenderBalance != expectedRemaining {
		t.Errorf("Balance inconsistency detected! Expected %d, got %d", expectedRemaining, finalSenderBalance)
	}

	if finalMerchBalance != 50000 {
		t.Errorf("Merchant balance inconsistency! Expected 50000, got %d", finalMerchBalance)
	}

	t.Logf("Chaos test finalized. Remaining Sender Wallet Units: %d minor units. Merchant Units: %d", finalSenderBalance, finalMerchBalance)
}

// TestCounterReplayAttack_Rejection confirms that submitting an older or equal counter
// is violently rejected by the database logic layer.
func TestCounterReplayAttack_Rejection(t *testing.T) {
	ctx := context.Background()
	pool, err := pgxpool.New(ctx, "postgres://veltra_admin:production_db_secure_password_2026@localhost:5432/veltra_ledger")
	if err != nil {
		t.Skip("Postgres container offline")
	}
	defer pool.Close()

	if err := pool.Ping(ctx); err != nil {
		t.Skip("Postgres container offline (ping failed)")
	}

	certID := "00000000-0000-0000-0000-000000000003"

	// Set last processed counter to 10
	_, _ = pool.Exec(ctx, "UPDATE user_offline_certificates SET last_processed_counter = 10 WHERE id = $1", certID)

	repo := NewRepository(pool)

	// Attempt to replay counter 5 (which is < 10)
	receipt := OfflineReceipt{
		TransactionRef:  "replay_attack_ref",
		UserID:          "00000000-0000-0000-0000-000000000001",
		MerchantID:      "00000000-0000-0000-0000-000000000002",
		Amount:          1000,
		HardwareCounter: 5,
		PlatformCertID:  certID,
	}

	err = repo.ReconcileOfflineReceipt(ctx, receipt)
	if err == nil {
		t.Error("Replay attack was NOT rejected! Logic failed to detect counter regression.")
	} else if err.Error() != "security anomaly: rollback or replay attack detected (counter regression)" {
		t.Errorf("Unexpected error message: %v", err)
	} else {
		t.Log("Replay attack successfully blocked by monotonic counter validation. ✅")
	}
}
