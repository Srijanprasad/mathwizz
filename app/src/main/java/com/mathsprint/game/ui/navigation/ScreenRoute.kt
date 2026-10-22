package com.mathsprint.game.ui.navigation

sealed class ScreenRoute(val route: String) {
    object Splash : ScreenRoute("splash")
    object Onboarding : ScreenRoute("onboarding")
    object Home : ScreenRoute("home")
    object PracticeSetup : ScreenRoute("practice_setup")
    object DailyChallenge : ScreenRoute("daily_challenge")
    object Levels : ScreenRoute("levels")
    object Stats : ScreenRoute("stats")
    object Achievements : ScreenRoute("achievements")
    object Settings : ScreenRoute("settings")
    object AppInfo : ScreenRoute("app_info")

    object Gameplay : ScreenRoute("game/{mode}/{category}/{difficulty}") {
        fun createRoute(mode: String, category: String, difficulty: String): String {
            return "game/$mode/$category/$difficulty"
        }
    }

    object RoundSummary : ScreenRoute("summary/{sessionId}") {
        fun createRoute(sessionId: String): String {
            return "summary/$sessionId"
        }
    }
}
