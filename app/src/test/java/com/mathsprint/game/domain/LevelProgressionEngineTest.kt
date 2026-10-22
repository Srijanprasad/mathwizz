package com.mathsprint.game.domain

import com.mathsprint.game.domain.engine.LevelProgressionEngine
import com.mathsprint.game.domain.model.PlayerProgress
import org.junit.Assert.*
import org.junit.Test

class LevelProgressionEngineTest {

    @Test
    fun testLevelCalculation() {
        assertEquals(1, LevelProgressionEngine.calculateLevel(0))
        assertEquals(1, LevelProgressionEngine.calculateLevel(100))
        assertEquals(2, LevelProgressionEngine.calculateLevel(150))
        assertTrue(LevelProgressionEngine.calculateLevel(1000) >= 3)
    }

    @Test
    fun testStarCalculation() {
        assertEquals(3, LevelProgressionEngine.calculateStars(100))
        assertEquals(3, LevelProgressionEngine.calculateStars(95))
        assertEquals(2, LevelProgressionEngine.calculateStars(80))
        assertEquals(1, LevelProgressionEngine.calculateStars(60))
        assertEquals(0, LevelProgressionEngine.calculateStars(40))
    }

    @Test
    fun testBandUnlocking() {
        val noviceProgress = PlayerProgress(totalXp = 0, level = 1)
        val noviceBands = LevelProgressionEngine.getBandsWithProgress(noviceProgress)
        assertTrue(noviceBands.first { it.id == "novice" }.isUnlocked)
        assertFalse(noviceBands.first { it.id == "apprentice" }.isUnlocked)

        val advancedProgress = PlayerProgress(totalXp = 5000, level = 7)
        val advancedBands = LevelProgressionEngine.getBandsWithProgress(advancedProgress)
        assertTrue(advancedBands.first { it.id == "novice" }.isUnlocked)
        assertTrue(advancedBands.first { it.id == "apprentice" }.isUnlocked)
        assertTrue(advancedBands.first { it.id == "skilled" }.isUnlocked)
        assertFalse(advancedBands.first { it.id == "master" }.isUnlocked)
    }
}
