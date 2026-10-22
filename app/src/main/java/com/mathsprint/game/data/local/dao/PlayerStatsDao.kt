package com.mathsprint.game.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mathsprint.game.data.local.entity.PlayerStatsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(stats: PlayerStatsEntity)

    @Query("SELECT * FROM player_stats WHERE id = 1 LIMIT 1")
    fun getPlayerStats(): Flow<PlayerStatsEntity?>

    @Query("SELECT * FROM player_stats WHERE id = 1 LIMIT 1")
    suspend fun getPlayerStatsSync(): PlayerStatsEntity?

    @Query("DELETE FROM player_stats")
    suspend fun clear()
}
