package com.example.marboles.mvvm

import android.content.res.Resources
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay

// GAME VIEW
class SensorHandler (private val sensorManager : SensorManager) : SensorEventListener {
    private val accelerometerSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels // xMax
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels // yMax

    var xTilt = 0f
    var yTilt = 0f

    // Defaultposition, kann auch noch geändert werden
    var coordinates = Offset(80f, 30f)

    init {
        accelerometerSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
        Resources.getSystem().displayMetrics.density
    }

    private val _accelerometerData = MutableLiveData<Offset>()
    val accelerometerData: LiveData<Offset> = _accelerometerData // Read-only

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            // Werte aus dem Sensor
            xTilt = -event.values[1]
            yTilt = -event.values[0]
            updateCoordinates()
        }
    }

    private fun updateCoordinates() {
        // Werte aus dem Sensor werden auf die alten Koordinaten addiert
        var newX = coordinates.x
        var newY = coordinates.y

        val ballSpeed = 10
        newX += xTilt * ballSpeed
        newY += yTilt * ballSpeed

        // Checks, ob der Ball noch im Feld ist
        // Magische Nummern, Bound Check funktioniert aus irgendeinem Grund nur mit diesen Werten
        if(newX < -345f){ newX = -345f }
        if(newX > 345f){ newX = 345f }

        if(newY < -155f){ newY = -155f }
        if(newY > 155f){ newY = 155f }

        coordinates = Offset(newX, newY)
        _accelerometerData.value = coordinates
    }

    // Brauchen wir in diesem Fall nicht
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) { }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }
}