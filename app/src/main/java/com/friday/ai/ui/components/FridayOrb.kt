package com.friday.ai.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.friday.ai.domain.model.AssistantStatus
import com.friday.ai.ui.theme.FridayColors

@Composable
fun FridayOrb(
    status: AssistantStatus,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    var isAnimating by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isAnimating) 1.1f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1500
                1.0f at 0
                1.1f at 750
                1.0f at 1500
            }
        )
    )

    Box(
        modifier = modifier
            .size(120.dp)
            .scale(scale)
            .shadow(elevation = 20.dp, shape = CircleShape)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = when (status) {
                        AssistantStatus.READY -> listOf(FridayColors.ArcReactorBlue, Color(0xFF005F99))
                        AssistantStatus.LISTENING -> listOf(FridayColors.NeonGreen, Color(0xFF00AA44))
                        AssistantStatus.THINKING -> listOf(FridayColors.RepulsorOrange, Color(0xFFCC4400))
                        AssistantStatus.SPEAKING -> listOf(FridayColors.ArcReactorBlue, Color(0xFF0088FF))
                        AssistantStatus.EXECUTING -> listOf(FridayColors.NeonGreen, Color(0xFF00DD66))
                        AssistantStatus.ERROR -> listOf(FridayColors.ErrorRed, Color(0xFFAA0000))
                        AssistantStatus.OFFLINE -> listOf(Color(0xFF666666), Color(0xFF333333))
                    }
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (status) {
                AssistantStatus.READY -> "F"
                AssistantStatus.LISTENING -> "◉"
                AssistantStatus.THINKING -> "⟳"
                AssistantStatus.SPEAKING -> "◈"
                AssistantStatus.EXECUTING -> "✓"
                AssistantStatus.ERROR -> "✕"
                AssistantStatus.OFFLINE -> "◯"
            },
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
