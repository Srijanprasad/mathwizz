package com.mathsprint.game.domain.engine

import com.mathsprint.game.domain.model.*
import java.util.UUID
import kotlin.random.Random

class MathEngine(private val random: Random = Random.Default) {

    fun generateQuestion(
        category: QuestionCategory,
        difficulty: DifficultyLevel,
        seedRandom: Random = random
    ): Question {
        val resolvedCategory = if (category == QuestionCategory.MIXED) {
            val valid = listOf(
                QuestionCategory.ADDITION,
                QuestionCategory.SUBTRACTION,
                QuestionCategory.MULTIPLICATION,
                QuestionCategory.DIVISION
            )
            valid[seedRandom.nextInt(valid.size)]
        } else {
            category
        }

        return when (resolvedCategory) {
            QuestionCategory.ADDITION -> generateAddition(difficulty, seedRandom)
            QuestionCategory.SUBTRACTION -> generateSubtraction(difficulty, seedRandom)
            QuestionCategory.MULTIPLICATION -> generateMultiplication(difficulty, seedRandom)
            QuestionCategory.DIVISION -> generateDivision(difficulty, seedRandom)
            QuestionCategory.FRACTIONS -> generateFractions(difficulty, seedRandom)
            QuestionCategory.DECIMALS -> generateDecimals(difficulty, seedRandom)
            QuestionCategory.PERCENTAGES -> generatePercentages(difficulty, seedRandom)
            QuestionCategory.MIXED -> generateAddition(difficulty, seedRandom) // fallback
        }
    }

    fun generateBatch(
        category: QuestionCategory,
        difficulty: DifficultyLevel,
        count: Int,
        seedRandom: Random = random
    ): List<Question> {
        val result = mutableListOf<Question>()
        repeat(count) {
            result.add(generateQuestion(category, difficulty, seedRandom))
        }
        return result
    }

    private fun generateAddition(difficulty: DifficultyLevel, r: Random): Question {
        val (min, max) = when (difficulty) {
            DifficultyLevel.EASY -> 2 to 20
            DifficultyLevel.MEDIUM -> 10 to 60
            DifficultyLevel.HARD -> 25 to 150
            DifficultyLevel.EXPERT -> 50 to 500
        }
        val a = r.nextInt(min, max + 1)
        val b = r.nextInt(min, max + 1)
        val answer = a + b
        val expression = "$a + $b"

        val distractors = generateIntegerDistractors(answer, r)
        val options = buildOptions(answer.toString(), distractors.map { it.toString() }, r)

        return Question(
            id = UUID.randomUUID().toString(),
            expression = expression,
            category = QuestionCategory.ADDITION,
            difficulty = difficulty,
            correctAnswerText = answer.toString(),
            options = options,
            explanation = "$a plus $b equals $answer"
        )
    }

    private fun generateSubtraction(difficulty: DifficultyLevel, r: Random): Question {
        val (min, max) = when (difficulty) {
            DifficultyLevel.EASY -> 2 to 20
            DifficultyLevel.MEDIUM -> 10 to 75
            DifficultyLevel.HARD -> 25 to 200
            DifficultyLevel.EXPERT -> 50 to 600
        }
        val a = r.nextInt(min, max + 1)
        val b = r.nextInt(min, max + 1)
        val (first, second) = if (a >= b) a to b else b to a
        val answer = first - second
        val expression = "$first − $second"

        val distractors = generateIntegerDistractors(answer, r)
        val options = buildOptions(answer.toString(), distractors.map { it.toString() }, r)

        return Question(
            id = UUID.randomUUID().toString(),
            expression = expression,
            category = QuestionCategory.SUBTRACTION,
            difficulty = difficulty,
            correctAnswerText = answer.toString(),
            options = options,
            explanation = "$first minus $second equals $answer"
        )
    }

    private fun generateMultiplication(difficulty: DifficultyLevel, r: Random): Question {
        val (rangeA, rangeB) = when (difficulty) {
            DifficultyLevel.EASY -> (2..9) to (2..9)
            DifficultyLevel.MEDIUM -> (3..12) to (3..12)
            DifficultyLevel.HARD -> (6..19) to (4..15)
            DifficultyLevel.EXPERT -> (12..35) to (6..25)
        }
        val a = r.nextInt(rangeA.first, rangeA.last + 1)
        val b = r.nextInt(rangeB.first, rangeB.last + 1)
        val answer = a * b
        val expression = "$a × $b"

        val distractors = generateIntegerDistractors(answer, r)
        val options = buildOptions(answer.toString(), distractors.map { it.toString() }, r)

        return Question(
            id = UUID.randomUUID().toString(),
            expression = expression,
            category = QuestionCategory.MULTIPLICATION,
            difficulty = difficulty,
            correctAnswerText = answer.toString(),
            options = options,
            explanation = "$a times $b equals $answer"
        )
    }

