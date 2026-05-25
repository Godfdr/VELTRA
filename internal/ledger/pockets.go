package ledger

import (
	"context"
)

type Pocket struct {
	ID           string `json:"id"`
	UserID       string `json:"user_id"`
	Name         string `json:"name"`
	Type         string `json:"type"`
	Balance      int64  `json:"balance"`
	TargetAmount int64  `json:"target_amount"`
}

func (r *Repository) ListPockets(ctx context.Context, userID string) ([]Pocket, error) {
	const query = `SELECT id, user_id, name, type, balance, target_amount FROM pockets WHERE user_id = $1`
	rows, err := r.db.Query(ctx, query, userID)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	var pockets []Pocket
	for rows.Next() {
		var p Pocket
		if err := rows.Scan(&p.ID, &p.UserID, &p.Name, &p.Type, &p.Balance, &p.TargetAmount); err != nil {
			return nil, err
		}
		pockets = append(pockets, p)
	}
	return pockets, nil
}

func (r *Repository) CreatePocket(ctx context.Context, userID, name, pType string, target int64) error {
	const query = `INSERT INTO pockets (user_id, name, type, target_amount) VALUES ($1, $2, $3, $4)`
	_, err := r.db.Exec(ctx, query, userID, name, pType, target)
	return err
}
