package com.friday.ai.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.friday.ai.domain.model.ConversationMessage
import com.friday.ai.ui.theme.FridayColors

@Composable
fun ChatScreen(
    messages: List<ConversationMessage>,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    onSendMessage: (String) -> Unit
) {
    var inputText: String = ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FridayColors.DeepBlack)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
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
                text = "Chat",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = FridayColors.ArcReactorBlue,
                modifier = Modifier.weight(1f)
            )
        }

        // Messages List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(messages) { message ->
                MessageBubble(
                    message = message.content,
                    isUser = message.role == "user",
                    timestamp = message.timestamp
                )
            }
        }
    }
}

@Composable
fun MessageBubble(
    message: String,
    isUser: Boolean,
    timestamp: Long
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isUser) FridayColors.ArcReactorBlue.copy(alpha = 0.3f)
                else FridayColors.SurfaceLight
            )
            .padding(12.dp),
        contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column {
            Text(
                text = message,
                fontSize = 14.sp,
                color = androidx.compose.ui.graphics.Color.White
            )
            Text(
                text = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                    .format(java.util.Date(timestamp)),
                fontSize = 10.sp,
                color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.5f)
            )
        }
    }
}
