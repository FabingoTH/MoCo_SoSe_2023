package com.example.marboles.mvvm.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marboles.database.Highscore
import com.example.marboles.database.HighscoreRepository
import kotlinx.coroutines.Dispatchers
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