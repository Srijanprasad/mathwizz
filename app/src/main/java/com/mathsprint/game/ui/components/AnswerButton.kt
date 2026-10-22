package com.mathsprint.game.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathsprint.game.domain.model.AnswerOption
import com.mathsprint.game.domain.model.AnswerState
import com.mathsprint.game.ui.theme.EmeraldSuccess
import com.mathsprint.game.ui.theme.RoseError

@Composable
fun AnswerButton(
    option: AnswerOption,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val targetBgColor = when (option.state) {
        AnswerState.CORRECT -> EmeraldSuccess
        AnswerState.INCORRECT -> RoseError
        AnswerState.SELECTED -> MaterialTheme.colorScheme.primaryContainer
        AnswerState.DEFAULT -> MaterialTheme.colorScheme.surfaceVariant
    }

    val targetContentColor = when (option.state) {
        AnswerState.CORRECT -> Color.White
        AnswerState.INCORRECT -> Color.White
        AnswerState.SELECTED -> MaterialTheme.colorScheme.onPrimaryContainer
        AnswerState.DEFAULT -> MaterialTheme.colorScheme.onSurface
    }

    val animatedBg by animateColorAsState(
        targetValue = targetBgColor,
        animationSpec = tween(durationMillis = 180),
        label = "AnswerBgColor"
    )

    val animatedContentColor by animateColorAsState(
        targetValue = targetContentColor,
        animationSpec = tween(durationMillis = 180),
        label = "AnswerContentColor"
    )

    val border = when (option.state) {
        AnswerState.CORRECT -> BorderStroke(2.dp, EmeraldSuccess)
        AnswerState.INCORRECT -> BorderStroke(2.dp, RoseError)
        AnswerState.SELECTED -> BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        AnswerState.DEFAULT -> BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
    }

    val accessibilityDesc = when (option.state) {
        AnswerState.CORRECT -> "Option ${option.text}, Correct answer"
        AnswerState.INCORRECT -> "Option ${option.text}, Incorrect answer"
        else -> "Option ${option.text}"
    }

    Card(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = animatedBg,
            contentColor = animatedContentColor,
            disabledContainerColor = animatedBg,
            disabledContentColor = animatedContentColor
        ),
        border = border,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp, pressedElevation = 0.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .semantics { contentDescription = accessibilityDesc }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = option.text,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = animatedContentColor
            )

            if (option.state == AnswerState.CORRECT) {
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Correct",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else if (option.state == AnswerState.INCORRECT) {
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Incorrect",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
