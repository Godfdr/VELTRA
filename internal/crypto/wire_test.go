package crypto

import (
	"crypto/aes"
	"crypto/cipher"
	"crypto/rand"
	"encoding/base64"
	"io"
	"testing"
)

func TestDecryptNFCReceipt_Success(t *testing.T) {
	sharedKey := []byte("this_is_a_32_byte_secret_key_v12") // Exactly 32 bytes
	plainText := `{"sender_id":"usr_123","amount":5000}`

	// 1. Simulate Client-Side Encryption
	block, err := aes.NewCipher(sharedKey)
	if err != nil {
		t.Fatalf("aes.NewCipher failed: %v", err)
	}
	gcm, err := cipher.NewGCM(block)
	if err != nil {
		t.Fatalf("cipher.NewGCM failed: %v", err)
	}

	nonce := make([]byte, NonceSizeBytes)
	if _, err := io.ReadFull(rand.Reader, nonce); err != nil {
		t.Fatal(err)
	}

	cipherTextWithTag := gcm.Seal(nil, nonce, []byte(plainText), nil)
	packedData := append(nonce, cipherTextWithTag...)
	base64Payload := base64.StdEncoding.EncodeToString(packedData)

	// 2. Execute Backend Decryption
	decrypted, err := DecryptNFCReceipt(base64Payload, sharedKey)
	if err != nil {
		t.Fatalf("Decryption failed: %v", err)
	}

	if string(decrypted) != plainText {
		t.Errorf("Mismatch! Expected %s, got %s", plainText, string(decrypted))
	}
}

func TestDecryptNFCReceipt_TamperDetection(t *testing.T) {
	sharedKey := []byte("this_is_a_32_byte_secret_key_v12")
	plainText := "secret_data"

	block, err := aes.NewCipher(sharedKey)
	if err != nil {
		t.Fatal(err)
	}
	gcm, err := cipher.NewGCM(block)
	if err != nil {
		t.Fatal(err)
	}
	nonce := make([]byte, NonceSizeBytes)
	io.ReadFull(rand.Reader, nonce)
	cipherTextWithTag := gcm.Seal(nil, nonce, []byte(plainText), nil)

	// Tamper with the ciphertext (flip one bit)
	cipherTextWithTag[0] ^= 0x01

	packedData := append(nonce, cipherTextWithTag...)
	base64Payload := base64.StdEncoding.EncodeToString(packedData)

	// Should fail authenticity check
	_, err = DecryptNFCReceipt(base64Payload, sharedKey)
	if err == nil {
		t.Error("Security failure: Tampered packet was NOT detected!")
	}
}

func TestDecryptNFCReceipt_InvalidKey(t *testing.T) {
	sharedKey := []byte("too_short")
	_, err := DecryptNFCReceipt("any_payload", sharedKey)
	if err == nil || err.Error() != "crypto configuration failure: secret key must be exactly 32 bytes" {
		t.Errorf("Expected invalid key error, got: %v", err)
	}
}
