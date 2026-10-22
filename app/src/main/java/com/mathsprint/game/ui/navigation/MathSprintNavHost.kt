package com.mathsprint.game.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mathsprint.game.data.local.PreferencesManager
import com.mathsprint.game.data.repository.GameRepository
import com.mathsprint.game.domain.model.*
import com.mathsprint.game.ui.screens.achievements.AchievementsScreen
import com.mathsprint.game.ui.screens.daily.DailyChallengeScreen
import com.mathsprint.game.ui.screens.game.GameViewModel
import com.mathsprint.game.ui.screens.game.GameplayScreen
import com.mathsprint.game.ui.screens.home.HomeScreen
import com.mathsprint.game.ui.screens.info.AppInfoScreen
import com.mathsprint.game.ui.screens.levels.LevelProgressionScreen
import com.mathsprint.game.ui.screens.onboarding.OnboardingScreen
import com.mathsprint.game.ui.screens.practice.PracticeSetupScreen
import com.mathsprint.game.ui.screens.settings.SettingsScreen
import com.mathsprint.game.ui.screens.splash.SplashScreen
import com.mathsprint.game.ui.screens.stats.StatsScreen
import com.mathsprint.game.ui.screens.summary.RoundSummaryScreen
import kotlinx.coroutines.launch

@Composable
fun MathSprintNavHost(
    navController: NavHostController,
    repository: GameRepository,
    preferencesManager: PreferencesManager,
    settings: GameSettings,
    gameViewModel: GameViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val playerProgress by repository.playerProgressFlow.collectAsState(initial = PlayerProgress())
    val recentSessions by repository.recentSessionsFlow.collectAsState(initial = emptyList())
    val achievements by repository.achievementsFlow.collectAsState(initial = emptyList())

    var dailyChallengeInfo by remember { mutableStateOf(DailyChallengeInfo("", "", "")) }

    LaunchedEffect(Unit) {
        dailyChallengeInfo = repository.getDailyChallengeInfo()
    }

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Splash.route
    ) {
        composable(ScreenRoute.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    if (settings.hasCompletedOnboarding) {
                        navController.navigate(ScreenRoute.Home.route) {
                            popUpTo(ScreenRoute.Splash.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(ScreenRoute.Onboarding.route) {
                            popUpTo(ScreenRoute.Splash.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(ScreenRoute.Onboarding.route) {
            OnboardingScreen(
                onFinishOnboarding = {
                    coroutineScope.launch {
                        preferencesManager.setOnboardingCompleted()
                        navController.navigate(ScreenRoute.Home.route) {
                            popUpTo(ScreenRoute.Onboarding.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(ScreenRoute.Home.route) {
            HomeScreen(
                playerProgress = playerProgress,
                dailyChallengeInfo = dailyChallengeInfo,
                onStartQuickPlay = {
                    gameViewModel.startGame(GameMode.QUICK_PLAY, QuestionCategory.MIXED, settings.defaultDifficulty)
                    navController.navigate(ScreenRoute.Gameplay.createRoute(GameMode.QUICK_PLAY.name, QuestionCategory.MIXED.name, settings.defaultDifficulty.name))
                },
                onOpenDailyChallenge = {
                    navController.navigate(ScreenRoute.DailyChallenge.route)
                },
                onOpenPractice = {
                    navController.navigate(ScreenRoute.PracticeSetup.route)
                },
                onStartTimeAttack = {
                    gameViewModel.startGame(GameMode.TIME_ATTACK, QuestionCategory.MIXED, settings.defaultDifficulty)
                    navController.navigate(ScreenRoute.Gameplay.createRoute(GameMode.TIME_ATTACK.name, QuestionCategory.MIXED.name, settings.defaultDifficulty.name))
                },
                onOpenLevels = {
                    navController.navigate(ScreenRoute.Levels.route)
                },
                onOpenStats = {
                    navController.navigate(ScreenRoute.Stats.route)
                },
                onOpenAchievements = {
                    navController.navigate(ScreenRoute.Achievements.route)
                },
                onOpenSettings = {
                    navController.navigate(ScreenRoute.Settings.route)
                }
            )
        }

        composable(ScreenRoute.PracticeSetup.route) {
            PracticeSetupScreen(
                onNavigateBack = { navController.popBackStack() },
                onStartPractice = { category, difficulty ->
                    gameViewModel.startGame(GameMode.PRACTICE, category, difficulty)
                    navController.navigate(ScreenRoute.Gameplay.createRoute(GameMode.PRACTICE.name, category.name, difficulty.name))
                }
            )
        }

        composable(ScreenRoute.DailyChallenge.route) {
            DailyChallengeScreen(
                challengeInfo = dailyChallengeInfo,
                onNavigateBack = { navController.popBackStack() },
                onStartDailyChallenge = {
                    gameViewModel.startGame(GameMode.DAILY_CHALLENGE, QuestionCategory.MIXED, DifficultyLevel.MEDIUM)
                    navController.navigate(ScreenRoute.Gameplay.createRoute(GameMode.DAILY_CHALLENGE.name, QuestionCategory.MIXED.name, DifficultyLevel.MEDIUM.name))
                }
            )
        }

        composable(ScreenRoute.Levels.route) {
            LevelProgressionScreen(
                playerProgress = playerProgress,
                onNavigateBack = { navController.popBackStack() },
                onSelectBand = { band ->
                    gameViewModel.startGame(GameMode.LEVEL_PROGRESSION, QuestionCategory.MIXED, band.difficulty)
                    navController.navigate(ScreenRoute.Gameplay.createRoute(GameMode.LEVEL_PROGRESSION.name, QuestionCategory.MIXED.name, band.difficulty.name))
                }
            )
        }

        composable(ScreenRoute.Stats.route) {
            StatsScreen(
                playerProgress = playerProgress,
                recentSessions = recentSessions,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(ScreenRoute.Achievements.route) {
            AchievementsScreen(
                achievements = achievements,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(ScreenRoute.Settings.route) {
            SettingsScreen(
                settings = settings,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAppInfo = { navController.navigate(ScreenRoute.AppInfo.route) },
                onUpdateDifficulty = { diff -> coroutineScope.launch { preferencesManager.updateDifficulty(diff) } },
                onUpdateSound = { enabled -> coroutineScope.launch { preferencesManager.updateSound(enabled) } },
                onUpdateHaptics = { enabled -> coroutineScope.launch { preferencesManager.updateHaptics(enabled) } },
                onUpdateReducedMotion = { motion -> coroutineScope.launch { preferencesManager.updateReducedMotion(motion) } },
                onUpdateDarkTheme = { dark -> coroutineScope.launch { preferencesManager.updateDarkTheme(dark) } },
                onResetProgress = { coroutineScope.launch { repository.resetAllProgress() } }
            )
        }

        composable(ScreenRoute.AppInfo.route) {
            AppInfoScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(
            route = ScreenRoute.Gameplay.route,
            arguments = listOf(
                navArgument("mode") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType },
                navArgument("difficulty") { type = NavType.StringType }
            )
        ) {
            GameplayScreen(
                viewModel = gameViewModel,
                onRoundFinished = { sessionId ->
                    navController.navigate(ScreenRoute.RoundSummary.createRoute(sessionId)) {
                        popUpTo(ScreenRoute.Gameplay.route) { inclusive = true }
                    }
                },
                onQuitToHome = {
                    navController.navigate(ScreenRoute.Home.route) {
                        popUpTo(ScreenRoute.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = ScreenRoute.RoundSummary.route,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) {
            val summary = gameViewModel.uiState.collectAsState().value.summary
            if (summary != null) {
                RoundSummaryScreen(
                    summary = summary,
                    onPlayAgain = {
                        gameViewModel.startGame(summary.mode, summary.category, summary.difficulty)
                        navController.navigate(ScreenRoute.Gameplay.createRoute(summary.mode.name, summary.category.name, summary.difficulty.name)) {
                            popUpTo(ScreenRoute.RoundSummary.route) { inclusive = true }
                        }
                    },
                    onReturnHome = {
                        navController.navigate(ScreenRoute.Home.route) {
                            popUpTo(ScreenRoute.Home.route) { inclusive = true }
                        }
                    }
                )
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(ScreenRoute.Home.route)
                }
            }
        }
    }
}
