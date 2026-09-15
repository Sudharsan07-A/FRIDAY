package com.friday.ai.data.voice

import android.content.Context
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import com.friday.ai.domain.voice.VoiceManager
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume

class AndroidVoiceManager(private val context: Context) : VoiceManager {
    private var speechRecognizer: SpeechRecognizer? = null
    private var tts: TextToSpeech? = null
    private var isListeningFlag = false
    private var isSpeakingFlag = false

    init {
        tts = TextToSpeech(context) { status ->
            if (status != TextToSpeech.SUCCESS) {
                Timber.e("TTS initialization failed")
            }
        }
    }

    override suspend fun startListening(onResult: (String) -> Unit, onError: (Exception) -> Unit) {
        suspendCancellableCoroutine { continuation ->
            try {
                if (!SpeechRecognizer.isRecognitionAvailable(context)) {
                    onError(Exception("Speech recognition not available"))
                    continuation.resume(Unit)
                    return@suspendCancellableCoroutine
                }

                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
                isListeningFlag = true

                speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                    override fun onReadyForSpeech(params: android.os.Bundle?) {}
                    override fun onBeginningOfSpeech() {}
                    override fun onRmsChanged(rmsdB: Float) {}
                    override fun onBufferReceived(buffer: ByteArray?) {}
                    override fun onEndOfSpeech() {}
                    override fun onError(error: Int) {
                        onError(Exception("Speech recognition error: $error"))
                        isListeningFlag = false
                        continuation.resume(Unit)
                    }

                    override fun onResults(results: android.os.Bundle?) {
                        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        if (!matches.isNullOrEmpty()) {
                            onResult(matches[0])
                        }
                        isListeningFlag = false
                        continuation.resume(Unit)
                    }

                    override fun onPartialResults(partialResults: android.os.Bundle?) {}
                    override fun onEvent(eventType: Int, params: android.os.Bundle?) {}
                })

                val intent = android.content.Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(android.speech.RecognizerIntent.EXTRA_MAX_RESULTS, 1)
                }
                speechRecognizer?.startListening(intent)
            } catch (e: Exception) {
                onError(e)
                isListeningFlag = false
                continuation.resume(Unit)
            }
        }
    }

    override suspend fun stopListening() {
        speechRecognizer?.stopListening()
        isListeningFlag = false
    }

    override suspend fun speak(text: String, onComplete: () -> Unit) {
        suspendCancellableCoroutine { continuation ->
            try {
                if (tts == null) {
                    onComplete()
                    continuation.resume(Unit)
                    return@suspendCancellableCoroutine
                }

                isSpeakingFlag = true
                tts?.setOnUtteranceProgressListener(object : android.speech.tts.UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {}
                    override fun onDone(utteranceId: String?) {
                        isSpeakingFlag = false
                        onComplete()
                        continuation.resume(Unit)
                    }
                    override fun onError(utteranceId: String?) {
                        isSpeakingFlag = false
                        continuation.resume(Unit)
                    }
                })
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utterance")
            } catch (e: Exception) {
                isSpeakingFlag = false
                Timber.e(e, "TTS error")
                continuation.resume(Unit)
            }
        }
    }

    override suspend fun stopSpeaking() {
        tts?.stop()
        isSpeakingFlag = false
    }

    override fun isListening(): Boolean = isListeningFlag
    override fun isSpeaking(): Boolean = isSpeakingFlag

    fun release() {
        speechRecognizer?.destroy()
        tts?.shutdown()
    }
}
