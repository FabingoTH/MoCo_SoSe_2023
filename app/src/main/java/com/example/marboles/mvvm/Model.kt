package com.example.marboles.mvvm

import android.content.res.Resources
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Range
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// GAME VIEW
class SensorHandler (private val sensorManager : SensorManager) : SensorEventListener {
    private val accelerometerSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels // xMax
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels // yMax

    var ballRadius = 25f

    var xTilt = 0f
    var yTilt = 0f

    var newX = 10f
    var newY = 150f

    // Defaultposition, kann auch noch geändert werden
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

            // Werte aus dem Sensor
             // xTilt = event.values[0]
             // yTilt = -event.values[1]

            // Werte aus dem Sensor
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
        // Magische Nummern, Bound Check funktioniert aus irgendeinem Grund nur mit diesen Werten
        if (newX < -345f) {
            newX = -345f
        }
        if (newX > 345f) {
            newX = 345f
        }

        if (newY < -155f) {
            newY = -155f
        }
        if (newY > 155f) {
            newY = 155f
        }

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
            // Links box check für x musste ich auf 15f ändern für meinen screen
            if (oldX <= wallLeftX && leftRightX.contains(newX)) {
                newX = wallLeftX
            }
            // Rechts box check für x
            if (oldX >= wallRightX && leftRightX.contains(newX)) {
                newX = wallRightX
            }
            // Oben box check für y
            if (oldY >= wallTopY && topBottomY.contains(newY)) {
                newY = wallTopY
            }
            // Unten check für y, musste ich auf 150 für meinen screen ändern
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

    // neue Funktion checkCollisions, nicht fertig
    // Kommentar hier: Ich denke wenn du die Funktion checkCollision so hast wie oben,
    // dann brauchst du diese Funktion hier eigentlich nicht mehr. Ich lass sie aber
    // vorsichtshalber mal drin weil ich nicht 100% weiß was du damit vorhattest

    /*
    private fun checkCollisions(oldX: Float, oldY: Float, newXPos: Float, newYPos: Float): Pair<Float, Float> {
        for (wall in walls) {
            var newX = newXPos
            var newY = newYPos

            val leftRightX = Range.create(wall.wallLeftX, wall.wallRightX)
            val topBottomY = Range.create(wall.wallBottomY, wall.wallTopY)

            if (leftRightX.contains(newX) && topBottomY.contains(newY)) {
                // Links box check für x musste ich auf 15f ändern für meinen screen
                if (oldX <= wall.wallLeftX && leftRightX.contains(newX)) {
                    newX = wall.wallLeftX
                }
                // Rechts box check für x
                if (oldX >= wall.wallRightX && leftRightX.contains(newX)) {
                    newX = wall.wallRightX
                }
                // Oben box check für y
                if (oldY >= wall.wallTopY && topBottomY.contains(newY)) {
                    newY = wall.wallTopY
                }
                // Unten check für y, musste ich auf 150 für meinen screen ändern
                if (oldY <= wall.wallBottomY && topBottomY.contains(newY)) {
                    newY = wall.wallBottomY
                }
            }
        }
        return Pair(newX, newY)
    }
    */
}


