package com.mathsprint.game.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mathsprint.game.data.local.entity.DailyChallengeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(challenge: DailyChallengeEntity)

    @Query("SELECT * FROM daily_challenges WHERE dateKey = :dateKey LIMIT 1")
    suspend fun getChallenge(dateKey: String): DailyChallengeEntity?

    @Query("SELECT * FROM daily_challenges WHERE dateKey = :dateKey LIMIT 1")
    fun observeChallenge(dateKey: String): Flow<DailyChallengeEntity?>

    @Query("SELECT COUNT(*) FROM daily_challenges WHERE isCompleted = 1")
    suspend fun getCompletedCount(): Int

    @Query("SELECT * FROM daily_challenges ORDER BY dateKey DESC LIMIT 30")
    fun getRecentChallenges(): Flow<List<DailyChallengeEntity>>
}
