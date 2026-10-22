package com.mathsprint.game.domain.model

enum class AnswerState {
    DEFAULT,
    SELECTED,
    CORRECT,
    INCORRECT
}

data class AnswerOption(
    val id: String,
    val text: String,
    val isCorrect: Boolean,
    val state: AnswerState = AnswerState.DEFAULT
)

data class Question(
    val id: String,
    val expression: String,
    val category: QuestionCategory,
    val difficulty: DifficultyLevel,
    val correctAnswerText: String,
    val options: List<AnswerOption>,
    val explanation: String = ""
) {
    init {
        require(options.isNotEmpty()) { "Question must have answer options." }
        require(options.count { it.isCorrect } == 1) {
            "Question '$expression' must have exactly one correct option. Found: ${options.count { it.isCorrect }}"
        }
        val texts = options.map { it.text }
        require(texts.distinct().size == options.size) {
            "Question '$expression' options must be unique distractors. Found duplicates: $texts"
        }
    }
}
