package com.mathsprint.game.domain.model

data class PlayerProgress(
    val totalXp: Int = 0,
    val level: Int = 1,
    val totalGamesPlayed: Int = 0,
    val totalQuestionsAnswered: Int = 0,
    val totalCorrectAnswers: Int = 0,
    val bestStreak: Int = 0,
    val currentDailyStreak: Int = 0,
    val highestScore: Int = 0,
    val unlockedBands: Set<String> = setOf("novice")
) {
    val accuracyPercent: Int
        get() = if (totalQuestionsAnswered == 0) 0 else ((totalCorrectAnswers * 100) / totalQuestionsAnswered)
}

data class LevelBand(
    val id: String,
    val title: String,
    val description: String,
    val requiredLevel: Int,
    val difficulty: DifficultyLevel,
    val isUnlocked: Boolean,
    val totalStars: Int = 0,
    val maxStars: Int = 15
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val isUnlocked: Boolean = false,
    val currentProgress: Int = 0,
    val maxProgress: Int = 1,
    val unlockedAt: Long? = null
)
