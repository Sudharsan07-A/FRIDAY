# FRIDAY - AI Assistant for Android

A sophisticated AI-powered voice assistant for Android devices, inspired by JARVIS/FRIDAY from Iron Man.

## Features

### Core Features
- 🎤 **Voice Interaction**: Natural speech recognition and text-to-speech
- 🧠 **AI Brain**: OpenAI integration for intelligent responses
- 💾 **Memory System**: Persistent memory and learning from interactions
- 📚 **Codex**: Knowledge base for quick access to information
- 💬 **Smart Chat**: Conversational AI with context awareness
- ⏰ **Reminders**: Set and manage reminders via voice commands
- 🔐 **Secure**: Local data storage with encryption

## Architecture

```
app/
├── data/          # Data layer with repositories
├── domain/        # Business logic layer
└── ui/            # UI layer with Compose
```

## Setup Instructions

1. Clone the repository
2. Open in Android Studio
3. Add OpenAI API key to settings
4. Run the app

## Testing

```bash
./gradlew test
./gradlew connectedAndroidTest
```

## Build

```bash
./gradlew build
./gradlew assembleDebug
./gradlew assembleRelease
```

## License

MIT License
