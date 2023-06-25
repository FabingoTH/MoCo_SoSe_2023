package com.example.marboles.mvvm.viewModels

import android.content.Context
import androidx.lifecycle.*
import com.example.marboles.database.Highscore
import com.example.marboles.database.HighscoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScoreViewModel(context: Context): ViewModel() {

    private val highscoreRepository = HighscoreRepository(context)

    private val _highscores = MutableLiveData<List<Highscore>?>()
    val highscores: LiveData<List<Highscore>?> = _highscores

    init {
        viewModelScope.launch {
            _highscores.value = withContext(Dispatchers.IO) {
                highscoreRepository.getAllHighscores()
            }
        }
    }

    fun addHighscore() {
        // test highscore
        val highscore = Highscore(date = "15.06.2023", score = 30)
        viewModelScope.launch {
            highscoreRepository.addHighscore(highscore)
        }
    }
}

// offene Frage: sollen wir das einfach mit scoreviewmodel zusammenfassen, da man die Zeit ja eh für die highscores braucht
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