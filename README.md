# VELTRA - Premium NFC Fintech Payment Platform ⚡

<div align="center">

![VELTRA Logo](https://img.shields.io/badge/VELTRA-Premium%20NFC%20Payments-00D4FF?style=for-the-badge)
![Go](https://img.shields.io/badge/Go-1.26+-00ADD8?style=flat-square&logo=go)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0+-7F52FF?style=flat-square&logo=kotlin)
![Architecture](https://img.shields.io/badge/Architecture-Hybrid%20Database-orange?style=flat-square)
![Status](https://img.shields.io/badge/Status-v1.2%20Pro%20Build-brightgreen?style=for-the-badge)

**High-fidelity, luxury fintech ecosystem redefining digital payments with sub-1.5s transactions, social banking, and offline reliability.**

</div>

---

## 🎯 Overview

VELTRA (branded as **Zeltra**) is a **next-generation fintech platform** engineered for the African and global markets. By combining a high-performance **Go backend** with a luxury **Android experience**, VELTRA delivers more than just payments—it provides a complete financial ecosystem for both consumers and businesses.

---

## 🚀 Key Features & Functionalities

### 🛡️ Core Payments & Security
*   **Tap & Pay (NFC)**: Contactless payments with circular ripple animations and haptic feedback.
*   **Instant Pay**: Biometric shortcut (Long-press dashboard button) for immediate scanning.
*   **Hardware Secured Offline Wallet**: A "Reserve & Lock" mechanism using cryptographically signed tokens.
*   **Disposable Burner Cards**: Generate one-time-use virtual cards with animated "mint" and "burn" effects.
*   **Ghost Mode**: A 3-finger gesture that instantly blurs all sensitive financial data for total privacy.
*   **Biometric Security**: Face/Fingerprint integration for every critical action.

### 📊 Intelligence & Analytics
*   **Spend Story Analytics**: Weekly summaries presented as interactive, animated stories with category icons.
*   **AI Goal Date Prediction**: "The Fortune Teller" engine predicting exactly when you'll hit your savings goals.
*   **Auto-Budgeting Guardian**: Real-time spending guardrails for Transport, Dining, and Lifestyle with automated warnings.
*   **Financial Health Score**: Real-time tracker gamifying spending and saving habits.
*   **V-Points Loyalty**: Integrated loyalty system rewarding frequent users with digital assets.

### 💼 Veltra for Business (Merchant Mode)
*   **Phone-as-POS**: Transform any Android device into a terminal to collect NFC payments instantly.
*   **Business Dashboard**: Tracks Sales, Capital, and Net Profit/Loss (P/L) in real-time.
*   **Advanced Inventory**: Stock tracking with unit-level profit analysis (Cost Price vs Selling Price).
*   **Professional Invoicing**: Client management and invoice generation directly from the app.
*   **Business Expense Categorization**: Smart logging for Raw Materials and Logistics.
*   **Merchant Notifications**: Live webhook settlement signals and business alerts.

### 🤝 Social Banking
*   **Squad Pockets**: Joint savings goals with real-time avatars and glowing progress bars.
*   **Spot a Friend (Ping)**: Social money requests with high-fidelity notification cards.
*   **Group Bill Splitting**: Intelligent split logic with recipient toggles (Reimburse Payer or Pay Merchant).
*   **Global Remittance**: Real-time currency converter with live API integration and 1.5% service fee logic.

---

## 🏗️ Tech Stack & Architecture

### Hybrid Database Architecture (Backend)
Veltra utilizes a distributed, multi-engine database strategy for maximum reliability:
*   **PostgreSQL 16 (Core Ledger)**: ACID-compliant primary database for immutable records using `pgx/v5`.
*   **MongoDB 7.0 (AI & Analytics)**: NoSQL engine for high-velocity behavioral data and AI logs.
*   **Redis 7.2 (Speed Layer)**: In-memory cache for sub-millisecond transaction approvals and rate limiting.
*   **SQLite (Offline Sync)**: Embedded storage for secured offline tokens on the mobile device.

### Go Backend (High-Performance API)
*   **Gin Framework**: High-throughput HTTP routing with structured `slog` logging.
*   **Swagger/OpenAPI**: Interactive API documentation (Code-First) for developer integration.
*   **Atomic Transactions**: Multi-statement SQL mutations enforced entirely within the database engine to prevent race conditions.

---

## 📦 Project Structure

```text
VELTRA/
├── cmd/api/main.go           # Go Application Entry Point
├── docs/                     # Auto-generated Swagger Documentation
├── internal/
│   ├── database/             # Connection pooling (Postgres, Mongo, Redis)
│   ├── middleware/           # Rate limiting, JWT Auth, Panic recovery
│   ├── ledger/               # Atomic financial mutations & models
│   ├── ai/                   # Behavioral analytics & AI logic
├── android/                  # Android Studio Project (Kotlin/XML)
├── docker-compose.yml        # Infrastructure containerization
├── schema.sql                # Relational ledger schema
└── README.md                 # This file
```

---

## ⚡ Quick Start

### Backend (Go)
```bash
# Clone the repository
git clone https://github.com/Godfdr/VELTRA.git
cd VELTRA

# Startup Infrastructure
docker-compose up -d

# Run the API server
go run cmd/api/main.go
```

### Mobile (Android)
1. Open the `android` folder in **Android Studio**.
2. Sync Project with Gradle Files.
3. Deploy to a device with **NFC and Biometrics** support.

---

## 🤝 Contributing
Built with ❤️ by **Joshua Dawang**.

---

## 📄 License
This project is licensed under the MIT License.
