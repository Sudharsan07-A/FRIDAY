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
import com.friday.ai.domain.model.Memory
import com.friday.ai.ui.theme.FridayColors

@Composable
fun MemoryScreen(
    memories: List<Memory>,
    onBackClick: () -> Unit,
    onDeleteMemory: (Memory) -> Unit
) {
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
                text = "Memory",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = FridayColors.ArcReactorBlue
            )
        }

        // Memories List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(memories) { memory ->
                MemoryCard(
                    memory = memory,
                    onDelete = { onDeleteMemory(memory) }
                )
            }
        }
    }
}

@Composable
fun MemoryCard(
    memory: Memory,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(FridayColors.SurfaceLight)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = memory.type.toString(),
                    fontSize = 12.sp,
                    color = FridayColors.NeonGreen,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = memory.content,
                fontSize = 14.sp,
                color = androidx.compose.ui.graphics.Color.White
            )
        }
    }
}