    private fun generateDivision(difficulty: DifficultyLevel, r: Random): Question {
        val (rangeDivisor, rangeQuotient) = when (difficulty) {
            DifficultyLevel.EASY -> (2..9) to (2..9)
            DifficultyLevel.MEDIUM -> (3..12) to (3..15)
            DifficultyLevel.HARD -> (4..16) to (6..25)
            DifficultyLevel.EXPERT -> (6..25) to (10..40)
        }
        val divisor = r.nextInt(rangeDivisor.first, rangeDivisor.last + 1)
        val quotient = r.nextInt(rangeQuotient.first, rangeQuotient.last + 1)
        val dividend = divisor * quotient
        val expression = "$dividend ÷ $divisor"

        val distractors = generateIntegerDistractors(quotient, r)
        val options = buildOptions(quotient.toString(), distractors.map { it.toString() }, r)

        return Question(
            id = UUID.randomUUID().toString(),
            expression = expression,
            category = QuestionCategory.DIVISION,
            difficulty = difficulty,
            correctAnswerText = quotient.toString(),
            options = options,
            explanation = "$dividend divided by $divisor equals $quotient"
        )
    }

    private fun generateFractions(difficulty: DifficultyLevel, r: Random): Question {
        val denominators = when (difficulty) {
            DifficultyLevel.EASY -> listOf(2L, 3L, 4L)
            DifficultyLevel.MEDIUM -> listOf(2L, 3L, 4L, 5L, 6L)
            DifficultyLevel.HARD -> listOf(3L, 4L, 5L, 6L, 8L, 10L)
            DifficultyLevel.EXPERT -> listOf(4L, 6L, 8L, 9L, 12L)
        }
        val den1 = denominators[r.nextInt(denominators.size)]
        val den2 = if (difficulty == DifficultyLevel.EASY) den1 else denominators[r.nextInt(denominators.size)]
        val num1 = r.nextInt(1, den1.toInt()).toLong()
        val num2 = r.nextInt(1, den2.toInt()).toLong()

        val r1 = Rational.of(num1, den1)
        val r2 = Rational.of(num2, den2)
        val isAddition = r.nextBoolean()

        val (res, opSymbol) = if (isAddition) {
            (r1 + r2) to "+"
        } else {
            if (r1 >= r2) (r1 - r2) to "−" else (r2 - r1) to "−"
        }

        val expression = if (isAddition) "$r1 + $r2" else {
            if (r1 >= r2) "$r1 − $r2" else "$r2 − $r1"
        }

        val correctStr = res.toString()
        val distractorRationals = mutableSetOf<String>()
        val offsets = listOf(Rational.of(1, res.reducedDenominator), Rational.of(-1, res.reducedDenominator), Rational.of(1, 2), Rational.of(2, 3))
        for (offset in offsets) {
            try {
                val candidate = res + offset
                if (candidate.reducedNumerator > 0 && candidate.toString() != correctStr) {
                    distractorRationals.add(candidate.toString())
                }
            } catch (_: Exception) {}
        }
        var counter = 1
        while (distractorRationals.size < 3) {
            val fallback = Rational.of(res.reducedNumerator + counter, res.reducedDenominator).toString()
            if (fallback != correctStr) distractorRationals.add(fallback)
            counter++
        }

        val options = buildOptions(correctStr, distractorRationals.take(3), r)

        return Question(
            id = UUID.randomUUID().toString(),
            expression = expression,
            category = QuestionCategory.FRACTIONS,
            difficulty = difficulty,
            correctAnswerText = correctStr,
            options = options,
            explanation = "$expression = $correctStr"
        )
    }

