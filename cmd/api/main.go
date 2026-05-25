package main

import (
	"context"
	"log"
	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/Godfdr/VELTRA/internal/database"
	"github.com/Godfdr/VELTRA/internal/ledger"
	"github.com/Godfdr/VELTRA/internal/ai"
)

func main() {
	// 1. Initialize Databases
	ctx, stop := signal.NotifyContext(context.Background(), os.Interrupt, syscall.SIGTERM)
	defer stop()

	postgresURL := os.Getenv("POSTGRES_URL")
	if postgresURL == "" {
		postgresURL = "postgres://veltra:veltra_pass@localhost:5432/veltra_ledger"
	}

	pgPool, err := database.NewPostgresPool(postgresURL)
	if err != nil {
		log.Fatalf("failed to connect to postgres: %v", err)
	}
	defer pgPool.Close()

	redisAddr := os.Getenv("REDIS_ADDR")
	if redisAddr == "" {
		redisAddr = "localhost:6379"
	}
	redisClient, err := database.NewRedisClient(redisAddr, "", 0)
	if err != nil {
		log.Printf("warning: failed to connect to redis: %v", err)
	} else {
		defer redisClient.Close()
	}

	mongoURI := os.Getenv("MONGO_URI")
	if mongoURI == "" {
		mongoURI = "mongodb://localhost:27017"
	}
	mongoClient, err := database.NewMongoClient(mongoURI)
	if err != nil {
		log.Printf("warning: failed to connect to mongodb: %v", err)
	} else {
		defer mongoClient.Disconnect(ctx)
	}

	// 2. Initialize Repositories
	ledgerRepo := ledger.NewRepository(pgPool)
	aiRepo := ai.NewRepository(mongoClient.Database("veltra_ai"))

	// 3. Setup Gin Engine
	r := gin.Default()

	// Health Check
	r.GET("/health", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"status": "Veltra Core API is Online ⚡"})
	})

	// Ledger Endpoints
	r.POST("/api/ledger/transfer", func(c *gin.Context) {
		var req struct {
			SenderID   string `json:"sender_id"`
			ReceiverID string `json:"receiver_id"`
			Amount     int64  `json:"amount"`
		}
		if err := c.ShouldBindJSON(&req); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}

		if err := ledgerRepo.ExecuteNFCTransfer(c.Request.Context(), req.SenderID, req.ReceiverID, req.Amount); err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, gin.H{"message": "Transfer successful"})
	})

	// AI Analytics Endpoints
	r.GET("/api/ai/stories/:userId", func(c *gin.Context) {
		userID := c.Param("userId")
		stories, err := aiRepo.GetUserStories(c.Request.Context(), userID)
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}
		c.JSON(http.StatusOK, stories)
	})

	// 4. Start Server
	srv := &http.Server{
		Addr:    ":8080",
		Handler: r,
	}

	go func() {
		if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Fatalf("listen: %s\n", err)
		}
	}()

	log.Println("VELTRA API Server started on :8080 🚀")

	<-ctx.Done()
	log.Println("Shutting down gracefully...")

	shutdownCtx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	if err := srv.Shutdown(shutdownCtx); err != nil {
		log.Fatal("Server forced to shutdown: ", err)
	}

	log.Println("Server exited")
}
