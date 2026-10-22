# MathSprint Build & Release Signing Guide

This guide details how to build, sign, and verify release artifacts for **MathSprint** without exposing private cryptographic secrets in source control.

---

## 1. Prerequisites
* **JDK:** OpenJDK 17 (`/opt/homebrew/Cellar/openjdk@17/17.0.19/libexec/openjdk.jdk/Contents/Home`)
* **Android SDK:** API 35 installed with Build-Tools `35.0.0`
* **Gradle Wrapper:** 8.11.1

---

## 2. Generating a Production Keystore

Execute the following command in a secure, non-repository folder (e.g. `~/.android/keystores/`):

```bash
keytool -genkeypair -v \
  -keystore ~/.android/keystores/mathsprint-release.jks \
  -alias mathsprint \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -storetype JKS
```

Keep your keystore file and passwords safe and never commit `.jks` files or passwords to git.

---

## 3. Injecting Release Credentials via Environment Variables

Set the following environment variables on your CI/CD runner or local workstation:

```bash
export MATHSPRINT_KEYSTORE_PATH="/path/to/mathsprint-release.jks"
export MATHSPRINT_KEYSTORE_PASSWORD="your-keystore-password"
export MATHSPRINT_KEY_ALIAS="mathsprint"
export MATHSPRINT_KEY_PASSWORD="your-key-password"
```

In `app/build.gradle.kts`, the production signing config reads these variables dynamically:
```kotlin
signingConfigs {
    create("release") {
        storeFile = file(System.getenv("MATHSPRINT_KEYSTORE_PATH") ?: "debug.keystore")
        storePassword = System.getenv("MATHSPRINT_KEYSTORE_PASSWORD") ?: "android"
        keyAlias = System.getenv("MATHSPRINT_KEY_ALIAS") ?: "androiddebugkey"
        keyPassword = System.getenv("MATHSPRINT_KEY_PASSWORD") ?: "android"
    }
}
```

---

## 4. Build Commands

### A. Run Automated Unit Tests
```bash
./gradlew test
```

### B. Build Local Debug APK
```bash
./gradlew assembleDebug
```
*Output location:* `app/build/outputs/apk/debug/app-debug.apk`

### C. Build Production Google Play Android App Bundle (AAB)
```bash
./gradlew bundleRelease
```
*Output location:* `app/build/outputs/bundle/release/app-release.aab`

---

## 5. Verifying App Bundle with Bundletool
To test the generated `.aab` on a physical device before uploading:

```bash
# 1. Generate universal APK from AAB
bundletool build-apks \
  --bundle=app/build/outputs/bundle/release/app-release.aab \
  --output=app/build/outputs/bundle/release/mathsprint.apks \
  --mode=universal

# 2. Install to connected device
bundletool install-apks --apks=app/build/outputs/bundle/release/mathsprint.apks
```
