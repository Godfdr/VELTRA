# VELTRA - Premium NFC Fintech Payment Platform ⚡

<div align="center">

![VELTRA Logo](https://img.shields.io/badge/VELTRA-Premium%20NFC%20Payments-00D4FF?style=for-the-badge)
![Go](https://img.shields.io/badge/Go-1.26+-00ADD8?style=flat-square&logo=go)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0+-7F52FF?style=flat-square&logo=kotlin)
![Architecture](https://img.shields.io/badge/Architecture-Hybrid%20Database-orange?style=flat-square)
![Status](https://img.shields.io/badge/Status-v1.0%20Published-brightgreen?style=for-the-badge)

**High-fidelity, luxury fintech ecosystem redefining digital payments with sub-1.5s transactions, social banking, and offline reliability.**

</div>

---

## 🎯 Overview

VELTRA is a **next-generation fintech platform** engineered for the African and global markets. By combining a high-performance **Go backend** with a luxury **Android experience**, VELTRA delivers more than just payments—it provides a complete financial ecosystem for both consumers and businesses.

- ⚡ **Ultra-Fast Transactions**: Sub-1.5 second end-to-end NFC taps.
- 🛡️ **Advanced Privacy**: Premium "Ghost Mode" and Biometric security.
- 📡 **Offline Mastery**: Hardware-secured offline wallets for payments without internet.
- 📊 **AI Intelligence**: Heuristic AI for automated budgeting and savings predictions.

---

## 🚀 Key Features

### 🛡️ Core Payments & Security
*   **Tap & Pay (NFC)**: Contactless payments with circular ripple animations and haptic feedback.
*   **Instant Pay**: Biometric shortcut (Long-press dashboard button) for immediate scanning.
*   **Hardware Secured Offline Wallet**: A "Reserve & Lock" mechanism using cryptographically signed tokens.
*   **Disposable Burner Cards**: Generate one-time-use virtual cards with animated "mint" and "burn" effects.
*   **Ghost Mode**: A 3-finger gesture that instantly blurs all sensitive financial data for total privacy.

### 📊 Intelligence & Analytics
*   **Financial Health Score**: Real-time tracker gamifying spending and saving habits.
*   **Spending Insights Hub**: Luxury visual breakdown with interactive horizontal "Spend Stories."
*   **AI Goal Date Prediction**: "The Fortune Teller" engine predicting exactly when you'll hit your savings goals.
*   **Auto-Budgeting Guardian**: Real-time spending guardrails with automated warnings.
*   **Payday Auto-Save**: Rule-based engine to automate savings the moment your salary arrives.

### 💼 Veltra for Business (Merchant Mode)
*   **Phone-as-POS**: Transform any Android device into a terminal to collect NFC payments instantly.
*   **Business Dashboard**: Tracks Sales, Capital, and Net Profit/Loss (P/L).
*   **Inventory & Invoicing**: Detailed stock tracking with unit-level profit analysis (Cost Price vs Selling Price).
*   **Merchant Notifications**: Live webhook settlement signals and business alerts.
*   **Expense Categorization**: Smart logging for Raw Materials and Logistics.

### 🤝 Social Banking
*   **Squad Pockets**: Joint savings goals with real-time avatars and glowing progress bars.
*   **Spot a Friend (Ping)**: Social money requests with high-fidelity notification cards.
*   **Group Bill Splitting**: Intelligent split logic with recipient toggles (Reimburse Payer or Pay Merchant).
*   **Global Remittance**: Real-time currency converter with live API integration and 1.5% service fee logic.

---

## 🏗️ Tech Stack & Architecture

### Hybrid Database Architecture (Backend)
Veltra utilizes a distributed, multi-engine database strategy for maximum reliability:
*   **PostgreSQL (Core Ledger)**: ACID-compliant primary database for immutable financial records.
*   **MongoDB (AI & Analytics)**: NoSQL engine for high-velocity behavioral data and AI logs.
*   **Redis (Speed Layer)**: In-memory cache for sub-millisecond transaction approvals.
*   **SQLite (Offline Sync)**: Embedded storage for secured offline tokens.

### Go Backend (High-Performance API)
*   **Gin Framework**: High-throughput HTTP routing.
*   **Swagger/OpenAPI**: Interactive API documentation and testing UI.
*   **Pgx Driver**: Advanced, high-performance PostgreSQL interface.
*   **Clean Repository Pattern**: Scalable directory structure separating concerns.

### Android Frontend
*   **Kotlin & Jetpack**: ViewBinding, ViewPager2, RecyclerView, MotionLayout.
*   **Lottie Engine**: Vector-based fluid animations for onboarding and dashboard.
*   **Hardware Integration**: Native NFC (HCE/ISO-DEP) and Biometric Prompt API.

---

## 📦 Project Structure

```text
VELTRA/
├── cmd/api/main.go           # Go Application Entry Point
├── docs/                     # Auto-generated Swagger Documentation
├── internal/database/        # DB Initializations (Postgres, Mongo, Redis)
├── internal/ledger/          # Core Financial Logic (ACID Transactions)
├── internal/ai/              # AI Insights & Behavioral Models
├── android/                  # Android Studio Project
│   └── app/src/main/         # Kotlin Source & High-Fidelity UI Layouts
└── README.md                 # This file
```

---

## ⚡ Quick Start

### Backend (Go)
```bash
# Clone the repository
git clone https://github.com/Godfdr/VELTRA.git
cd VELTRA

# Install dependencies
go mod tidy

# Run the API server
go run cmd/api/main.go
```

### Mobile (Android)
1. Open the `android` folder in **Android Studio**.
2. Sync Project with Gradle Files.
3. Deploy to a device with **NFC and Biometrics** support.

---

## 🤝 Contributing
Built with ❤️ by **Joshua Dawang**. Contributions are welcome via feature branches and pull requests.

---

## 📄 License
This project is licensed under the MIT License.
