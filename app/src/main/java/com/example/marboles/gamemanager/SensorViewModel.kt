package com.example.marboles.gamemanager

import android.content.Context
import android.hardware.SensorManager
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// VIEWMODEL

class SensorViewModel(context : Context) : ViewModel() {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensorModel = SensorHandler(sensorManager, this)
    val gameState: MutableLiveData<GameState> = MutableLiveData(GameState.INGAME)

    val ballCoordinates : LiveData<Offset> = sensorModel.accelerometerData

    fun resetGameState() {
        gameState.value = GameState.INGAME
    }
}

// Level View
data class LevelStatus(val levelNumber: Int, val isUnlocked: Boolean)
class LevelViewModel(context: Context) : ViewModel() {
    private val _levelStatusList = MutableLiveData<List<LevelStatus>>()
    val levelStatusList: LiveData<List<LevelStatus>> get() = _levelStatusList

    init {
        val initialStatusList = List(6) { LevelStatus(it + 1, false) }
        _levelStatusList.value = initialStatusList
    }

    fun unlockLevel(levelNumber: Int) {
        val currentStatusList = _levelStatusList.value.orEmpty().toMutableList()
        if (levelNumber in 1..currentStatusList.size) {
            currentStatusList[levelNumber - 1] = currentStatusList[levelNumber - 1].copy(isUnlocked = true)
            _levelStatusList.value = currentStatusList
        }
    }
}
