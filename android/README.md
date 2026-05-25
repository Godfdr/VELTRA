# VELTRA - Premium NFC Fintech Payment System

VELTRA is a high-fidelity, luxury fintech mobile application designed to redefine digital payments with a focus on speed, social interaction, and offline reliability. Built with a modern Android tech stack and a sophisticated Go backend logic.

## 🚀 Key Features & Functionalities

### 🛡️ Core Payments & Security
*   **Tap & Pay (NFC)**: Lightning-fast contactless payments with circular ripple animations and haptic feedback.
*   **Instant Pay**: A premium biometric shortcut (Long-press dashboard button + Face/Fingerprint) for immediate scanning.
*   **Hardware Secured Offline Wallet**: A "Reserve & Lock" mechanism that allows payments without internet access using cryptographically signed tokens.
*   **Biometric Authentication**: Deep integration with native Android biometrics for login, payments, and account creation.
*   **Ghost Mode**: A 3-finger gesture that instantly blurs all sensitive financial data for total privacy in public spaces.

### 📊 Intelligence & Analytics
*   **Spending Insights Hub**: Luxury visual data breakdown with interactive horizontal "Spend Stories."
*   **Auto-Budgeting Guardian**: Real-time spending guardrails for Transport, Dining, and Lifestyle with automated warnings.
*   **AI Savings Architect**: A heuristic AI engine that analyzes spending patterns to generate personalized auto-pilot savings plans.
*   **V-Points Loyalty**: Integrated loyalty system rewarding frequent users with digital assets.

### 💼 Veltra for Business (Merchant Mode)
*   **Phone-as-POS**: Transform any Android device into a merchant terminal to collect NFC payments instantly.
*   **Business Dashboard**: Dedicated hub tracking Sales, Capital, and Net Profit/Loss (P/L).
*   **Inventory Manager**: Detailed stock tracking including Cost Price vs Selling Price for unit-level profit analysis.
*   **In-App Invoicing**: Professional client management and invoice generation.
*   **Expense Categorization**: Smart logging for Raw Materials and Logistics.

### 🤝 Social & Growth
*   **Squad Pockets**: Joint savings goals for groups with real-time contributor avatars and glowing progress bars.
*   **Spot a Friend (Ping)**: A fun, one-tap social money request feature with high-fidelity notification cards.
*   **Group Bill Splitting**: Intelligent split logic with recipient toggles (reimburse payer or pay merchant directly).
*   **Refer & Earn**: Viral growth hub with instant clipboard link sharing and performance bonuses.

### 🎨 Luxury UI/UX
*   **High-Fidelity Design**: Sleek dark-mode aesthetic with vibrant gradients and modern card-based layouts.
*   **Lottie Animations**: Fluid, vector-based onboarding carousel and dashboard elements (Stationary Bus banner).
*   **Interactive ATM Skins**: Customizable digital card visuals (Classic, Gold, Cyberpunk, Eco-Green) with pulsing logo effects.
*   **System-Wide Themes**: Persistent Dark and Light modes with automatic UI adaptation.

## 🛠️ Tech Stack & Architecture

### Hybrid Database Architecture (Backend)
Veltra utilizes a distributed, multi-engine database strategy to handle high-frequency financial and behavioral data:
*   **PostgreSQL (Core Ledger)**: ACID-compliant primary database for immutable financial records and user balances.
*   **MongoDB (AI & Analytics)**: NoSQL engine for high-velocity behavioral data, Spend Stories, and AI insight logs.
*   **Redis (Speed Layer)**: In-memory cache for sub-millisecond NFC transaction approvals and session management.
*   **SQLite (Offline Sync)**: Embedded on-device storage for cryptographically secured offline wallet tokens.

### Go Backend (High-Performance API)
The core API is built using **Go (Golang)** for its superior concurrency and low-latency profile:
*   **Gin Framework**: High-throughput HTTP routing.
*   **Pgx Driver**: Advanced, high-performance PostgreSQL interface.
*   **Clean Repository Pattern**: Scalable directory structure separating Ledger, AI, and Caching layers.
*   **Context-Aware**: Full support for request timeouts and cancellations to preserve system resources.

### Android Frontend
*   **Kotlin & Jetpack**: ViewBinding, ViewPager2, RecyclerView, MotionLayout.
*   **Lottie Engine**: Vector-based fluid animations.
*   **NFC & Biometrics**: Hardware-level integration for security and payments.

---
Built with ❤️ by **Joshua Dawang**
