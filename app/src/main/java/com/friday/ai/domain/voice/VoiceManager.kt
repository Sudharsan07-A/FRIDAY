package com.friday.ai.domain.voice

interface VoiceManager {
    suspend fun startListening(onResult: (String) -> Unit, onError: (Exception) -> Unit)
    suspend fun stopListening()
    suspend fun speak(text: String, onComplete: () -> Unit = {})
    suspend fun stopSpeaking()
    fun isListening(): Boolean
    fun isSpeaking(): Boolean
}
