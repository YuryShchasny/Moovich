package com.sb.moovich.core.base

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MySpeechRecognize(context: Context) {
    private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private val recognitionIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
    }
    private val mediaPlayer by lazy {
        MediaPlayer().apply {
            setDataSource(context.assets.openFd("mic_activated.wav"))
            setVolume(1f, 1f)
            prepare()
        }
    }

    private val partitionResultsState = MutableStateFlow("")
    private val resultsState = MutableStateFlow("")
    private val rmsState = MutableStateFlow(0f)
    private val scope = CoroutineScope(Dispatchers.IO)
    private var recording = false
    private var onEndOfSpeechListener: (() -> Unit)? = null
    private var onRmsChangedListener: ((Float) -> Unit)? = null
    private var onResultsListener: ((String) -> Unit)? = null
    private var onPartitionResultsListener: ((String) -> Unit)? = null


    init {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                recording = true
                mediaPlayer.seekTo(0)
                mediaPlayer.start()
            }

            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {
                rmsState.update { rmsdB }
            }

            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                onEndOfSpeechListener?.invoke()
                recording = false
            }

            override fun onError(error: Int) {
                onEndOfSpeechListener?.invoke()
                recording = false
            }

            override fun onResults(results: Bundle?) {
                val result = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                resultsState.update { result?.joinToString().toString() }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val parts = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                partitionResultsState.update { parts?.joinToString().toString() }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
        scope.launch {
            rmsState.collect {
                    value ->
                if (recording) {
                    withContext(Dispatchers.Main) {
                        onRmsChangedListener?.invoke(value)
                    }
                }
            }
        }
        scope.launch {
            resultsState.collect { value ->
                withContext(Dispatchers.Main) {
                    onResultsListener?.invoke(value)
                }
            }
        }
        scope.launch {
            partitionResultsState.collect { value ->
                withContext(Dispatchers.Main) {
                    onPartitionResultsListener?.invoke(value)
                }
            }
        }
    }

    fun setOnRmsChangedListener(onRmsChanged: (Float) -> Unit) {
        onRmsChangedListener = onRmsChanged
    }

    fun setOnResultsListener(onResults: (String) -> Unit) {
        onResultsListener = onResults
    }

    fun setOnPartitionResultsListener(onPartitionResults: (String) -> Unit) {
        onPartitionResultsListener = onPartitionResults
    }

    fun setOnEndOfSpeechListener(onEndOfSpeech: () -> Unit) {
        onEndOfSpeechListener = onEndOfSpeech
    }

    fun startRecord() {
        if(!recording) speechRecognizer.startListening(recognitionIntent)
    }

    fun destroy() {
        scope.cancel()
        speechRecognizer.destroy()
    }
}