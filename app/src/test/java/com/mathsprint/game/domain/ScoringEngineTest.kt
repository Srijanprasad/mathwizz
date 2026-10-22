package com.mathsprint.game.domain

import com.mathsprint.game.domain.engine.ScoringEngine
import com.mathsprint.game.domain.model.DifficultyLevel
import org.junit.Assert.*
import org.junit.Test

class ScoringEngineTest {

    @Test
    fun testCorrectAnswerIncreasesScoreAndStreak() {
        val eval = ScoringEngine.evaluateAnswer(
            isCorrect = true,
            currentScore = 100,
            currentStreak = 2,
            difficulty = DifficultyLevel.EASY,
            responseTimeMs = 1200
        )

        assertTrue(eval.pointsDelta > 0)
        assertTrue(eval.newScore > 100)
        assertEquals(3, eval.newStreak)
        assertTrue(eval.isComboMilestone) // 3 is milestone
    }

    @Test
    fun testIncorrectAnswerPenalizesAndResetsStreak() {
        val eval = ScoringEngine.evaluateAnswer(
            isCorrect = false,
            currentScore = 100,
            currentStreak = 5,
            difficulty = DifficultyLevel.MEDIUM,
            responseTimeMs = 2000
        )

        assertEquals(0, eval.newStreak)
        assertEquals(70, eval.newScore) // 100 - 30
        assertEquals(-30, eval.pointsDelta)
    }

    @Test
    fun testScoreNeverDropsBelowZero() {
        val eval = ScoringEngine.evaluateAnswer(
            isCorrect = false,
            currentScore = 10,
            currentStreak = 0,
            difficulty = DifficultyLevel.EASY,
            responseTimeMs = 2000
        )

        assertEquals(0, eval.newScore)
    }

    @Test
    fun testStreakMultiplierCap() {
        val m1 = ScoringEngine.calculateStreakMultiplier(1)
        val m5 = ScoringEngine.calculateStreakMultiplier(5)
        val m12 = ScoringEngine.calculateStreakMultiplier(12)
        val m50 = ScoringEngine.calculateStreakMultiplier(50)

        assertEquals(1.0, m1, 0.001)
        assertEquals(1.5, m5, 0.001)
        assertEquals(2.5, m12, 0.001)
        assertEquals(3.0, m50, 0.001) // Capped at 3.0
    }
}
