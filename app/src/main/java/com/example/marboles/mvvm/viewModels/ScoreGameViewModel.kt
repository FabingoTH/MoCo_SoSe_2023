package com.example.marboles.mvvm.viewModels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marboles.database.Highscore
import com.example.marboles.database.HighscoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar


class ScoreGameViewModel(context: Context) : ViewModel() {

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

    // Current Date
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1 // Beachten Sie, dass die Monate von 0 bis 11 nummeriert sind, daher +1
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val currentDate = "$day.$month.$year"

    // Time formatter
    fun formatTimer(seconds: Int = _time.value!!): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return "%02d:%02d".format(minutes, remainingSeconds)
    }

    //////////////////////////
    // Score Database logic //
    //////////////////////////

    private val highscoreRepository = HighscoreRepository(context)

    private val _highscores = MutableLiveData<List<Highscore>?>()
    val highscores: LiveData<List<Highscore>?> = _highscores

    init {
        loadHighscores()
    }

    private fun loadHighscores() {
        viewModelScope.launch {
            _highscores.value = withContext(Dispatchers.IO) {
                highscoreRepository.getAllHighscores()
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun addHighscore() {
        val intScore : Int = _time.value ?: 0
            viewModelScope.launch {
            highscoreRepository.addHighscore(Highscore(date = currentDate, score = intScore))
            loadHighscores() // Aktualisierung der Highscores nach dem Einfügen
        }
    }

    fun deleteAllHighscores() {
        viewModelScope.launch {
            highscoreRepository.deleteAllHighscores()
            loadHighscores()
        }
    }

}