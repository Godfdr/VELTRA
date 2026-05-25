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

	_ "github.com/Godfdr/VELTRA/docs" // Swagger docs generated folder
	swaggerFiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
)

// @title           VELTRA API Engine
// @version         1.0
// @description     High-performance core for VELTRA NFC routing, Ledgers, and AI banking.
// @host            localhost:8080
// @BasePath        /

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
		log.Printf("warning: failed to connect to postgres: %v", err)
	} else {
		defer pgPool.Close()
	}

	// 2. Initialize Repositories & Handlers
	ledgerRepo := ledger.NewRepository(pgPool)
	ledgerHandler := ledger.NewHandler(ledgerRepo)

	// AI Repo placeholder (Mongo check omitted for speed if not running)
	// aiRepo := ai.NewRepository(mongoClient.Database("veltra_ai"))

	// 3. Setup Gin Engine
	r := gin.Default()

	// Swagger UI
	r.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))

	// Health Check
	r.GET("/health", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"status": "Veltra Core API is Online ⚡"})
	})

	// Ledger Endpoints
	r.POST("/api/ledger/transfer", ledgerHandler.HandleNFCTap)

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
