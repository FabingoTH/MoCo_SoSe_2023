package com.example.marboles.mvvm.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay

class GameViewModel() {

    private val _isPaused by lazy { MutableLiveData<Boolean>() }
    val isPaused: LiveData<Boolean> = _isPaused

    // Zeit in Sekunden
    private val _time by lazy { MutableLiveData<Int>() }
    val time: LiveData<Int> = _time

    init {
        _isPaused.value = false
        _time.value = 0
    }

    // TIMER LOGIC
    fun formatTimer(seconds: Int = _time.value!!): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return "%02d:%02d".format(minutes, remainingSeconds)
    }

    suspend fun timer() {
        while (true) {
            delay(1000) // Verzögerung von 1 Sekunde

            if (!_isPaused.value!!) {
                _time.value = _time.value!! + 1
            }
        }
    }

    fun changePausedState() {
        _isPaused.value = !_isPaused.value!!
    }

    fun resetTimer() {
        _time.value = 0
    }
}