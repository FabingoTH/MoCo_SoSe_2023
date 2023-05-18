package com.example.marboles

import android.content.Context
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// VIEWMODEL

class SensorViewModel(context : Context) : ViewModel() {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensorModel = SensorModel(sensorManager)

    val ballCoordinates : LiveData<Offset> = sensorModel.accelerometerData
}