package com.mathsprint.game

import android.app.Application
import com.mathsprint.game.data.local.MathSprintDatabase
import com.mathsprint.game.data.local.PreferencesManager
import com.mathsprint.game.data.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MathSprintApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val database by lazy { MathSprintDatabase.getInstance(this) }
    val preferencesManager by lazy { PreferencesManager(this) }
    val gameRepository by lazy { GameRepository(database) }

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            gameRepository.initializeDefaultAchievements()
        }
    }
}
