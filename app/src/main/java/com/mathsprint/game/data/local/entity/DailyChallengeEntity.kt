package com.mathsprint.game.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_challenges")
data class DailyChallengeEntity(
    @PrimaryKey
    val dateKey: String, // YYYY-MM-DD
    val isCompleted: Boolean,
    val bestScore: Int,
    val accuracyPercent: Int,
    val completedAt: Long
)
