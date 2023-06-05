package com.example.marboles.mvvm

import android.content.Context
import android.hardware.SensorManager
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

// VIEWMODEL

class SensorViewModel(context : Context) : ViewModel() {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensorModel = Sensor(sensorManager)

    val ballCoordinates : LiveData<Offset> = sensorModel.accelerometerData
}