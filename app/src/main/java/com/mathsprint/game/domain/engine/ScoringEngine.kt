package com.mathsprint.game.domain.engine

import com.mathsprint.game.domain.model.DifficultyLevel
import kotlin.math.max

object ScoringEngine {

    private const val BASE_POINTS_PER_CORRECT = 100
    private const val MAX_STREAK_MULTIPLIER = 3.0
    private const val INCORRECT_PENALTY_POINTS = 30

    data class ScoreEvaluation(
        val pointsDelta: Int,
        val newScore: Int,
        val newStreak: Int,
        val isComboMilestone: Boolean
    )

    fun evaluateAnswer(
        isCorrect: Boolean,
        currentScore: Int,
        currentStreak: Int,
        difficulty: DifficultyLevel,
        responseTimeMs: Long
    ): ScoreEvaluation {
        if (!isCorrect) {
            val penalized = max(0, currentScore - INCORRECT_PENALTY_POINTS)
            return ScoreEvaluation(
                pointsDelta = -INCORRECT_PENALTY_POINTS,
                newScore = penalized,
                newStreak = 0,
                isComboMilestone = false
            )
        }

        val updatedStreak = currentStreak + 1
        val streakMultiplier = calculateStreakMultiplier(updatedStreak)
        val speedBonus = calculateSpeedBonus(responseTimeMs)
        val difficultyMultiplier = difficulty.scoreMultiplier

        val points = ((BASE_POINTS_PER_CORRECT + speedBonus) * streakMultiplier * difficultyMultiplier).toInt()
        val updatedScore = currentScore + points

        val isMilestone = updatedStreak in listOf(3, 5, 10, 15, 20, 30)

        return ScoreEvaluation(
            pointsDelta = points,
            newScore = updatedScore,
            newStreak = updatedStreak,
            isComboMilestone = isMilestone
        )
    }

    fun calculateStreakMultiplier(streak: Int): Double {
        return when {
            streak <= 1 -> 1.0
            streak in 2..3 -> 1.25
            streak in 4..6 -> 1.5
            streak in 7..9 -> 2.0
            streak in 10..14 -> 2.5
            else -> MAX_STREAK_MULTIPLIER
        }
    }

    private fun calculateSpeedBonus(responseTimeMs: Long): Int {
        return when {
            responseTimeMs < 1500 -> 50
            responseTimeMs < 3000 -> 30
            responseTimeMs < 5000 -> 15
            else -> 0
        }
    }

    fun calculateXpReward(totalScore: Int, accuracyPercent: Int, maxStreak: Int): Int {
        val base = totalScore / 10
        val accuracyBonus = if (accuracyPercent >= 90) 50 else if (accuracyPercent >= 70) 25 else 0
        val streakBonus = maxStreak * 5
        return base + accuracyBonus + streakBonus
    }
}
