package com.mathsprint.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.mathsprint.game.domain.model.GameSettings
import com.mathsprint.game.ui.navigation.MathSprintNavHost
import com.mathsprint.game.ui.screens.game.GameViewModel
import com.mathsprint.game.ui.theme.MathSprintTheme

class MainActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val app = application as MathSprintApplication
                return GameViewModel(app.gameRepository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as MathSprintApplication

        setContent {
            val settings by app.preferencesManager.settingsFlow.collectAsState(initial = GameSettings())

            MathSprintTheme(darkTheme = settings.isDarkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    MathSprintNavHost(
                        navController = navController,
                        repository = app.gameRepository,
                        preferencesManager = app.preferencesManager,
                        settings = settings,
                        gameViewModel = gameViewModel
                    )
                }
            }
        }
    }
}
