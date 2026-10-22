package com.mathsprint.game.domain.model

data class GameSettings(
    val defaultDifficulty: DifficultyLevel = DifficultyLevel.EASY,
    val soundEnabled: Boolean = true,
    val hapticsEnabled: Boolean = true,
    val reducedMotion: Boolean = false,
    val timerEnabled: Boolean = true,
    val isDarkTheme: Boolean = true,
    val hasCompletedOnboarding: Boolean = false
)
