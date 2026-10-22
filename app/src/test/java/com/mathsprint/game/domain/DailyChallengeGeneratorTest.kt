package com.mathsprint.game.domain

import com.mathsprint.game.domain.engine.DailyChallengeGenerator
import org.junit.Assert.*
import org.junit.Test

class DailyChallengeGeneratorTest {

    @Test
    fun testDeterministicGeneration() {
        val date = "2026-10-21"
        val questions1 = DailyChallengeGenerator.generateDailyQuestions(date)
        val questions2 = DailyChallengeGenerator.generateDailyQuestions(date)

        assertEquals(10, questions1.size)
        assertEquals(10, questions2.size)

        for (i in 0 until 10) {
            assertEquals("Question $i expression must be identical across runs for the same date",
                questions1[i].expression, questions2[i].expression)
            assertEquals("Question $i answer must be identical",
                questions1[i].correctAnswerText, questions2[i].correctAnswerText)
        }
    }

    @Test
    fun testDifferentDateGeneratesDifferentSeed() {
        val seed1 = DailyChallengeGenerator.computeSeed("2026-10-21")
        val seed2 = DailyChallengeGenerator.computeSeed("2026-10-22")
        assertNotEquals(seed1, seed2)
    }
}
