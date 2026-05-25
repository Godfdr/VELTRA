package ledger

import (
	"net/http"
	"github.com/gin-gonic/gin"
)

type Handler struct {
	repo *Repository
}

func NewHandler(repo *Repository) *Handler {
	return &Handler{repo: repo}
}

// NFCPayload represents the incoming tap-to-pay request
type NFCPayload struct {
	SenderID  string `json:"sender_id" binding:"required" example:"usr_99823"`
	ReceiverID string `json:"receiver_id" binding:"required" example:"merch_1122"`
	Amount    int64  `json:"amount" binding:"required" example:"500000"` // 5,000.00 NGN in Kobo
}

// PaymentResponse represents the API response for a transaction
type PaymentResponse struct {
	TxID    string `json:"transaction_id" example:"tx_7718293"`
	Status  string `json:"status" example:"COMPLETED"`
	Message string `json:"message" example:"Transfer successful"`
}

// HandleNFCTap processes a physical device transaction
// @Summary      Process NFC Tap Payment
// @Description  Accepts sender and receiver details to perform an atomic ledger transfer.
// @Tags         Ledger
// @Accept       json
// @Produce      json
// @Param        payload body      NFCPayload  true  "NFC Tap Transaction Details"
// @Success      200     {object}  PaymentResponse
// @Failure      400     {object}  map[string]string "Invalid request payload"
// @Failure      500     {object}  map[string]string "Internal server error"
// @Router       /api/ledger/transfer [post]
func (h *Handler) HandleNFCTap(c *gin.Context) {
	var req NFCPayload
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := h.repo.ExecuteNFCTransfer(c.Request.Context(), req.SenderID, req.ReceiverID, req.Amount); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, PaymentResponse{
		TxID:    "tx_" + req.SenderID[:4],
		Status:  "COMPLETED",
		Message: "Transfer successful",
	})
}
