# MathSprint Performance & Responsiveness Plan

## 1. Engineering Principles
Responsiveness and predictable low latency are foundational product requirements for a fast-paced mental mathematics game. Every user tap, answer submission, and screen navigation must feel immediate and fluid on entry-level Android hardware (e.g., devices with 2GB-3GB RAM and entry quad-core/octa-core ARM SoCs).

---

## 2. Target Performance Benchmarks

| Metric | Target (Entry / Mid Tier) | Measurement Technique |
| :--- | :--- | :--- |
| **Warm Launch to Interactive Home** | $p95 \le 1.2\,\text{s}$ | Android Studio Profiler / Macrobenchmark |
| **Cold Launch to Interactive Home** | $p95 \le 1.8\,\text{s}$ | Cold startup trace without heavy initial I/O |
| **Answer Submission Feedback** | $p95 \le 60\,\text{ms}$ | Immediate UI state mutation in memory before disk persist |
| **Screen Transition Latency** | $p95 \le 120\,\text{ms}$ | Compose NavHost transitions with pre-instantiated routes |
| **Gameplay Frame Rate** | Stable 60 FPS (zero jank) | Choreographer frame dropped count $\le 1$ per 500 frames |
| **Peak Memory Footprint** | $\le 85\,\text{MB}$ RSS | Heap profiler on API 26+ devices |

---

## 3. Latency Optimization Techniques

### 3.1 In-Memory Answer Evaluation
* Mathematical questions and their valid solutions are generated or pre-evaluated in pure memory.
* When the user taps an answer, the correctness check runs synchronously on the main thread in under $1\,\text{ms}$ (pure integer/rational equality), updating the Compose state immediately.
* Persistence to Room and DataStore happens asynchronously in the background via `Dispatchers.IO`, decoupling UX latency from flash storage write speeds.

### 3.2 Pre-Generation of Question Batches
* For Quick Play and Daily Challenge, the round questions (10 questions) are generated upfront during the transition to the game screen.
* For Time Attack, questions are generated in a background queue buffer so the next question is ready before the current one is completed.

### 3.3 Recomposition Minimization in Jetpack Compose
* Strict use of `@Immutable` and `@Stable` annotations on all domain models (`Question`, `AnswerOption`, `GameSession`).
* Primitives passed into child composables where possible.
* Keys on dynamic elements (`items(keys = { ... })`).
* Decoupled animation timers so high-frequency countdown timers do not trigger full screen recompositions (isolated to a dedicated `TimerIndicator` composable).

### 3.4 Startup Optimization
* Database initialization is lazy and deferred until required by view models.
* Avoid large dependency injection frameworks with heavy reflection.
* No heavy assets, audio decoding, or blocking filesystem operations on the Application `onCreate`.

---

## 4. Resource & Memory Conservation
1. **Vector Graphics Only:** 100% vector drawables (`xml` and Compose icons) instead of large PNG/JPEG bitmap bundles.
2. **Deterministic Garbage Collection:** Minimal short-lived object allocations during gameplay loops.
3. **Hardware Acceleration:** Leverage standard Android hardware rendering with Compose canvas primitives.
