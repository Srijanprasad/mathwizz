package com.mathsprint.game.domain.engine

import com.mathsprint.game.domain.model.DifficultyLevel
import com.mathsprint.game.domain.model.Question
import com.mathsprint.game.domain.model.QuestionCategory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

object DailyChallengeGenerator {

    private const val ALGORITHM_VERSION = 1
    val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun getTodayDateKey(): String {
        return LocalDate.now().format(DATE_FORMATTER)
    }

    fun computeSeed(dateKey: String): Long {
        // Deterministic integer representation of YYYYMMDD combined with algorithm version
        val clean = dateKey.replace("-", "")
        val baseNumber = clean.toLongOrNull() ?: 20261021L
        return baseNumber * 31L + ALGORITHM_VERSION * 10007L
    }

    fun generateDailyQuestions(dateKey: String = getTodayDateKey()): List<Question> {
        val seed = computeSeed(dateKey)
        val rng = Random(seed)
        val engine = MathEngine()

        // Daily Challenge features a curated mix of categories and escalating difficulties:
        // Q1-Q3: Addition & Subtraction (Easy/Medium)
        // Q4-Q6: Multiplication & Division (Medium)
        // Q7-Q8: Fractions / Decimals / Percentages (Medium/Hard)
        // Q9-Q10: Mixed Operations (Hard)
        val plan = listOf(
            QuestionCategory.ADDITION to DifficultyLevel.EASY,
            QuestionCategory.SUBTRACTION to DifficultyLevel.EASY,
            QuestionCategory.ADDITION to DifficultyLevel.MEDIUM,
            QuestionCategory.MULTIPLICATION to DifficultyLevel.MEDIUM,
            QuestionCategory.DIVISION to DifficultyLevel.MEDIUM,
            QuestionCategory.MULTIPLICATION to DifficultyLevel.MEDIUM,
            QuestionCategory.PERCENTAGES to DifficultyLevel.MEDIUM,
            QuestionCategory.FRACTIONS to DifficultyLevel.HARD,
            QuestionCategory.MIXED to DifficultyLevel.HARD,
            QuestionCategory.MIXED to DifficultyLevel.HARD
        )

        return plan.mapIndexed { index, (category, difficulty) ->
            engine.generateQuestion(category, difficulty, rng)
        }
    }
}
