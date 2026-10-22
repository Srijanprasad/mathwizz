package com.mathsprint.game.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mathsprint.game.data.local.entity.GameSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: GameSessionEntity)

    @Query("SELECT * FROM game_sessions ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<GameSessionEntity>>

    @Query("SELECT * FROM game_sessions ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentSessions(limit: Int): Flow<List<GameSessionEntity>>

    @Query("SELECT COUNT(*) FROM game_sessions")
    suspend fun getSessionCount(): Int

    @Query("SELECT MAX(totalScore) FROM game_sessions")
    suspend fun getHighestScore(): Int?

    @Query("SELECT MAX(maxStreak) FROM game_sessions")
    suspend fun getBestStreak(): Int?

    @Query("DELETE FROM game_sessions")
    suspend fun clearAll()
}
