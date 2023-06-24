package com.example.marboles.gamemanager

import android.content.Context
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameStateViewModel(context : Context) : ViewModel() {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    // private val sensorModel = SensorHandler(sensorManager)
    // val gameState : LiveData<GameState> = sensorModel.gameState
}