    private fun generateDecimals(difficulty: DifficultyLevel, r: Random): Question {
        // Scaled by 10 to guarantee exact decimal arithmetic without float rounding errors
        val scale = 10
        val (rangeA, rangeB) = when (difficulty) {
            DifficultyLevel.EASY -> (10..50) to (5..30)
            DifficultyLevel.MEDIUM -> (20..150) to (10..90)
            DifficultyLevel.HARD -> (50..300) to (20..200)
            DifficultyLevel.EXPERT -> (100..800) to (50..500)
        }
        val scaledA = r.nextInt(rangeA.first, rangeA.last + 1)
        val scaledB = r.nextInt(rangeB.first, rangeB.last + 1)
        val isAddition = r.nextBoolean()

        val (scaledResult, opSymbol, strA, strB) = if (isAddition) {
            val res = scaledA + scaledB
            listOf(res, "+", formatTenths(scaledA), formatTenths(scaledB))
        } else {
            val (first, second) = if (scaledA >= scaledB) scaledA to scaledB else scaledB to scaledA
            val res = first - second
            listOf(res, "−", formatTenths(first), formatTenths(second))
        }

        val expression = "$strA $opSymbol $strB"
        val answerStr = formatTenths(scaledResult as Int)

        val distractors = mutableSetOf<String>()
        val offsets = listOf(-2, -1, 1, 2, 10, -10)
        for (off in offsets) {
            val cand = (scaledResult + off).coerceAtLeast(1)
            val candStr = formatTenths(cand)
            if (candStr != answerStr) {
                distractors.add(candStr)
            }
        }
        var c = 3
        while (distractors.size < 3) {
            val candStr = formatTenths(scaledResult + c)
            if (candStr != answerStr) distractors.add(candStr)
            c++
        }

        val options = buildOptions(answerStr, distractors.take(3), r)

        return Question(
            id = UUID.randomUUID().toString(),
            expression = expression,
            category = QuestionCategory.DECIMALS,
            difficulty = difficulty,
            correctAnswerText = answerStr,
            options = options,
            explanation = "$expression = $answerStr"
        )
    }

    private fun formatTenths(value: Int): String {
        val whole = value / 10
        val frac = value % 10
        return "$whole.$frac"
    }

    private fun generatePercentages(difficulty: DifficultyLevel, r: Random): Question {
        val pctPool = when (difficulty) {
            DifficultyLevel.EASY -> listOf(10, 50, 100)
            DifficultyLevel.MEDIUM -> listOf(10, 20, 25, 50)
            DifficultyLevel.HARD -> listOf(15, 20, 25, 75)
            DifficultyLevel.EXPERT -> listOf(5, 12, 15, 30, 40, 60, 75)
        }
        val pct = pctPool[r.nextInt(pctPool.size)]
        val base = when (pct) {
            50 -> r.nextInt(2, 50) * 2
            25, 75 -> r.nextInt(2, 25) * 4
            10, 20, 30, 40, 60 -> r.nextInt(2, 30) * 10
            5, 15 -> r.nextInt(2, 20) * 20
            12 -> r.nextInt(2, 10) * 25
            else -> r.nextInt(2, 30) * 10
        }
        val answer = (pct * base) / 100
        val expression = "$pct% of $base"

        val distractors = generateIntegerDistractors(answer, r)
        val options = buildOptions(answer.toString(), distractors.map { it.toString() }, r)

        return Question(
            id = UUID.randomUUID().toString(),
            expression = expression,
            category = QuestionCategory.PERCENTAGES,
            difficulty = difficulty,
            correctAnswerText = answer.toString(),
            options = options,
            explanation = "$pct% of $base = ($pct × $base) / 100 = $answer"
        )
    }

    private fun generateIntegerDistractors(answer: Int, r: Random): List<Int> {
        val candidates = mutableSetOf<Int>()
        val offsets = listOf(-2, -1, 1, 2, -10, 10, -5, 5)
        for (offset in offsets) {
            val dist = answer + offset
            if (dist > 0 && dist != answer) {
                candidates.add(dist)
            }
        }
        var delta = 3
        while (candidates.size < 3) {
            val cand = (answer + delta).coerceAtLeast(1)
            if (cand != answer) {
                candidates.add(cand)
            }
            delta = if (delta > 0) -delta else -(delta - 1)
        }
        return candidates.shuffled(r).take(3)
    }

    private fun buildOptions(correctText: String, distractorTexts: List<String>, r: Random): List<AnswerOption> {
        val items = mutableListOf<AnswerOption>()
        items.add(AnswerOption(id = UUID.randomUUID().toString(), text = correctText, isCorrect = true))
        for (d in distractorTexts.distinct().filter { it != correctText }.take(3)) {
            items.add(AnswerOption(id = UUID.randomUUID().toString(), text = d, isCorrect = false))
        }
        // In the rare event of duplicate text filter, ensure 4 options
        var pad = 1
        while (items.size < 4) {
            val padText = (correctText.toIntOrNull()?.let { (it + pad * 7).toString() } ?: "${correctText}_$pad")
            if (items.none { it.text == padText }) {
                items.add(AnswerOption(id = UUID.randomUUID().toString(), text = padText, isCorrect = false))
            }
            pad++
        }
        return items.shuffled(r)
    }
}
