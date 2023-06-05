package com.example.marboles.mvvm

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// GAME VIEW

class Sensor (private val sensorManager : SensorManager) : SensorEventListener {
    private val accelerometerSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    // private var coordinates = Offset(playingFieldWidth / 2, playingFieldHeight / 2)
    // Default- bzw. Startposition

    // Warum Init? Blicke nicht so ganz durch aber scheint zu funktionieren.
    init {
        accelerometerSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    private val _accelerometerData = MutableLiveData<Offset>()
    val accelerometerData: LiveData<Offset> = _accelerometerData // Read-only

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val x = event.values[1] * 2 // * 2 wegen Geschwindigkeit, scheint mit Offset schneller?
            val y = event.values[0] * 2
            coordinates += Offset(x, y)
            _accelerometerData.value = coordinates
        }
    }

    // Brauchen wir in diesem Fall nicht
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) { }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }
}