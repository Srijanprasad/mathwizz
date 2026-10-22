package com.mathsprint.game.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_sessions")
data class GameSessionEntity(
    @PrimaryKey
    val sessionId: String,
    val mode: String,
    val category: String,
    val difficulty: String,
    val totalQuestions: Int,
    val correctCount: Int,
    val accuracyPercent: Int,
    val totalScore: Int,
    val maxStreak: Int,
    val durationSeconds: Long,
    val timestamp: Long
)
