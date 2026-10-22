# MathSprint: Google Play Screenshot Capture Guide

This guide details how to capture pristine high-resolution screenshots directly from a running device or emulator for the Google Play Store.

---

## 1. Google Play Screenshot Specifications
* **Format:** 24-bit PNG or JPEG (no alpha)
* **Minimum dimension:** 1080 px on shortest side
* **Aspect ratio:** 16:9 or 18:9 (e.g. $1080 \times 1920\,\text{px}$ or $1080 \times 2400\,\text{px}$)
* **Minimum count:** 4 screenshots (up to 8 recommended)

---

## 2. Screenshot Script Using ADB

When an Android device or emulator is connected via `adb`:

```bash
# Set ADB command
ADB="$HOME/Library/Android/sdk/platform-tools/adb"

# Ensure output directory exists
mkdir -p docs/screenshots

# 1. Clean Status Bar (Demo Mode for pristine status bar icons)
$ADB shell settings put global sysui_demo_allowed 1
$ADB shell am broadcast -a com.android.systemui.demo -e command enter
$ADB shell am broadcast -a com.android.systemui.demo -e command clock -e hhmm 0930
$ADB shell am broadcast -a com.android.systemui.demo -e command battery -e level 100 -e plugged false
$ADB shell am broadcast -a com.android.systemui.demo -e command network -e wifi show -e level 4 -e mobile show -e datatype none

# 2. Capture Screens
# Screen 1: Home Dashboard & Quick Play
$ADB exec-out screencap -p > docs/screenshots/01_home_dashboard.png

# Screen 2: Active Gameplay with Math Question & 4 Choice Grid
$ADB exec-out screencap -p > docs/screenshots/02_question_gameplay.png

# Screen 3: Combo Streak & Rapid Feedback
$ADB exec-out screencap -p > docs/screenshots/03_combo_streak.png

# Screen 4: Round Summary & Mistake Review
$ADB exec-out screencap -p > docs/screenshots/04_round_summary.png

# Screen 5: Daily Challenge & Calendar Tracking
$ADB exec-out screencap -p > docs/screenshots/05_daily_challenge.png

# Screen 6: Level Progression & Skill Bands
$ADB exec-out screencap -p > docs/screenshots/06_level_progression.png

# Screen 7: Player Statistics & Accuracy Breakdown
$ADB exec-out screencap -p > docs/screenshots/07_player_stats.png

# 3. Exit Demo Mode
$ADB shell am broadcast -a com.android.systemui.demo -e command exit
```

---

## 3. Recommended Marketing Overlays
For optimal Play Store conversion, frame screenshots with clean device bezels and title captions:
1. *Title 1:* "Lightning Fast Mental Math"
2. *Title 2:* "5 Exciting Game Modes"
3. *Title 3:* "Seeded Daily Challenges"
4. *Title 4:* "100% Offline & Private"
