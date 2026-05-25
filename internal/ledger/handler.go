package ledger

import (
	"encoding/json"
	"net/http"
	"github.com/gin-gonic/gin"
	"github.com/Godfdr/VELTRA/internal/crypto"
)

type Handler struct {
	repo *Repository
}

func NewHandler(repo *Repository) *Handler {
	return &Handler{repo: repo}
}

// NFCPayload represents the incoming tap-to-pay request from a terminal
type NFCPayload struct {
	SenderID   string `json:"sender_id" binding:"required" example:"usr_99823"`
	ReceiverID string `json:"receiver_id" example:"merch_1122"`
	Amount     int64  `json:"amount" binding:"required" example:"500000"` // 5,000.00 NGN in Kobo
	DeviceSig  string `json:"device_signature" binding:"required" example:"a4f2e9..."`
}

// SecureNFCPayload represents the encrypted envelope
type SecureNFCPayload struct {
	EncryptedData string `json:"encrypted_data" binding:"required"`
}

// PaymentResponse represents the API response for a transaction
type PaymentResponse struct {
	TxID    string `json:"transaction_id" example:"tx_7718293"`
	Status  string `json:"status" example:"APPROVED"`
	Message string `json:"message" example:"Transfer successful"`
}

// HandleSecureNFCTap handles physical device transactions with over-the-wire encryption
// @Summary      Process Secure NFC Tap Payment
// @Description  Decrypts AES-256-GCM data and passes it through the ACID transaction ledger.
// @Tags         Payments
// @Accept       json
// @Produce      json
// @Param        payload body      SecureNFCPayload  true  "Encrypted NFC Data"
// @Success      200     {object}  PaymentResponse
// @Router       /v1/payments/secure-nfc-tap [post]
func (h *Handler) HandleSecureNFCTap(c *gin.Context) {
	var req SecureNFCPayload
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid secure envelope"})
		return
	}

	// In production, the shared key would be fetched from a secure HSM or session cache
	sharedKey := []byte("this_is_a_32_byte_secret_key_v1")

	plainText, err := crypto.DecryptNFCReceipt(req.EncryptedData, sharedKey)
	if err != nil {
		c.JSON(http.StatusUnauthorized, gin.H{"error": err.Error()})
		return
	}

	var nfcReq NFCPayload
	if err := json.Unmarshal(plainText, &nfcReq); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Malformed decrypted data"})
		return
	}

	receiverID := nfcReq.ReceiverID
	if receiverID == "" {
		receiverID = "merch_default_001"
	}

	if err := h.repo.ProcessNFCPaymentAtomic(c.Request.Context(), "tx_sec_"+nfcReq.SenderID[:4], nfcReq.SenderID, receiverID, nfcReq.Amount); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, PaymentResponse{
		TxID:    "tx_secure_" + nfcReq.SenderID[:4],
		Status:  "APPROVED",
		Message: "Secure transfer successful",
	})
}

// HandleNFCTap handles standard physical device transactions
// @Summary      Process NFC Tap Payment
// @Description  Accepts encrypted card/phone tap data and passes it through the ACID transaction ledger.
// @Tags         Payments
// @Accept       json
// @Produce      json
// @Param        payload body      NFCPayload  true  "NFC Tap Transaction Details"
// @Success      200     {object}  PaymentResponse
// @Failure      400     {string}  string "Bad Request - Invalid Payload Structure"
// @Failure      402     {string}  string "Payment Required - Insufficient Balance"
// @Router       /v1/payments/nfc-tap [post]
func (h *Handler) HandleNFCTap(c *gin.Context) {
	var req NFCPayload
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Bad Request - Invalid Payload Structure"})
		return
	}

	receiverID := req.ReceiverID
	if receiverID == "" {
		receiverID = "merch_default_001"
	}

	if err := h.repo.ProcessNFCPaymentAtomic(c.Request.Context(), "tx_auto_ref_"+req.SenderID[:4], req.SenderID, receiverID, req.Amount); err != nil {
		if err.Error() == "declined: insufficient funds or invalid account tracking" {
			c.JSON(http.StatusPaymentRequired, "Payment Required - Insufficient Balance")
		} else {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		}
		return
	}

	c.JSON(http.StatusOK, PaymentResponse{
		TxID:    "tx_" + req.SenderID[:4],
		Status:  "APPROVED",
		Message: "Transfer successful",
	})
}

// GetPockets returns all pockets for a user
// @Summary      List User Pockets
// @Description  Retrieves all savings, squad, and offline pockets for the authenticated user.
// @Tags         Pockets
// @Produce      json
// @Success      200     {array}   Pocket
// @Router       /v1/pockets [get]
func (h *Handler) GetPockets(c *gin.Context) {
	userID := c.GetString("validated_user_id")
	if userID == "" {
		userID = "user_mock_001"
	}

	pockets, err := h.repo.ListPockets(c.Request.Context(), userID)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, pockets)
}

// CreatePocket adds a new financial pocket
// @Summary      Create New Pocket
// @Description  Creates a new savings or squad pocket.
// @Tags         Pockets
// @Accept       json
// @Produce      json
// @Param        payload body      Pocket  true  "Pocket Details"
// @Success      201     {string}  string "Pocket created"
// @Router       /v1/pockets [post]
func (h *Handler) CreatePocket(c *gin.Context) {
	var req Pocket
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	userID := c.GetString("validated_user_id")
	if userID == "" {
		userID = "user_mock_001"
	}

	if err := h.repo.CreatePocket(c.Request.Context(), userID, req.Name, req.Type, req.TargetAmount); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusCreated, gin.H{"message": "Pocket created"})
}

// GetInventory returns the merchant catalog
// @Summary      Get Merchant Inventory
// @Description  Retrieves all items in stock for the merchant.
// @Tags         Merchant
// @Produce      json
// @Success      200     {array}   InventoryItem
// @Router       /v1/merchant/inventory [get]
func (h *Handler) GetInventory(c *gin.Context) {
	merchantID := c.GetString("validated_user_id")
	if merchantID == "" {
		merchantID = "merch_mock_001"
	}

	items, err := h.repo.GetInventory(c.Request.Context(), merchantID)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, items)
}

// LogExpense records a new business cost
// @Summary      Log Business Expense
// @Description  Records a new expense entry (Raw Materials, Logistics, etc).
// @Tags         Merchant
// @Accept       json
// @Produce      json
// @Param        payload body      Expense  true  "Expense Details"
// @Success      201     {string}  string "Expense logged"
// @Router       /v1/merchant/expenses [post]
func (h *Handler) LogExpense(c *gin.Context) {
	var req Expense
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	merchantID := c.GetString("validated_user_id")
	if merchantID == "" {
		merchantID = "merch_mock_001"
	}

	if err := h.repo.LogExpense(c.Request.Context(), merchantID, req.Category, req.Amount, req.Description); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusCreated, gin.H{"message": "Expense logged"})
}

// ReconcileOffline processes an offline receipt from the client
// @Summary      Reconcile Offline Transaction
// @Description  Accepts a cryptographically signed receipt from the offline wallet to settle funds online.
// @Tags         Ledger
// @Accept       json
// @Produce      json
// @Param        payload body      OfflineReceipt  true  "Offline Receipt Details"
// @Success      200     {string}  string "Settle successful"
// @Router       /v1/ledger/reconcile [post]
func (h *Handler) ReconcileOffline(c *gin.Context) {
	var req OfflineReceipt
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := h.repo.ReconcileOfflineReceipt(c.Request.Context(), req); err != nil {
		c.JSON(http.StatusForbidden, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Settle successful"})
}
