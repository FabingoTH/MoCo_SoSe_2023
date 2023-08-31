package com.example.marboles.mvvm.viewModels

import android.content.Context
import android.hardware.SensorManager
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.marboles.GameState
import com.example.marboles.mvvm.Models.SensorModel

// VIEWMODEL

class SensorViewModel(context : Context) : ViewModel() {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensorModel = SensorModel(sensorManager)

    // Informationen aus dem SensorModel
    val gameState : LiveData<GameState> = sensorModel.gameState
    val ballCoordinates : LiveData<Offset> = sensorModel.accelerometerData
    val levelNumber : LiveData<Int> = sensorModel.levelNumber

    fun resetGameState() {
        sensorModel.resetBallCoordinates()
    }

    fun changeGameState(state : String){
        sensorModel.changeGameState(state)
    }

    fun setLevelManually(number : Int) {
        sensorModel.setLevel(number)
    }
}