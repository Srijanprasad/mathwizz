package com.mathsprint.game.domain.model

data class RecordedAnswer(
    val questionId: String,
    val expression: String,
    val selectedAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean,
    val responseTimeMs: Long
)

data class RoundSummary(
    val sessionId: String,
    val mode: GameMode,
    val category: QuestionCategory,
    val difficulty: DifficultyLevel,
    val totalQuestions: Int,
    val correctCount: Int,
    val accuracyPercent: Int,
    val totalScore: Int,
    val maxStreak: Int,
    val durationSeconds: Long,
    val answers: List<RecordedAnswer>,
    val timestamp: Long = System.currentTimeMillis()
)

data class GameSession(
    val sessionId: String,
    val mode: GameMode,
    val category: QuestionCategory,
    val difficulty: DifficultyLevel,
    val questions: List<Question>,
    val currentIndex: Int = 0,
    val score: Int = 0,
    val currentStreak: Int = 0,
    val maxStreak: Int = 0,
    val answers: List<RecordedAnswer> = emptyList(),
    val startTimeMs: Long = System.currentTimeMillis(),
    val remainingTimeSeconds: Int = 60,
    val isFinished: Boolean = false
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)

    val progressFraction: Float
        get() = if (questions.isEmpty()) 0f else (currentIndex.toFloat() / questions.size.toFloat())
}
