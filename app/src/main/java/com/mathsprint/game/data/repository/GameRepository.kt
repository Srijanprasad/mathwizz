package com.mathsprint.game.data.repository

import com.mathsprint.game.data.local.MathSprintDatabase
import com.mathsprint.game.data.local.entity.AchievementEntity
import com.mathsprint.game.data.local.entity.DailyChallengeEntity
import com.mathsprint.game.data.local.entity.GameSessionEntity
import com.mathsprint.game.data.local.entity.PlayerStatsEntity
import com.mathsprint.game.domain.engine.DailyChallengeGenerator
import com.mathsprint.game.domain.engine.LevelProgressionEngine
import com.mathsprint.game.domain.engine.ScoringEngine
import com.mathsprint.game.domain.model.Achievement
import com.mathsprint.game.domain.model.DailyChallengeInfo
import com.mathsprint.game.domain.model.PlayerProgress
import com.mathsprint.game.domain.model.RoundSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.math.max

class GameRepository(private val database: MathSprintDatabase) {

    private val sessionDao = database.gameSessionDao()
    private val dailyDao = database.dailyChallengeDao()
    private val statsDao = database.playerStatsDao()
    private val achievementDao = database.achievementDao()

    val playerProgressFlow: Flow<PlayerProgress> = statsDao.getPlayerStats().map { entity ->
        if (entity == null) {
            PlayerProgress()
        } else {
            PlayerProgress(
                totalXp = entity.totalXp,
                level = entity.level,
                totalGamesPlayed = entity.totalGamesPlayed,
                totalQuestionsAnswered = entity.totalQuestionsAnswered,
                totalCorrectAnswers = entity.totalCorrectAnswers,
                bestStreak = entity.bestStreak,
                currentDailyStreak = entity.currentDailyStreak,
                highestScore = entity.highestScore,
                unlockedBands = entity.unlockedBandsCsv.split(",").filter { it.isNotBlank() }.toSet()
            )
        }
    }

    val recentSessionsFlow: Flow<List<GameSessionEntity>> = sessionDao.getRecentSessions(20)

    val achievementsFlow: Flow<List<Achievement>> = achievementDao.getAllAchievements().map { list ->
        list.map { entity ->
            Achievement(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                iconName = entity.iconName,
                isUnlocked = entity.isUnlocked,
                currentProgress = entity.currentProgress,
                maxProgress = entity.maxProgress,
                unlockedAt = entity.unlockedAt
            )
        }
    }

    suspend fun saveSession(summary: RoundSummary) = withContext(Dispatchers.IO) {
        // 1. Insert session record
        sessionDao.insertSession(
            GameSessionEntity(
                sessionId = summary.sessionId,
                mode = summary.mode.name,
                category = summary.category.name,
                difficulty = summary.difficulty.name,
                totalQuestions = summary.totalQuestions,
                correctCount = summary.correctCount,
                accuracyPercent = summary.accuracyPercent,
                totalScore = summary.totalScore,
                maxStreak = summary.maxStreak,
                durationSeconds = summary.durationSeconds,
                timestamp = summary.timestamp
            )
        )

        // 2. Compute XP reward
        val earnedXp = ScoringEngine.calculateXpReward(
            totalScore = summary.totalScore,
            accuracyPercent = summary.accuracyPercent,
            maxStreak = summary.maxStreak
        )

        // 3. Update player stats
        val currentStats = statsDao.getPlayerStatsSync() ?: PlayerStatsEntity()
        val newXp = currentStats.totalXp + earnedXp
        val newLevel = LevelProgressionEngine.calculateLevel(newXp)
        val newGamesPlayed = currentStats.totalGamesPlayed + 1
        val newQuestions = currentStats.totalQuestionsAnswered + summary.totalQuestions
        val newCorrect = currentStats.totalCorrectAnswers + summary.correctCount
        val newBestStreak = max(currentStats.bestStreak, summary.maxStreak)
        val newHighScore = max(currentStats.highestScore, summary.totalScore)

        // Check unlocked bands
        val unlockedBands = currentStats.unlockedBandsCsv.split(",").filter { it.isNotBlank() }.toMutableSet()
        for (band in LevelProgressionEngine.DEFAULT_BANDS) {
            if (newLevel >= band.requiredLevel) {
                unlockedBands.add(band.id)
            }
        }

        statsDao.insertOrUpdate(
            currentStats.copy(
                totalXp = newXp,
                level = newLevel,
                totalGamesPlayed = newGamesPlayed,
                totalQuestionsAnswered = newQuestions,
                totalCorrectAnswers = newCorrect,
                bestStreak = newBestStreak,
                highestScore = newHighScore,
                unlockedBandsCsv = unlockedBands.joinToString(",")
            )
        )

        // 4. Update achievements progress
        updateAchievementsAfterRound(newGamesPlayed, newQuestions, newBestStreak, newLevel, summary.accuracyPercent)
    }

