# MathSprint Architecture Documentation

## 1. Executive Summary & Product Vision
**MathSprint** is an offline-first mathematics game built natively for Android using Kotlin and Jetpack Compose. Its design objective is to deliver rapid, engaging, and pedagogically sound mental math challenges with low interaction latency and high visual responsiveness across diverse device tiers.

The application operates without an account or mandatory internet connection, storing all progress and session metrics locally via SQLite/Room and DataStore Preferences.

---

## 2. Technology Stack

| Layer / Concern | Technology | Justification |
| :--- | :--- | :--- |
| **Language** | Kotlin 2.0.21 | Modern type safety, coroutines, sealed interfaces, and first-class Compose support. |
| **UI Framework** | Jetpack Compose + Material 3 | Declarative, reactive UI with fluid animations, dynamic theming, and accessible semantics. |
| **Architecture Pattern** | Clean Architecture (MVI / Unidirectional Data Flow) | Clear boundaries between Presentation, Domain, and Data layers to maximize testability. |
| **Local Persistence** | AndroidX Room 2.6.1 | Robust relational storage with compile-time SQL verification, migrations, and Flow streams. |
| **Key-Value Storage** | AndroidX DataStore Preferences 1.1.1 | Safe, asynchronous transactional storage for settings, theme, and audio/haptic toggles. |
| **Concurrency** | Kotlin Coroutines & StateFlow | Reactive, lifecycle-aware state handling with zero main-thread blocking. |
| **Target SDK** | API 35 (Android 15) | Fully aligned with latest Google Play Console requirements; minSdk 26 (Android 8.0). |

---

## 3. High-Level Architecture Diagram

```mermaid
flowchart TB
    subgraph UI_Layer ["Presentation Layer (Jetpack Compose)"]
        UI[Screens & Components]
        NAV[Navigation Graph]
        VM[ViewModels (StateFlow)]
    end

    subgraph Domain_Layer ["Domain Layer (Pure Kotlin)"]
        UC[Use Cases]
        ENGINE[Math Generator & Evaluation Engine]
        MODELS[Domain Models]
        RULES[Scoring, Streak & Level Rules]
    end

    subgraph Data_Layer ["Data Layer"]
        REPO[Repository Implementations]
        ROOM[(Room Database - GameSessions, Daily, Stats)]
        PREFS[(DataStore Preferences - Settings)]
    end

    UI --> NAV
    NAV --> VM
    VM --> UC
    UC --> ENGINE
    UC --> MODELS
    UC --> RULES
    UC --> REPO
    REPO --> ROOM
    REPO --> PREFS
```

---

## 4. Layer Responsibilities

### 4.1 Presentation Layer (`com.mathsprint.game.ui`)
* **Jetpack Compose Screens:** Unidirectional Data Flow (`State` in, `Event` out).
* **ViewModels:** Owns UI state represented as immutable sealed states or data classes exposed via `StateFlow`. Handles coroutine scopes (`viewModelScope`) and user intent dispatching.
* **Navigation:** `NavHost` driven by type-safe route definitions. Preserves state across back-stack entries.
* **Accessibility:** High color contrast, touch targets $\ge 48\,\text{dp}$, and semantic `contentDescription` for screen readers.

### 4.2 Domain Layer (`com.mathsprint.game.domain`)
* **Zero Android Dependencies:** 100% pure Kotlin for fast JVM unit testing.
* **Models:**
  * `Question`: Expression string, operands, operator, correct answer, and distractor options.
  * `AnswerOption`: Label, value, state (Default, Selected, Correct, Incorrect).
  * `QuestionCategory`: Addition, Subtraction, Multiplication, Division, Fractions, Decimals, Percentages, Mixed.
  * `DifficultyLevel`: Easy, Medium, Hard, Expert (dynamically alters operand bounds and time thresholds).
  * `GameSession`: Mode, active round, score, streak, mistakes list, and start/end timestamps.
  * `DailyChallenge`: Date seed, deterministically generated question pool, completion status.
  * `PlayerProgress`: Level, XP, unlocked topics, and high scores.
* **Math Engine (`MathEngine`):**
  * Subtraction respects negative number policy.
  * Division guarantees exact integer quotients (no unintended floating-point rounding in integer modes).
  * Division by zero is mathematically barred.
  * Fractions use exact Rational arithmetic (GCD/LCM reduction) to avoid floating-point drift.
  * Decimals use fixed-scale integer arithmetic or exact representations.
  * Distractor generation uses plausible near-misses, operator transposition, and sign errors, guaranteeing 4 unique choices.

### 4.3 Data Layer (`com.mathsprint.game.data`)
* **Repositories:**
  * `GameSessionRepository`: Stores completed session records, per-topic accuracy, and round summaries.
  * `DailyChallengeRepository`: Manages daily challenge completion states and date synchronization.
  * `ProgressRepository`: Accumulates player XP, mastery stars, and unlocked levels.
  * `PreferencesRepository`: Manages user settings (difficulty default, sound, haptics, theme, reduced motion).
* **Room Database:** Compile-time verified schema with `GameSessionEntity`, `DailyChallengeEntity`, `AchievementEntity`, and `TopicStatEntity`.

---

## 5. Offline & Reliability Strategy
1. **Zero Mandatory Network Calls:** Core gameplay has no remote dependency.
2. **Process Recreation & Lifecycle Safety:** Active round state is preserved in ViewModel memory and persisted immediately upon round conclusion.
3. **Idempotent Updates:** Duplicate completion events cannot award duplicate XP or double-record stats.
4. **Data Minimization:** No personally identifiable information (PII) is gathered or transmitted.
