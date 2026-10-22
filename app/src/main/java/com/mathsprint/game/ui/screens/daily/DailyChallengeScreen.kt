package com.mathsprint.game.ui.screens.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathsprint.game.domain.model.DailyChallengeInfo
import com.mathsprint.game.ui.components.PrimaryButton
import com.mathsprint.game.ui.components.StatCard
import com.mathsprint.game.ui.theme.AmberStreak
import com.mathsprint.game.ui.theme.EmeraldSuccess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyChallengeScreen(
    challengeInfo: DailyChallengeInfo,
    onNavigateBack: () -> Unit,
    onStartDailyChallenge: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Challenge", fontWeight = FontWeight.Bold) },
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
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                PrimaryButton(
                    text = if (challengeInfo.isCompleted) "Replay Daily Challenge" else "Start Daily Challenge",
                    icon = Icons.Default.PlayArrow,
                    onClick = onStartDailyChallenge
                )
            }
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        if (challengeInfo.isCompleted) EmeraldSuccess.copy(alpha = 0.15f) else AmberStreak.copy(alpha = 0.15f)
                    )
            ) {
                Icon(
                    imageVector = if (challengeInfo.isCompleted) Icons.Default.CheckCircle else Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = if (challengeInfo.isCompleted) EmeraldSuccess else AmberStreak,
                    modifier = Modifier.size(46.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Challenge: ${challengeInfo.dateKey}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "10 deterministic mathematical puzzles specifically calibrated for today. Same questions for everyone globally, 100% offline.",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                StatCard(
                    title = "Status",
                    value = if (challengeInfo.isCompleted) "Cleared" else "Pending",
                    icon = if (challengeInfo.isCompleted) Icons.Default.CheckCircle else Icons.Default.CalendarToday,
                    iconTint = if (challengeInfo.isCompleted) EmeraldSuccess else AmberStreak,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Best Score",
                    value = "${challengeInfo.bestScore}",
                    icon = Icons.Default.CheckCircle,
                    iconTint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
