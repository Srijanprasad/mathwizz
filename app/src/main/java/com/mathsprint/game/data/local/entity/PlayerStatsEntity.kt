package com.mathsprint.game.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_stats")
data class PlayerStatsEntity(
    @PrimaryKey
    val id: Int = 1,
    val totalXp: Int = 0,
    val level: Int = 1,
    val totalGamesPlayed: Int = 0,
    val totalQuestionsAnswered: Int = 0,
    val totalCorrectAnswers: Int = 0,
    val bestStreak: Int = 0,
    val currentDailyStreak: Int = 0,
    val highestScore: Int = 0,
    val lastActiveDate: String = "",
    val unlockedBandsCsv: String = "novice"
)
