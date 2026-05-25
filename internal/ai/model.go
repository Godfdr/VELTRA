package ai

import (
	"time"
)

type SpendStory struct {
	ID        string    `bson:"_id,omitempty" json:"id"`
	UserID    string    `bson:"user_id" json:"user_id"`
	Category  string    `bson:"category" json:"category"`
	Insight   string    `bson:"insight" json:"insight"`
	Icon      string    `bson:"icon" json:"icon"`
	Timestamp time.Time `bson:"timestamp" json:"timestamp"`
}

type UserBehavior struct {
	UserID         string    `bson:"user_id" json:"user_id"`
	SpendingHabits []string  `bson:"spending_habits" json:"spending_habits"`
	RiskScore      float64   `bson:"risk_score" json:"risk_score"`
	LastUpdated    time.Time `bson:"last_updated" json:"last_updated"`
}
