package com.example.marboles.mvvm.Models

import android.content.res.Resources
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Range
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marboles.mvvm.walls

// MODEL
class SensorModel (private val sensorManager : SensorManager) : SensorEventListener {
    private val accelerometerSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels // xMax
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels // yMax

    var ballRadius = 25f

    var xTilt = 0f
    var yTilt = 0f

    var newX = 10f
    var newY = 150f

    // Startposition
    var coordinates = Offset(10f, 150f)

    private var mRotationMatrix = FloatArray(16)

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
            SensorManager.getRotationMatrixFromVector(mRotationMatrix, event.values)

            // Rotationsmatrix, funktioniert immer aber bisher nur nach Osten
            xTilt = mRotationMatrix[1]
            yTilt = mRotationMatrix[0]

            updateCoordinates()
        }
    }

    private fun updateCoordinates() {
        // Werte aus dem Sensor werden auf die alten Koordinaten addiert
        val oldX = coordinates.x
        val oldY = coordinates.y

        val ballSpeed = 15
        newX = oldX + xTilt * ballSpeed
        newY = oldY + yTilt * ballSpeed

        // Checks, ob der Ball noch im Feld ist
        // Magische Nummern, Bound Check funktioniert bei mir nur mit diesen Werten
        // TODO: Statt Magic Numbers Screen Size berechnen
        if (newX < -345f) { newX = -345f }
        if (newX > 345f) { newX = 345f }

        if (newY < -155f) { newY = -155f }
        if (newY > 155f) { newY = 155f }

        // Loopen durch alle Wände
        var collision : Pair<Float, Float>
        for(wall in walls){
            collision = checkCollision(oldX, oldY, newX, newY, wall.wallLeftX, wall.wallRightX, wall.wallTopY, wall.wallBottomY)
            newX = collision.first
            newY = collision.second
        }

        // Neue Koordinaten festlegen
        coordinates = Offset(newX, newY)
        _accelerometerData.value = coordinates
    }

    // TODO Reset ballcoordinates

    private fun checkCollision(
        oldX: Float,
        oldY: Float,
        newXPos: Float,
        newYPos: Float,
        wallLeft : Float,
        wallRight : Float,
        wallTop: Float,
        wallBottom: Float
    ) : Pair<Float, Float> {
        var newX = newXPos
        var newY = newYPos

        val wallLeftX = wallLeft - ballRadius
        val wallRightX = wallRight + ballRadius
        val wallTopY = wallTop + ballRadius
        val wallBottomY = wallBottom - ballRadius

        val leftRightX = Range.create(wallLeftX, wallRightX)
        val topBottomY = Range.create(wallBottomY, wallTopY)

        if (leftRightX.contains(newX) && topBottomY.contains(newY)) {
            // LINKS
            if (oldX <= wallLeftX && leftRightX.contains(newX)) {
                newX = wallLeftX
            }
            // RECHTS
            if (oldX >= wallRightX && leftRightX.contains(newX)) {
                newX = wallRightX
            }
            // OBEN
            if (oldY >= wallTopY && topBottomY.contains(newY)) {
                newY = wallTopY
            }
            // UNTEN
            if (oldY <= wallBottomY && topBottomY.contains(newY)) {
                newY = wallBottomY
            }
        }
        return Pair(newX, newY)
    }


    // Brauchen wir in diesem Fall nicht
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }
}


