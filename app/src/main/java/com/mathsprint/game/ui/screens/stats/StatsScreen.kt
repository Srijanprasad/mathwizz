package com.mathsprint.game.ui.screens.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathsprint.game.data.local.entity.GameSessionEntity
import com.mathsprint.game.domain.model.PlayerProgress
import com.mathsprint.game.ui.components.StatCard
import com.mathsprint.game.ui.theme.AmberStreak
import com.mathsprint.game.ui.theme.CyanMetric
import com.mathsprint.game.ui.theme.EmeraldSuccess
import com.mathsprint.game.ui.theme.IndigoLight
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    playerProgress: PlayerProgress,
    recentSessions: List<GameSessionEntity>,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Performance & Stats", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Text(
                    text = "All-Time Overview",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StatCard(
                        title = "Questions Solved",
                        value = "${playerProgress.totalQuestionsAnswered}",
                        icon = Icons.Default.Check,
                        iconTint = IndigoLight,
                        subtitle = "${playerProgress.totalCorrectAnswers} answered correctly",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Overall Accuracy",
                        value = "${playerProgress.accuracyPercent}%",
                        icon = Icons.Default.PieChart,
                        iconTint = EmeraldSuccess,
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
                        title = "Highest Score",
                        value = "${playerProgress.highestScore}",
                        icon = Icons.Default.Stars,
                        iconTint = AmberStreak,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Best Combo Streak",
                        value = "${playerProgress.bestStreak}",
                        icon = Icons.Default.LocalFireDepartment,
                        iconTint = AmberStreak,
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
                        title = "Rounds Completed",
                        value = "${playerProgress.totalGamesPlayed}",
                        icon = Icons.Default.SportsEsports,
                        iconTint = CyanMetric,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Daily Streak",
                        value = "${playerProgress.currentDailyStreak} days",
                        icon = Icons.Default.CalendarToday,
                        iconTint = EmeraldSuccess,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Recent Sessions
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Recent Sessions (${recentSessions.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            if (recentSessions.isEmpty()) {
                item {
                    Text(
                        text = "No games played yet. Start a Quick Play or Practice round!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(recentSessions) { session ->
                    SessionHistoryItem(session = session)
                }
            }
        }
    }
}

@Composable
private fun SessionHistoryItem(session: GameSessionEntity) {
    val dateStr = remember(session.timestamp) {
        val sdf = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
        sdf.format(Date(session.timestamp))
    }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Column {
                Text(
                    text = "${session.mode.replace("_", " ")} • ${session.category}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$dateStr • ${session.difficulty}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${session.totalScore} pts",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = AmberStreak
                )
                Text(
                    text = "${session.accuracyPercent}% acc",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (session.accuracyPercent >= 80) EmeraldSuccess else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