    suspend fun getDailyChallengeInfo(dateKey: String = DailyChallengeGenerator.getTodayDateKey()): DailyChallengeInfo = withContext(Dispatchers.IO) {
        val existing = dailyDao.getChallenge(dateKey)
        DailyChallengeInfo(
            dateKey = dateKey,
            title = "Challenge for $dateKey",
            description = "10 curated questions across arithmetic and fractions",
            totalQuestions = 10,
            targetScore = 800,
            isCompleted = existing?.isCompleted == true,
            bestScore = existing?.bestScore ?: 0,
            completedAt = existing?.completedAt
        )
    }

    suspend fun recordDailyChallengeCompletion(
        dateKey: String,
        score: Int,
        accuracyPercent: Int
    ) = withContext(Dispatchers.IO) {
        val existing = dailyDao.getChallenge(dateKey)
        val best = max(existing?.bestScore ?: 0, score)
        dailyDao.insertOrUpdate(
            DailyChallengeEntity(
                dateKey = dateKey,
                isCompleted = true,
                bestScore = best,
                accuracyPercent = accuracyPercent,
                completedAt = System.currentTimeMillis()
            )
        )

        // Increment daily streak in player stats
        val currentStats = statsDao.getPlayerStatsSync() ?: PlayerStatsEntity()
        val updatedDailyStreak = if (currentStats.lastActiveDate == dateKey) {
            currentStats.currentDailyStreak
        } else {
            currentStats.currentDailyStreak + 1
        }
        statsDao.insertOrUpdate(
            currentStats.copy(
                currentDailyStreak = updatedDailyStreak,
                lastActiveDate = dateKey
            )
        )
    }

    private suspend fun updateAchievementsAfterRound(
        totalGames: Int,
        totalQuestions: Int,
        bestStreak: Int,
        level: Int,
        lastAccuracy: Int
    ) {
        val list = listOf(
            AchievementEntity("first_sprint", "First Sprint", "Complete your first mathematics round", "flag", totalGames >= 1, totalGames.coerceAtMost(1), 1, if (totalGames >= 1) System.currentTimeMillis() else null),
            AchievementEntity("sharp_shooter", "Sharpshooter", "Score 100% accuracy in any 10-question round", "target", lastAccuracy == 100, if (lastAccuracy == 100) 1 else 0, 1, if (lastAccuracy == 100) System.currentTimeMillis() else null),
            AchievementEntity("streak_master", "Streak Master", "Reach a combo streak of 10 in a single game", "bolt", bestStreak >= 10, bestStreak.coerceAtMost(10), 10, if (bestStreak >= 10) System.currentTimeMillis() else null),
            AchievementEntity("century_club", "Century Club", "Answer 100 mathematical questions", "star", totalQuestions >= 100, totalQuestions.coerceAtMost(100), 100, if (totalQuestions >= 100) System.currentTimeMillis() else null),
            AchievementEntity("level_five", "Apprentice Ascended", "Reach Player Level 5", "crown", level >= 5, level.coerceAtMost(5), 5, if (level >= 5) System.currentTimeMillis() else null)
        )
        achievementDao.insertAll(list)
    }

    suspend fun resetAllProgress() = withContext(Dispatchers.IO) {
        sessionDao.clearAll()
        statsDao.clear()
        initializeDefaultAchievements()
    }

    suspend fun initializeDefaultAchievements() = withContext(Dispatchers.IO) {
        if (achievementDao.getUnlockedCount() == 0) {
            val defaults = listOf(
                AchievementEntity("first_sprint", "First Sprint", "Complete your first mathematics round", "flag", false, 0, 1, null),
                AchievementEntity("sharp_shooter", "Sharpshooter", "Score 100% accuracy in any 10-question round", "target", false, 0, 1, null),
                AchievementEntity("streak_master", "Streak Master", "Reach a combo streak of 10 in a single game", "bolt", false, 0, 10, null),
                AchievementEntity("century_club", "Century Club", "Answer 100 mathematical questions", "star", false, 0, 100, null),
                AchievementEntity("level_five", "Apprentice Ascended", "Reach Player Level 5", "crown", false, 0, 5, null)
            )
            achievementDao.insertAll(defaults)
        }
    }
}
