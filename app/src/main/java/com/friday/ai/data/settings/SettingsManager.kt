package com.friday.ai.data.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("friday_settings")

class SettingsManager(private val context: Context) {
    companion object {
        val AI_PROVIDER = stringPreferencesKey("ai_provider")
        val API_ENDPOINT = stringPreferencesKey("api_endpoint")
        val MODEL = stringPreferencesKey("model")
        val VOICE_ENABLED = stringPreferencesKey("voice_enabled")
        val SPEECH_RATE = stringPreferencesKey("speech_rate")
        val THEME = stringPreferencesKey("theme")
    }

    fun getAiProvider(): Flow<String> = context.dataStore.data.map { it[AI_PROVIDER] ?: "openai" }
    fun getApiEndpoint(): Flow<String> = context.dataStore.data.map { it[API_ENDPOINT] ?: "" }
    fun getModel(): Flow<String> = context.dataStore.data.map { it[MODEL] ?: "" }
    fun getVoiceEnabled(): Flow<Boolean> = context.dataStore.data.map { it[VOICE_ENABLED]?.toBoolean() ?: true }
    fun getSpeechRate(): Flow<Float> = context.dataStore.data.map { it[SPEECH_RATE]?.toFloatOrNull() ?: 1.0f }
    fun getTheme(): Flow<String> = context.dataStore.data.map { it[THEME] ?: "dark" }

    suspend fun setAiProvider(provider: String) = context.dataStore.edit { it[AI_PROVIDER] = provider }
    suspend fun setApiEndpoint(endpoint: String) = context.dataStore.edit { it[API_ENDPOINT] = endpoint }
    suspend fun setModel(model: String) = context.dataStore.edit { it[MODEL] = model }
    suspend fun setVoiceEnabled(enabled: Boolean) = context.dataStore.edit { it[VOICE_ENABLED] = enabled.toString() }
    suspend fun setSpeechRate(rate: Float) = context.dataStore.edit { it[SPEECH_RATE] = rate.toString() }
    suspend fun setTheme(theme: String) = context.dataStore.edit { it[THEME] = theme }
}
