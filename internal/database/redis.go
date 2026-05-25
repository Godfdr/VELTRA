package database

import (
	"context"
	"fmt"
	"github.com/redis/go-redis/v9"
)

// NewRedisClient initializes the Redis client for caching
func NewRedisClient(addr string, password string, db int) (*redis.Client, error) {
	ctx := context.Background()

	client := redis.NewClient(&redis.Options{
		Addr:     addr,
		Password: password, // no password set
		DB:       db,       // use default DB
	})

	// Ping to verify connection
	if err := client.Ping(ctx).Err(); err != nil {
		return nil, fmt.Errorf("redis ping failed: %w", err)
	}

	return client, nil
}
