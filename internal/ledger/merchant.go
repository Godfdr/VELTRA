package ledger

import (
	"context"
)

type InventoryItem struct {
	ID           string `json:"id"`
	MerchantID   string `json:"merchant_id"`
	Name         string `json:"name"`
	StockCount   int    `json:"stock_count"`
	CostPrice    int64  `json:"cost_price"`
	SellingPrice int64  `json:"selling_price"`
}

type Expense struct {
	ID          string `json:"id"`
	MerchantID  string `json:"merchant_id"`
	Category    string `json:"category"`
	Amount      int64  `json:"amount"`
	Description string `json:"description"`
}

func (r *Repository) GetInventory(ctx context.Context, merchantID string) ([]InventoryItem, error) {
	const query = `SELECT id, merchant_id, name, stock_count, cost_price, selling_price FROM inventory_items WHERE merchant_id = $1`
	rows, err := r.db.Query(ctx, query, merchantID)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	var items []InventoryItem
	for rows.Next() {
		var i InventoryItem
		if err := rows.Scan(&i.ID, &i.MerchantID, &i.Name, &i.StockCount, &i.CostPrice, &i.SellingPrice); err != nil {
			return nil, err
		}
		items = append(items, i)
	}
	return items, nil
}

func (r *Repository) LogExpense(ctx context.Context, merchantID, category string, amount int64, desc string) error {
	const query = `INSERT INTO business_expenses (merchant_id, category, amount, description) VALUES ($1, $2, $3, $4)`
	_, err := r.db.Exec(ctx, query, merchantID, category, amount, desc)
	return err
}
