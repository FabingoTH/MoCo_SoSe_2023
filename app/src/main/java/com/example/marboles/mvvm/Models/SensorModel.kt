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
class SensorModel (private val sensorManager : SensorManager, private val sensorViewModel: SensorViewModel) : SensorEventListener {

    // kleine Notiz: in einer mvvm-Architektur ist im Model eig keine Referenz auf das VM (siehe Argumentliste) - hier ist das ja nur für das enum/die States
    // da, also nicht so tragisch, aber maybe können wir das nach nem zukünftigen merge einfach auf Model-Ebene packen um dann vom
    // VM darauf zuzugreifen. Damit wir uns nicht selber ins bein schießen damit, und gesagt wird, wir haben "mvvm nicht richtig implementiert" (:

    private val accelerometerSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels // xMax
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels // yMax

    var ballRadius = 25f

    var xTilt = 0f
    var yTilt = 0f

    var newX = 10f
    var newY = 150f

    // Setze Startposition
    //private val startPosition = Offset(10f, 150f)

    //Ersatz-Startposition, falls es mit dem Sensor was kaputt machen sollte
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
    val accelerometerData: LiveData<Offset> = _accelerometerData // Read-only

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

        // Checks, ob der Ball noch im Feld ist
        // Magische Nummern, Bound Check funktioniert bei mir nur mit diesen Werten
        // TODO: Statt Magic Numbers Screen Size berechnen
        if (newX < -345f) { newX = -345f }
        if (newX > 345f) { newX = 345f }

        if (newY < -155f) { newY = -155f }
        if (newY > 155f) { newY = 155f }

        // Loopen durch alle Wände
        var collision : Pair<Float, Float> = Pair(startPosition.x, startPosition.y)
        val levelNumberTest = 2 // TODO SEHR WICHTIG HIER IST DIESE 2 NUR EIN PLATZHALTER
        // TODO Das tatsächliche aktuelle Level muss noch gobal irgwndwo getrackt werden!

        when (levelNumberTest) {
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

            // LINKS
            if (oldX <= goalLeftX && horizontalX.contains(newX)) {
                collisionDetected = true
            }
            // RECHTS
            if (oldX >= goalRightX && horizontalX.contains(newX)) {
                collisionDetected = true
            }
            // OBEN
            if (oldY >= goalTopY && verticalY.contains(newY)) {
                collisionDetected = true
            }
            // UNTEN
            if (oldY <= goalBottomY && verticalY.contains(newY)) {
                collisionDetected = true
            }
        }
        return collisionDetected
    }
    fun resetBallCoordinates(){
        coordinates = startPosition
        _accelerometerData.value = coordinates
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
                sensorViewModel.gameState.value = GameState.GAMEOVER
            }
        }

        if(checkGoalCollision(newX, newY, goalCenterX, goalCenterY)){
            sensorViewModel.gameState.value = GameState.WON
        }
    }

    // Brauchen wir in diesem Fall nicht
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }
}


