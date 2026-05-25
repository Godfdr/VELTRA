package ledger

import (
	"time"
)

type Account struct {
	ID        string    `json:"id"`
	UserID    string    `json:"user_id"`
	Balance   int64     `json:"balance"` // amount in minor units (e.g. kobo for NGN)
	Currency  string    `json:"currency"`
	CreatedAt time.Time `json:"created_at"`
	UpdatedAt time.Time `json:"updated_at"`
}

type Transaction struct {
	ID            string    `json:"id"`
	FromAccountID string    `json:"from_account_id"`
	ToAccountID   string    `json:"to_account_id"`
	Amount        int64     `json:"amount"`
	Currency      string    `json:"currency"`
	Type          string    `json:"type"` // e.g. "NFC_TRANSFER", "P2P", "MERCHANT_PAY"
	Status        string    `json:"status"`
	CreatedAt     time.Time `json:"created_at"`
}
