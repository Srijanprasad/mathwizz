package com.mathsprint.game.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.mathsprint.game.domain.model.DifficultyLevel
import com.mathsprint.game.domain.model.GameSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "mathsprint_preferences")

class PreferencesManager(private val context: Context) {

    private object PreferencesKeys {
        val DEFAULT_DIFFICULTY = stringPreferencesKey("default_difficulty")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val HAPTICS_ENABLED = booleanPreferencesKey("haptics_enabled")
        val REDUCED_MOTION = booleanPreferencesKey("reduced_motion")
        val TIMER_ENABLED = booleanPreferencesKey("timer_enabled")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }

    val settingsFlow: Flow<GameSettings> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val diffStr = preferences[PreferencesKeys.DEFAULT_DIFFICULTY] ?: DifficultyLevel.EASY.name
            val diff = try {
                DifficultyLevel.valueOf(diffStr)
            } catch (_: Exception) {
                DifficultyLevel.EASY
            }
            GameSettings(
                defaultDifficulty = diff,
                soundEnabled = preferences[PreferencesKeys.SOUND_ENABLED] ?: true,
                hapticsEnabled = preferences[PreferencesKeys.HAPTICS_ENABLED] ?: true,
                reducedMotion = preferences[PreferencesKeys.REDUCED_MOTION] ?: false,
                timerEnabled = preferences[PreferencesKeys.TIMER_ENABLED] ?: true,
                isDarkTheme = preferences[PreferencesKeys.DARK_THEME] ?: true,
                hasCompletedOnboarding = preferences[PreferencesKeys.ONBOARDING_COMPLETED] ?: false
            )
        }

    suspend fun updateDifficulty(difficulty: DifficultyLevel) {
        context.dataStore.edit { it[PreferencesKeys.DEFAULT_DIFFICULTY] = difficulty.name }
    }

    suspend fun updateSound(enabled: Boolean) {
        context.dataStore.edit { it[PreferencesKeys.SOUND_ENABLED] = enabled }
    }

    suspend fun updateHaptics(enabled: Boolean) {
        context.dataStore.edit { it[PreferencesKeys.HAPTICS_ENABLED] = enabled }
    }

    suspend fun updateReducedMotion(enabled: Boolean) {
        context.dataStore.edit { it[PreferencesKeys.REDUCED_MOTION] = enabled }
    }

    suspend fun updateTimerEnabled(enabled: Boolean) {
        context.dataStore.edit { it[PreferencesKeys.TIMER_ENABLED] = enabled }
    }

    suspend fun updateDarkTheme(isDark: Boolean) {
        context.dataStore.edit { it[PreferencesKeys.DARK_THEME] = isDark }
    }

    suspend fun setOnboardingCompleted() {
        context.dataStore.edit { it[PreferencesKeys.ONBOARDING_COMPLETED] = true }
    }
}
