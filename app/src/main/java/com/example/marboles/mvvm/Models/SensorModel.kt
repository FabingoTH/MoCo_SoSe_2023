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
import com.example.marboles.GameState
import com.example.marboles.mvvm.*
import com.example.marboles.mvvm.viewModels.SensorViewModel

// MODEL
class SensorModel (private val sensorManager : SensorManager) : SensorEventListener {

    private val accelerometerSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    var ballRadius = 25f

    var xTilt = 0f
    var yTilt = 0f

    var newX = 10f
    var newY = 150f

    private val startPosition = Offset(-320f, 150f)
    var coordinates = startPosition

    private var mRotationMatrix = FloatArray(9)

    init {
        accelerometerSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
        Resources.getSystem().displayMetrics.density
    }

    private val _accelerometerData = MutableLiveData<Offset>()
    val accelerometerData : LiveData<Offset> = _accelerometerData // Read-only

    private val _levelNumber = MutableLiveData<Int>(1)
    val levelNumber : LiveData<Int> = _levelNumber

    private val _gameState = MutableLiveData<GameState>(GameState.INGAME)
    val gameState : LiveData<GameState> = _gameState

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            SensorManager.getRotationMatrixFromVector(mRotationMatrix, event.values)

            val remappedRotationMatrix = FloatArray(9)
            SensorManager.remapCoordinateSystem(
                mRotationMatrix,
                SensorManager.AXIS_X, SensorManager.AXIS_Y, remappedRotationMatrix
            )

            val orientationAngles = FloatArray(3)
            SensorManager.getOrientation(remappedRotationMatrix, orientationAngles)

            // Werte aus dem Sensor
            xTilt = -orientationAngles[1]
            yTilt = -orientationAngles[2]

            updateCoordinates()
        }
    }

    private fun updateCoordinates() {
        // Werte aus dem Sensor werden auf die alten Koordinaten addiert
        val oldX = coordinates.x
        val oldY = coordinates.y

        val ballSpeed = 12
        newX = oldX + xTilt * ballSpeed
        newY = oldY + yTilt * ballSpeed

        if (newX < -345f) { newX = -345f }
        if (newX > 345f) { newX = 345f }

        if (newY < -155f) { newY = -155f }
        if (newY > 155f) { newY = 155f }

        // Loopen durch alle Wände
        var collision : Pair<Float, Float> = Pair(startPosition.x, startPosition.y)

        when (levelNumber.value) {
            1 -> buildLevel (
                wallsLevelOne,
                holesLevelOne,
                levelOneGoal.centerX, levelOneGoal.centerY,
                collision,
                oldX, oldY
            )

            2 -> buildLevel (
                wallsLevelTwo,
                holesLevelTwo,
                levelTwoGoal.centerX, levelTwoGoal.centerY,
                collision,
                oldX, oldY
            )

            3 -> buildLevel (
                wallsLevelThree,
                holesLevelThree,
                levelThreeGoal.centerX, levelThreeGoal.centerY,
                collision,
                oldX, oldY
            )

            4 -> buildLevel (
                wallsLevelFour,
                holesLevelFour,
                levelFourGoal.centerX, levelFourGoal.centerY,
                collision,
                oldX, oldY
            )

            5 -> buildLevel (
                wallsLevelFive,
                holesLevelFive,
                levelFiveGoal.centerX, levelFiveGoal.centerY,
                collision,
                oldX, oldY
            )
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

    private fun checkGoalCollision(
        oldX: Float,
        oldY: Float,
        centerX : Float,
        centerY : Float
    ): Boolean {

        val goalLeftX = centerX - 30f
        val goalRightX = centerX + 30f
        val goalTopY = centerY - 30f
        val goalBottomY = centerY + 30f

        val horizontalX = Range.create(goalLeftX, goalRightX)
        val verticalY = Range.create(goalTopY, goalBottomY)
        var collisionDetected = false

        if (horizontalX.contains(newX) && verticalY.contains(newY)) {

            if (horizontalX.contains(newX)) {
                collisionDetected = true
            }

            if (verticalY.contains(newY)) {
                collisionDetected = true
            }
        }
        return collisionDetected
    }
    fun resetBallCoordinates(){
        coordinates = startPosition
        _accelerometerData.value = coordinates
    }

    fun changeGameState(state : String){
        when(state){
            "gameover" -> _gameState.value = GameState.GAMEOVER
            "won" -> _gameState.value = GameState.WON
            "ingame" -> _gameState.value = GameState.INGAME
            "paused" -> _gameState.value = GameState.PAUSED
            else -> throw Exception("Ungültiger Gamestate beim Aufruf von changeGameState")
        }
    }

    private fun buildLevel(wallList : List<Wall>, holeList : List<Hole>,
                           goalCenterX : Float, goalCenterY : Float,
                           collision : Pair<Float, Float>,
                           oldX: Float, oldY: Float)
    {
        for(wall in wallList){
            var localCollision = collision
            localCollision = checkCollision (
                oldX, oldY, newX, newY,
                wall.wallLeftX, wall.wallRightX, wall.wallTopY, wall.wallBottomY
            )
            newX = localCollision.first
            newY = localCollision.second
        }

        for(hole in holeList) {
            if(checkGoalCollision(newX, newY, hole.centerX, hole.centerY)) {
                _gameState.value = GameState.GAMEOVER
            }
        }

        if(checkGoalCollision(newX, newY, goalCenterX, goalCenterY)){
            if(_gameState.value != GameState.WON){
                _gameState.value = GameState.WON
                _levelNumber.value = _levelNumber.value!! + 1 // Wenn Win, dann Level erhöhen
            }
        }
    }

    fun setLevel(number : Int) {
        _levelNumber.value = number
    }

    // Brauchen wir in diesem Fall nicht
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }
}


