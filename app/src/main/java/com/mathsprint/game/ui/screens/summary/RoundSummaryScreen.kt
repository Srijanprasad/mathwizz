package com.mathsprint.game.ui.screens.summary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathsprint.game.domain.engine.LevelProgressionEngine
import com.mathsprint.game.domain.model.RecordedAnswer
import com.mathsprint.game.domain.model.RoundSummary
import com.mathsprint.game.ui.components.PrimaryButton
import com.mathsprint.game.ui.components.SecondaryButton
import com.mathsprint.game.ui.components.StatCard
import com.mathsprint.game.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundSummaryScreen(
    summary: RoundSummary,
    onPlayAgain: () -> Unit,
    onReturnHome: () -> Unit
) {
    val stars = LevelProgressionEngine.calculateStars(summary.accuracyPercent)
    val mistakes = summary.answers.filter { !it.isCorrect }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Round Complete", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PrimaryButton(
                    text = "Play Again",
                    onClick = onPlayAgain,
                    icon = Icons.Default.Replay
                )
                SecondaryButton(
                    text = "Return to Dashboard",
                    onClick = onReturnHome,
                    icon = Icons.Default.Home
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Victory / Performance Banner
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        // Star Rating
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            repeat(3) { index ->
                                val active = index < stars
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = if (active) AmberStreak else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = when {
                                summary.accuracyPercent == 100 -> "Flawless Performance!"
                                summary.accuracyPercent >= 80 -> "Excellent Speed & Accuracy!"
                                summary.accuracyPercent >= 50 -> "Good Workout! Keep Practicing!"
                                else -> "Keep Going! Practice Makes Perfect!"
                            },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${summary.mode.title} • ${summary.category.displayName}",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Stat Cards Grid
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StatCard(
                        title = "Final Score",
                        value = "${summary.totalScore}",
                        icon = Icons.Default.Stars,
                        iconTint = AmberStreak,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Accuracy",
                        value = "${summary.accuracyPercent}%",
                        icon = Icons.Default.PieChart,
                        iconTint = EmeraldSuccess,
                        subtitle = "${summary.correctCount} / ${summary.totalQuestions} correct",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StatCard(
                        title = "Max Streak",
                        value = "${summary.maxStreak}",
                        icon = Icons.Default.LocalFireDepartment,
                        iconTint = AmberStreak,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Duration",
                        value = "${summary.durationSeconds}s",
                        icon = Icons.Default.Timer,
                        iconTint = CyanMetric,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Mistake Review Section (if any)
            if (mistakes.isNotEmpty()) {
                item {
                    Text(
                        text = "Review Mistakes (${mistakes.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                items(mistakes) { mistake ->
                    MistakeItemCard(mistake = mistake)
                }
            }
        }
    }
}

@Composable
private fun MistakeItemCard(mistake: RecordedAnswer) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = BorderStroke(1.dp, RoseError.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = mistake.expression,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "Your answer: ${mistake.selectedAnswer}",
                        fontSize = 13.sp,
                        color = RoseError,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Correct: ${mistake.correctAnswer}",
                        fontSize = 13.sp,
                        color = EmeraldSuccess,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = null,
                tint = RoseError,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
