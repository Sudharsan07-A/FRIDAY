package com.friday.ai.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.friday.ai.domain.model.AssistantStatus
import com.friday.ai.ui.components.FridayOrb
import com.friday.ai.ui.components.GlowButton
import com.friday.ai.ui.theme.FridayColors

@Composable
fun HomeScreen(
    status: AssistantStatus,
    onMicrophoneClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onChatClick: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FridayColors.DeepBlack)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "FRIDAY",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = FridayColors.ArcReactorBlue
            )
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = FridayColors.ArcReactorBlue,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSettingsClick() }
            )
        }

        // Status Display
        Text(
            text = when (status) {
                AssistantStatus.READY -> "Ready"
                AssistantStatus.LISTENING -> "Listening..."
                AssistantStatus.THINKING -> "Thinking..."
                AssistantStatus.SPEAKING -> "Speaking..."
                AssistantStatus.EXECUTING -> "Executing..."
                AssistantStatus.ERROR -> "Error"
                AssistantStatus.OFFLINE -> "Offline"
            },
            fontSize = 14.sp,
            color = when (status) {
                AssistantStatus.READY -> FridayColors.ArcReactorBlue
                AssistantStatus.LISTENING -> FridayColors.NeonGreen
                AssistantStatus.THINKING -> FridayColors.RepulsorOrange
                AssistantStatus.SPEAKING -> FridayColors.ArcReactorBlue
                AssistantStatus.EXECUTING -> FridayColors.NeonGreen
                AssistantStatus.ERROR -> FridayColors.ErrorRed
                AssistantStatus.OFFLINE -> Color.Gray
            }
        )

        // Spacer
        Spacer(modifier = Modifier.height(16.dp))

        // Friday Orb
        FridayOrb(
            status = status,
            modifier = Modifier.clickable { onMicrophoneClick() }
        )

        // Spacer
        Spacer(modifier = Modifier.height(16.dp))

        // Quick Actions
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GlowButton(
                text = "Chat",
                onClick = { onChatClick() },
                modifier = Modifier.weight(1f),
                color = FridayColors.ArcReactorBlue
            )
            GlowButton(
                text = "Codex",
                onClick = { /* Navigate to Codex */ },
                modifier = Modifier.weight(1f),
                color = FridayColors.NeonGreen
            )
            GlowButton(
                text = "Memory",
                onClick = { /* Navigate to Memory */ },
                modifier = Modifier.weight(1f),
                color = FridayColors.RepulsorOrange
            )
        }

        // Text Input Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(FridayColors.SurfaceLight, androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(
                text = "Tap to speak or type...",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}
