package com.mathsprint.game.domain.model

data class DailyChallengeInfo(
    val dateKey: String, // Format: YYYY-MM-DD
    val title: String,
    val description: String,
    val totalQuestions: Int = 10,
    val targetScore: Int = 800,
    val isCompleted: Boolean = false,
    val bestScore: Int = 0,
    val completedAt: Long? = null
)
