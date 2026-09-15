package com.friday.ai.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.friday.ai.domain.model.CodexEntry
import com.friday.ai.ui.theme.FridayColors

@Composable
fun CodexScreen(
    entries: List<CodexEntry>,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    onEntryClick: (CodexEntry) -> Unit
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
                text = "Codex",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = FridayColors.ArcReactorBlue,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Entry",
                    tint = FridayColors.NeonGreen
                )
            }
        }

        // Entries List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(entries) { entry ->
                CodexEntryCard(
                    entry = entry,
                    onClick = { onEntryClick(entry) }
                )
            }
        }
    }
}

@Composable
fun CodexEntryCard(
    entry: CodexEntry,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(FridayColors.SurfaceLight)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = entry.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FridayColors.ArcReactorBlue
            )
            Text(
                text = entry.content.take(100),
                fontSize = 12.sp,
                color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f),
                maxLines = 2
            )
            Text(
                text = entry.category.toString(),
                fontSize = 10.sp,
                color = FridayColors.NeonGreen
            )
        }
    }
}
