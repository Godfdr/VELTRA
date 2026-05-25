package crypto

import (
	"crypto/aes"
	"crypto/cipher"
	"encoding/base64"
	"errors"
	"fmt"
)

const (
	NonceSizeBytes   = 12
	AuthTagSizeBytes = 16
)

/**
 * DecryptNFCReceipt handles the over-the-wire decryption of authenticated NFC payloads.
 * Expected format: Base64([12-Byte Nonce] + [Variable Ciphertext] + [16-Byte Auth Tag])
 */
func DecryptNFCReceipt(base64Payload string, sharedSecretKey []byte) ([]byte, error) {
	if len(sharedSecretKey) != 32 {
		return nil, errors.New("crypto configuration failure: secret key must be exactly 32 bytes")
	}

	packedWireData, err := base64.StdEncoding.DecodeString(base64Payload)
	if err != nil {
		return nil, fmt.Errorf("invalid base64 transit format: %w", err)
	}

	if len(packedWireData) < (NonceSizeBytes + AuthTagSizeBytes) {
		return nil, errors.New("wire data discarded: packet size falls below minimal cryptographic boundaries")
	}

	nonce := packedWireData[:NonceSizeBytes]
	cipherTextWithTag := packedWireData[NonceSizeBytes:]

	block, err := aes.NewCipher(sharedSecretKey)
	if err != nil {
		return nil, fmt.Errorf("failed to allocate hardware block cipher: %w", err)
	}

	gcm, err := cipher.NewGCM(block)
	if err != nil {
		return nil, fmt.Errorf("failed to bind Galois Counter extensions: %w", err)
	}

	plainText, err := gcm.Open(nil, nonce, cipherTextWithTag, nil)
	if err != nil {
		return nil, fmt.Errorf("packet drop: wire payload authenticity check failed: %w", err)
	}

	return plainText, nil
}
