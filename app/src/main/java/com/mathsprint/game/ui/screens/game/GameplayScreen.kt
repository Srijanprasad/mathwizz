package com.mathsprint.game.ui.screens.game

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathsprint.game.domain.model.GameMode
import com.mathsprint.game.ui.components.AnswerButton
import com.mathsprint.game.ui.components.ScoreBadge
import com.mathsprint.game.ui.components.StreakBanner
import com.mathsprint.game.ui.components.TimerBar
import com.mathsprint.game.ui.theme.AmberStreak

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameplayScreen(
    viewModel: GameViewModel,
    onRoundFinished: (String) -> Unit,
    onQuitToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val session = uiState.session

    // Intercept hardware back button to show pause dialog
    BackHandler(enabled = session?.isFinished == false) {
        viewModel.pauseGame()
    }

    LaunchedEffect(uiState.summary) {
        uiState.summary?.let { summary ->
            onRoundFinished(summary.sessionId)
        }
    }

    if (session == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (uiState.isPaused) {
        PauseDialog(
            onResume = { viewModel.resumeGame() },
            onRestart = { viewModel.startGame(session.mode, session.category, session.difficulty) },
            onQuit = onQuitToHome
        )
    }

    val currentQuestion = session.currentQuestion

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(end = 12.dp)
                    ) {
                        Column {
                            Text(
                                text = if (session.mode == GameMode.TIME_ATTACK) {
                                    "${uiState.remainingSeconds}s"
                                } else {
                                    "Question ${session.currentIndex + 1} / ${session.questions.size}"
                                },
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "${session.category.displayName} • ${session.difficulty.displayName}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        ScoreBadge(score = session.score)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.pauseGame() }) {
                        Icon(imageVector = Icons.Default.Pause, contentDescription = "Pause")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Progress & Streak Area
            Column(modifier = Modifier.fillMaxWidth()) {
                if (session.mode.isTimed) {
                    TimerBar(
                        remainingSeconds = uiState.remainingSeconds,
                        totalSeconds = uiState.totalSeconds
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                } else {
                    LinearProgressIndicator(
                        progress = { session.progressFraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    StreakBanner(streak = session.currentStreak)
                }
            }

            // Question Display Hero Card
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 12.dp)
            ) {
                if (currentQuestion != null) {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 36.dp)
                        ) {
                            Text(
                                text = currentQuestion.expression,
                                fontSize = 42.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                letterSpacing = 1.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "= ?",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            // Answer Options Grid (2x2 layout with thumb-friendly buttons)
            if (currentQuestion != null) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    val options = currentQuestion.options
                    if (options.size >= 4) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AnswerButton(
                                option = options[0],
                                onClick = { viewModel.selectAnswer(options[0]) },
                                enabled = !uiState.isEvaluatingAnswer,
                                modifier = Modifier.weight(1f)
                            )
                            AnswerButton(
                                option = options[1],
                                onClick = { viewModel.selectAnswer(options[1]) },
                                enabled = !uiState.isEvaluatingAnswer,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AnswerButton(
                                option = options[2],
                                onClick = { viewModel.selectAnswer(options[2]) },
                                enabled = !uiState.isEvaluatingAnswer,
                                modifier = Modifier.weight(1f)
                            )
                            AnswerButton(
                                option = options[3],
                                onClick = { viewModel.selectAnswer(options[3]) },
                                enabled = !uiState.isEvaluatingAnswer,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    } else {
                        options.forEach { opt ->
                            AnswerButton(
                                option = opt,
                                onClick = { viewModel.selectAnswer(opt) },
                                enabled = !uiState.isEvaluatingAnswer
                            )
                        }
                    }
                }
            }
        }
    }
}
