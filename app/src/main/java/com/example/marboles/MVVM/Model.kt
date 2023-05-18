package com.example.marboles

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// MODEL

class SensorModel (private val sensorManager : SensorManager) : SensorEventListener {
    private val accelerometerSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

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
            // Negativ wegen Portait Mode zu Landscape Konvertierung
            val x = -event.values[1]
            val y = event.values[0]
            val coordinates = Offset(x, y)
            _accelerometerData.value = coordinates
        }
    }

    // Brauchen wir in diesem Fall nicht
    override fun onAccuracyChanged(p0: android.hardware.Sensor?, p1: Int) { }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }
}