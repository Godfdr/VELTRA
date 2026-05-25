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
	"github.com/Godfdr/VELTRA/internal/middleware"

	_ "github.com/Godfdr/VELTRA/docs" // Swagger docs generated folder
	swaggerFiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
)

// @title           VELTRA API Engine
// @version         1.0
// @description     High-performance core for VELTRA NFC routing, Ledgers, and AI banking.
// @host            localhost:8080
// @BasePath        /v1

func main() {
	// 1. Initialize Databases
	ctx, stop := signal.NotifyContext(context.Background(), os.Interrupt, syscall.SIGTERM)
	defer stop()

	postgresURL := os.Getenv("POSTGRES_URL")
	if postgresURL == "" {
		postgresURL = "postgres://veltra_admin:production_db_secure_password_2026@localhost:5432/veltra_ledger"
	}

	pgPool, err := database.NewPostgresPool(postgresURL)
	if err != nil {
		log.Printf("warning: failed to connect to postgres: %v", err)
	} else {
		defer pgPool.Close()
	}

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

	// 2. Initialize Repositories & Handlers
	ledgerRepo := ledger.NewRepository(pgPool)
	ledgerHandler := ledger.NewHandler(ledgerRepo)

	// 3. Setup Gin Engine
	r := gin.Default()

	// Global Middleware
	if redisClient != nil {
		r.Use(middleware.SecurityRateLimiter(redisClient, 100, 10*time.Second))
	}

	// Swagger UI - Blocked in Production
	if os.Getenv("APP_ENV") != "production" {
		log.Println("Swagger documentation available at http://localhost:8080/swagger/index.html 📄")
		r.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))
	}

	// API v1 Routes
	v1 := r.Group("/v1")
	{
		v1.GET("/health", func(c *gin.Context) {
			c.JSON(http.StatusOK, gin.H{"status": "Veltra Core API is Online ⚡"})
		})

		v1.POST("/payments/nfc-tap", ledgerHandler.HandleNFCTap)

		// Pockets
		v1.GET("/pockets", ledgerHandler.GetPockets)
		v1.POST("/pockets", ledgerHandler.CreatePocket)

		// Auth
		v1.POST("/auth/login", func(c *gin.Context) {
			c.JSON(http.StatusOK, gin.H{"token": "ey_mock_token_for_joshua", "user_id": "user_mock_001"})
		})
		v1.POST("/auth/signup", func(c *gin.Context) {
			c.JSON(http.StatusCreated, gin.H{"status": "User created successfully"})
		})

		// Merchant
		v1.GET("/merchant/inventory", ledgerHandler.GetInventory)
		v1.POST("/merchant/expenses", ledgerHandler.LogExpense)

		// Offline Wallet
		v1.POST("/ledger/reconcile", ledgerHandler.ReconcileOffline)
	}

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
	log.Println("Swagger documentation available at http://localhost:8080/swagger/index.html 📄")

	<-ctx.Done()
	log.Println("Shutting down gracefully...")

	shutdownCtx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	if err := srv.Shutdown(shutdownCtx); err != nil {
		log.Fatal("Server forced to shutdown: ", err)
	}

	log.Println("Server exited")
}
