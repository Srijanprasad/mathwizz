package com.mathsprint.game.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mathsprint.game.data.local.dao.AchievementDao
import com.mathsprint.game.data.local.dao.DailyChallengeDao
import com.mathsprint.game.data.local.dao.GameSessionDao
import com.mathsprint.game.data.local.dao.PlayerStatsDao
import com.mathsprint.game.data.local.entity.AchievementEntity
import com.mathsprint.game.data.local.entity.DailyChallengeEntity
import com.mathsprint.game.data.local.entity.GameSessionEntity
import com.mathsprint.game.data.local.entity.PlayerStatsEntity

@Database(
    entities = [
        GameSessionEntity::class,
        DailyChallengeEntity::class,
        PlayerStatsEntity::class,
        AchievementEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MathSprintDatabase : RoomDatabase() {

    abstract fun gameSessionDao(): GameSessionDao
    abstract fun dailyChallengeDao(): DailyChallengeDao
    abstract fun playerStatsDao(): PlayerStatsDao
    abstract fun achievementDao(): AchievementDao

    companion object {
        @Volatile
        private var INSTANCE: MathSprintDatabase? = null

        fun getInstance(context: Context): MathSprintDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MathSprintDatabase::class.java,
                    "mathsprint_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
