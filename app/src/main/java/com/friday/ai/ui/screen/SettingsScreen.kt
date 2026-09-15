package com.friday.ai.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.friday.ai.ui.theme.FridayColors

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onApiKeyChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FridayColors.DeepBlack)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = FridayColors.ArcReactorBlue
                )
            }
            Text(
                text = "Settings",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = FridayColors.ArcReactorBlue
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // AI Provider Section
        SettingSection(
            title = "AI Provider",
            description = "Choose your AI provider and configure API key"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "API Configuration",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = FridayColors.NeonGreen
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Store your API key securely in settings. Never share it.",
            fontSize = 12.sp,
            color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingSection(
            title = "Voice Settings",
            description = "Configure speech recognition and text-to-speech"
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingSection(
            title = "Privacy & Data",
            description = "Manage your local data and privacy settings"
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingSection(
            title = "About FRIDAY",
            description = "Version 1.0.0"
        )
    }
}

@Composable
fun SettingSection(
    title: String,
    description: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = FridayColors.ArcReactorBlue
        )
        Text(
            text = description,
            fontSize = 12.sp,
            color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.6f)
        )
    }
}
