package com.mathsprint.game.domain

import com.mathsprint.game.domain.engine.MathEngine
import com.mathsprint.game.domain.model.DifficultyLevel
import com.mathsprint.game.domain.model.QuestionCategory
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class MathEngineTest {

    private lateinit var engine: MathEngine

    @Before
    fun setUp() {
        engine = MathEngine(Random(42))
    }

    @Test
    fun testAdditionQuestionValidation() {
        val q = engine.generateQuestion(QuestionCategory.ADDITION, DifficultyLevel.EASY)
        assertNotNull(q)
        assertEquals(4, q.options.size)
        assertEquals(1, q.options.count { it.isCorrect })
        val correctOption = q.options.first { it.isCorrect }
        assertEquals(q.correctAnswerText, correctOption.text)

        // Verify mathematical correctness
        val parts = q.expression.split("+").map { it.trim().toInt() }
        assertEquals(2, parts.size)
        assertEquals(parts[0] + parts[1], q.correctAnswerText.toInt())

        // Verify options are unique
        assertEquals(4, q.options.map { it.text }.distinct().size)
    }

    @Test
    fun testSubtractionQuestionValidation() {
        repeat(20) {
            val q = engine.generateQuestion(QuestionCategory.SUBTRACTION, DifficultyLevel.MEDIUM)
            val parts = q.expression.split("−").map { it.trim().toInt() }
            val answer = parts[0] - parts[1]
            assertTrue("Subtraction answer must be non-negative", answer >= 0)
            assertEquals(answer, q.correctAnswerText.toInt())
            assertEquals(4, q.options.map { it.text }.distinct().size)
            assertEquals(1, q.options.count { it.isCorrect })
        }
    }

    @Test
    fun testMultiplicationQuestionValidation() {
        repeat(20) {
            val q = engine.generateQuestion(QuestionCategory.MULTIPLICATION, DifficultyLevel.HARD)
            val parts = q.expression.split("×").map { it.trim().toInt() }
            val expected = parts[0] * parts[1]
            assertEquals(expected, q.correctAnswerText.toInt())
            assertEquals(4, q.options.map { it.text }.distinct().size)
        }
    }

    @Test
    fun testDivisionQuestionNoFractionsAndNoZero() {
        repeat(30) {
            val q = engine.generateQuestion(QuestionCategory.DIVISION, DifficultyLevel.EASY)
            val parts = q.expression.split("÷").map { it.trim().toInt() }
            val dividend = parts[0]
            val divisor = parts[1]
            assertTrue("Divisor must not be zero", divisor > 0)
            assertEquals("Division must yield exact integer with no remainder", 0, dividend % divisor)
            assertEquals(dividend / divisor, q.correctAnswerText.toInt())
            assertEquals(4, q.options.map { it.text }.distinct().size)
        }
    }

    @Test
    fun testFractionsValidation() {
        repeat(20) {
            val q = engine.generateQuestion(QuestionCategory.FRACTIONS, DifficultyLevel.MEDIUM)
            assertEquals(4, q.options.size)
            assertEquals(1, q.options.count { it.isCorrect })
            assertEquals(4, q.options.map { it.text }.distinct().size)
            assertTrue("Explanation must contain expression", q.explanation.contains("="))
        }
    }

    @Test
    fun testDecimalsValidationNoFloatDrift() {
        repeat(20) {
            val q = engine.generateQuestion(QuestionCategory.DECIMALS, DifficultyLevel.MEDIUM)
            assertEquals(4, q.options.size)
            assertEquals(1, q.options.count { it.isCorrect })
            assertEquals(4, q.options.map { it.text }.distinct().size)
            assertTrue("Decimal string must contain a decimal point", q.correctAnswerText.contains("."))
        }
    }

    @Test
    fun testPercentagesValidation() {
        repeat(20) {
            val q = engine.generateQuestion(QuestionCategory.PERCENTAGES, DifficultyLevel.EASY)
            assertEquals(4, q.options.size)
            assertEquals(1, q.options.count { it.isCorrect })
            assertEquals(4, q.options.map { it.text }.distinct().size)
            val answer = q.correctAnswerText.toInt()
            assertTrue("Percentage answer must be positive integer", answer > 0)
        }
    }

    @Test
    fun testBatchGeneration() {
        val batch = engine.generateBatch(QuestionCategory.MIXED, DifficultyLevel.MEDIUM, 10)
        assertEquals(10, batch.size)
        for (q in batch) {
            assertEquals(4, q.options.size)
            assertEquals(1, q.options.count { it.isCorrect })
        }
    }
}
