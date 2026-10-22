package com.mathsprint.game.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathsprint.game.ui.components.PrimaryButton
import com.mathsprint.game.ui.theme.AmberStreak
import com.mathsprint.game.ui.theme.EmeraldSuccess
import com.mathsprint.game.ui.theme.IndigoLight

data class OnboardingStep(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconTint: androidx.compose.ui.graphics.Color
)

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    val steps = listOf(
        OnboardingStep(
            title = "Fast Mental Calculations",
            description = "Boost your arithmetic agility with rapid, adaptive rounds designed to build genuine calculation confidence.",
            icon = Icons.Default.Bolt,
            iconTint = AmberStreak
        ),
        OnboardingStep(
            title = "100% Offline & Private",
            description = "No mandatory accounts, tracking, or network requests. Your progress and stats are stored strictly on your device.",
            icon = Icons.Default.Security,
            iconTint = EmeraldSuccess
        ),
        OnboardingStep(
            title = "Daily Challenges & Progression",
            description = "Tackle daily seeded challenges, earn stars, unlock skill bands, and watch your speed improve every session.",
            icon = Icons.Default.Timeline,
            iconTint = IndigoLight
        )
    )

    var currentStepIndex by remember { mutableIntStateOf(0) }
    val step = steps[currentStepIndex]

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                // Page indicator dots
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    steps.forEachIndexed { index, _ ->
                        val isSelected = index == currentStepIndex
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(if (isSelected) 10.dp else 8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                                )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                PrimaryButton(
                    text = if (currentStepIndex == steps.size - 1) "Get Started" else "Continue",
                    onClick = {
                        if (currentStepIndex < steps.size - 1) {
                            currentStepIndex++
                        } else {
                            onFinishOnboarding()
                        }
                    }
                )
                if (currentStepIndex < steps.size - 1) {
                    TextButton(
                        onClick = onFinishOnboarding,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Skip",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(110.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(28.dp)
                    )
            ) {
                Icon(
                    imageVector = step.icon,
                    contentDescription = null,
                    tint = step.iconTint,
                    modifier = Modifier.size(56.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = step.title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = step.description,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
