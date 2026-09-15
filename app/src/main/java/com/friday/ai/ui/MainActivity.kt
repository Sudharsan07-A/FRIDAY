package com.friday.ai.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.room.Room
import com.friday.ai.data.database.FridayDatabase
import com.friday.ai.data.ai.OpenAiProvider
import com.friday.ai.data.ai.OpenAiService
import com.friday.ai.data.repository.CodexRepository
import com.friday.ai.data.repository.ConversationRepository
import com.friday.ai.data.repository.MemoryRepository
import com.friday.ai.data.repository.ReminderRepository
import com.friday.ai.data.voice.AndroidVoiceManager
import com.friday.ai.domain.ai.AiBrain
import com.friday.ai.domain.model.AssistantStatus
import com.friday.ai.domain.model.Conversation
import com.friday.ai.ui.screen.ChatScreen
import com.friday.ai.ui.screen.CodexScreen
import com.friday.ai.ui.screen.HomeScreen
import com.friday.ai.ui.screen.MemoryScreen
import com.friday.ai.ui.screen.SettingsScreen
import com.friday.ai.ui.theme.FridayTheme
import com.friday.ai.ui.theme.FridayColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import timber.log.Timber

class MainActivity : ComponentActivity() {
    private lateinit var database: FridayDatabase
    private lateinit var codexRepository: CodexRepository
    private lateinit var memoryRepository: MemoryRepository
    private lateinit var reminderRepository: ReminderRepository
    private lateinit var conversationRepository: ConversationRepository
    private lateinit var aiBrain: AiBrain
    private lateinit var voiceManager: AndroidVoiceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        Timber.plant(Timber.DebugTree())

        // Initialize Database
        database = Room.databaseBuilder(
            applicationContext,
            FridayDatabase::class.java,
            "friday_db"
        ).build()

        // Initialize Repositories
        codexRepository = CodexRepository(database.codexEntryDao())
        memoryRepository = MemoryRepository(database.memoryDao())
        reminderRepository = ReminderRepository(database.reminderDao())
        conversationRepository = ConversationRepository(database.conversationDao())

        // Initialize Voice Manager
        voiceManager = AndroidVoiceManager(this)

        // Initialize AI Provider
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openai.com")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        val openAiService = retrofit.create(OpenAiService::class.java)
        val aiProvider = OpenAiProvider(openAiService, "") // API key from settings

        // Initialize AI Brain
        aiBrain = AiBrain(aiProvider, codexRepository, memoryRepository, conversationRepository)

        setContent {
            FridayTheme {
                MainApp(
                    aiBrain = aiBrain,
                    codexRepository = codexRepository,
                    memoryRepository = memoryRepository,
                    reminderRepository = reminderRepository,
                    conversationRepository = conversationRepository,
                    voiceManager = voiceManager
                )
            }
        }
    }

    override fun onDestroy() {
        voiceManager.release()
        super.onDestroy()
    }
}

@Composable
fun MainApp(
    aiBrain: AiBrain,
    codexRepository: CodexRepository,
    memoryRepository: MemoryRepository,
    reminderRepository: ReminderRepository,
    conversationRepository: ConversationRepository,
    voiceManager: AndroidVoiceManager
) {
    var currentScreen by remember { mutableStateOf("home") }
    var currentConversationId by remember { mutableStateOf(1L) }
    val assistantStatus by aiBrain.status.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = FridayColors.SurfaceDark,
                contentColor = FridayColors.ArcReactorBlue
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = currentScreen == "home",
                    onClick = { currentScreen = "home" },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FridayColors.ArcReactorBlue,
                        selectedTextColor = FridayColors.ArcReactorBlue,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Codex") },
                    label = { Text("Codex") },
                    selected = currentScreen == "codex",
                    onClick = { currentScreen = "codex" },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FridayColors.NeonGreen,
                        selectedTextColor = FridayColors.NeonGreen,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Chat, contentDescription = "Chat") },
                    label = { Text("Chat") },
                    selected = currentScreen == "chat",
                    onClick = { currentScreen = "chat" },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FridayColors.RepulsorOrange,
                        selectedTextColor = FridayColors.RepulsorOrange,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Memory") },
                    label = { Text("Memory") },
                    selected = currentScreen == "memory",
                    onClick = { currentScreen = "memory" },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FridayColors.ArcReactorBlue,
                        selectedTextColor = FridayColors.ArcReactorBlue,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = currentScreen == "settings",
                    onClick = { currentScreen = "settings" },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FridayColors.ArcReactorBlue,
                        selectedTextColor = FridayColors.ArcReactorBlue,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FridayColors.DeepBlack)
                .padding(paddingValues)
        ) {
            when (currentScreen) {
                "home" -> HomeScreen(
                    status = assistantStatus,
                    onMicrophoneClick = { /* Voice input */ },
                    onSettingsClick = { currentScreen = "settings" },
                    onChatClick = { currentScreen = "chat" }
                )
                "codex" -> {
                    val entries by codexRepository.getAllEntries().collectAsState(initial = emptyList())
                    CodexScreen(
                        entries = entries,
                        onBackClick = { currentScreen = "home" },
                        onAddClick = { /* Add entry */ },
                        onEntryClick = { /* View entry */ }
                    )
                }
                "chat" -> ChatScreen(
                    messages = emptyList(),
                    isLoading = false,
                    onBackClick = { currentScreen = "home" },
                    onSendMessage = { /* Send message */ }
                )
                "memory" -> {
                    val memories by memoryRepository.getAllMemories().collectAsState(initial = emptyList())
                    MemoryScreen(
                        memories = memories,
                        onBackClick = { currentScreen = "home" },
                        onDeleteMemory = { /* Delete */ }
                    )
                }
                "settings" -> SettingsScreen(
                    onBackClick = { currentScreen = "home" },
                    onApiKeyChange = { /* Update API key */ }
                )
            }
        }
    }
}
