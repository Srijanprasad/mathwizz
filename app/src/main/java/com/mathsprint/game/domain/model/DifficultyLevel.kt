package com.mathsprint.game.domain.model

enum class DifficultyLevel(
    val displayName: String,
    val description: String,
    val scoreMultiplier: Double,
    val defaultTimeLimitSeconds: Int
) {
    EASY("Easy", "Foundational mental math (numbers 1 to 20)", 1.0, 15),
    MEDIUM("Medium", "Intermediate practice (numbers 10 to 100)", 1.5, 12),
    HARD("Hard", "Speed arithmetic with larger ranges", 2.0, 10),
    EXPERT("Expert", "Advanced challenges & multi-step calculations", 3.0, 8);

    fun next(): DifficultyLevel = when (this) {
        EASY -> MEDIUM
        MEDIUM -> HARD
        HARD -> EXPERT
        EXPERT -> EXPERT
    }
}
