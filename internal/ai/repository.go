package ai

import (
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/bson"
)

type Repository struct {
	collection *mongo.Collection
}

func NewRepository(db *mongo.Database) *Repository {
	return &Repository{
		collection: db.Collection("spend_stories"),
	}
}

func (r *Repository) SaveSpendStory(ctx context.Context, story SpendStory) error {
	_, err := r.collection.InsertOne(ctx, story)
	return err
}

func (r *Repository) GetUserStories(ctx context.Context, userID string) ([]SpendStory, error) {
	cursor, err := r.collection.Find(ctx, bson.M{"user_id": userID})
	if err != nil {
		return nil, err
	}
	defer cursor.Close(ctx)

	var stories []SpendStory
	if err = cursor.All(ctx, &stories); err != nil {
		return nil, err
	}

	return stories, nil
}
