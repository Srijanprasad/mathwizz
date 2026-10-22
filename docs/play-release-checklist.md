# Google Play Store Release Checklist for MathSprint

## 1. Build & Release Specifications
* **Application ID:** `com.mathsprint.game`
* **Version Name:** `1.0.0`
* **Version Code:** `1`
* **Target SDK:** `35` (Android 15) - Fully compliant with August 2025/2026 Play Store requirements.
* **Min SDK:** `26` (Android 8.0) - Compatible with >99% active Android devices globally.
* **Distribution Format:** Android App Bundle (`.aab`) with resource shrinking and ProGuard/R8 code minification.

---

## 2. Google Play Policy & Regulatory Compliance

### 2.1 Privacy Policy & Data Safety
- [x] **Zero Personal Data Collection:** The app does not collect user names, emails, device IDs, advertising IDs, or location.
- [x] **Offline Operation:** Core gameplay works completely without an internet connection.
- [x] **Data Safety Form Declaration:**
  - *Data collected:* None.
  - *Data shared:* None.
  - *Security practices:* Data stored locally on device sandbox; encrypted via Android standard filesystem sandbox.
  - *Data deletion:* Clear data can be performed directly inside App Settings or via standard Android System Application Manager.

### 2.2 Permissions Audit
- `android.permission.INTERNET`: **Omitted / Not Required** for core offline app.
- `android.permission.ACCESS_NETWORK_STATE`: **Not Required**.
- `android.permission.VIBRATE`: **Optional** (used for subtle haptic feedback upon answer selection; controlled via Settings toggle).
- `android.permission.POST_NOTIFICATIONS`: **Not Required** for initial release.

### 2.3 Families Policy & Content Rating
- Target Age: Everyone / Ages 6 and up (students, casual players, adults).
- Content Rating Questionnaire: Expected rating **ESRB: Everyone / PEGI: 3 / IARC: 3+**.
- No violent, suggestive, offensive, or gambling themes.
- No third-party advertisements or unvetted external web links.

---

## 3. Store Listing Assets & Metadata

### 3.1 Metadata
* **Title:** MathSprint: Mental Math Game
* **Short Description:** Fast, fun, and offline mental math workouts with adaptive challenges!
* **Category:** Educational / Brain Games / Trivia

### 3.2 Graphical Deliverables Specification
1. **App Icon:** $512 \times 512\,\text{px}$, 32-bit PNG with alpha channel.
2. **Feature Graphic:** $1024 \times 500\,\text{px}$, JPG or 24-bit PNG (no alpha).
3. **Phone Screenshots:** Minimum 4 screenshots (16:9 or 18:9 aspect ratio, minimum 1080px on shortest side).
   - Screenshot 1: Home dashboard & Quick Play
   - Screenshot 2: Active question gameplay with instant feedback & combo streak
   - Screenshot 3: Daily Challenge & Level Progression
   - Screenshot 4: Progress statistics, accuracy analytics, & achievements

---

## 4. Release Verification Checklist
- [x] Debug and release variants build cleanly with R8 optimization.
- [x] Keystore and signing keys isolated from git source control.
- [x] All unit tests pass for question generators and math accuracy.
- [x] Back navigation, pause states, and app backgrounding verified.
- [x] Reduced motion settings respected in UI animations.
