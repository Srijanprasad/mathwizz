package com.mathsprint.game.domain.engine

import com.mathsprint.game.domain.model.DifficultyLevel
import com.mathsprint.game.domain.model.LevelBand
import com.mathsprint.game.domain.model.PlayerProgress

object LevelProgressionEngine {

    val DEFAULT_BANDS = listOf(
        LevelBand(
            id = "novice",
            title = "Novice Runner",
            description = "Basic addition and subtraction mastery",
            requiredLevel = 1,
            difficulty = DifficultyLevel.EASY,
            isUnlocked = true,
            maxStars = 15
        ),
        LevelBand(
            id = "apprentice",
            title = "Apprentice Solver",
            description = "Multiplication tables and division foundations",
            requiredLevel = 3,
            difficulty = DifficultyLevel.MEDIUM,
            isUnlocked = false,
            maxStars = 15
        ),
        LevelBand(
            id = "skilled",
            title = "Skilled Tactician",
            description = "Fractions, decimals, and speed arithmetic",
            requiredLevel = 6,
            difficulty = DifficultyLevel.HARD,
            isUnlocked = false,
            maxStars = 15
        ),
        LevelBand(
            id = "master",
            title = "Math Master",
            description = "Complex percentages and rapid mixed operations",
            requiredLevel = 10,
            difficulty = DifficultyLevel.EXPERT,
            isUnlocked = false,
            maxStars = 15
        ),
        LevelBand(
            id = "grandmaster",
            title = "Grandmaster",
            description = "Ultimate mental mathematics challenge at lightning speed",
            requiredLevel = 15,
            difficulty = DifficultyLevel.EXPERT,
            isUnlocked = false,
            maxStars = 15
        )
    )

    fun calculateLevel(totalXp: Int): Int {
        // Level threshold curve: Level 1 = 0 XP, Level 2 = 200 XP, Level 3 = 500 XP, etc.
        // Level L requires: 100 * L * (L - 1)
        var level = 1
        while (xpRequiredForLevel(level + 1) <= totalXp) {
            level++
        }
        return level
    }

    fun xpRequiredForLevel(targetLevel: Int): Int {
        if (targetLevel <= 1) return 0
        return (targetLevel - 1) * (targetLevel - 1) * 150
    }

    fun xpProgressInCurrentLevel(totalXp: Int): Pair<Int, Int> {
        val currentLevel = calculateLevel(totalXp)
        val currentLevelXp = xpRequiredForLevel(currentLevel)
        val nextLevelXp = xpRequiredForLevel(currentLevel + 1)
        val progress = totalXp - currentLevelXp
        val needed = nextLevelXp - currentLevelXp
        return progress to needed
    }

    fun getBandsWithProgress(progress: PlayerProgress): List<LevelBand> {
        return DEFAULT_BANDS.map { band ->
            val unlocked = progress.level >= band.requiredLevel || progress.unlockedBands.contains(band.id)
            band.copy(isUnlocked = unlocked)
        }
    }

    fun calculateStars(accuracyPercent: Int): Int {
        return when {
            accuracyPercent >= 95 -> 3
            accuracyPercent >= 75 -> 2
            accuracyPercent >= 50 -> 1
            else -> 0
        }
    }
}
