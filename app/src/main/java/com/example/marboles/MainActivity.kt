package com.example.marboles

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity


var xPos = 0f
var xVel = 0f
var xAccel = 0f

var yPos = 0f
var yVel = 0f
var yAccel = 0f

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager : SensorManager
    private lateinit var newBall : BallView
    private lateinit var newWall : WallView

    // Das hier werden die Seiten über die der Ball nicht rauslaufen soll
    // Sieht bei mir aber noch nicht ganz richtig aus, kA schaut mal bei euch
    var xMax = 0f
    var yMax = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        // Sensor der die "Neig-Werte" dokumentiert
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        var orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_GAME)

        // Größe des Displays berechnen - Scheint noch nicht 100% zu funktionieren?
        var displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics) // Veraltet?
        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels

        xMax = width - 100f
        yMax = height - 100f

        val frameLayout = FrameLayout(this)
        // Hier kommt der BALL
        newBall = BallView(this, xMax, yMax)
        frameLayout.addView(newBall)

        // Hier eine Wand zum testen
        newWall = WallView(this, 250f, 100f, 300f, 450f)
        frameLayout.addView(newWall)

        setContentView(frameLayout)
    }

    override fun onStart() {
        super.onStart()
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // Hier muss anscheinend der SensorListener noch mal neu registriert werden...
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME)
    }

    // ...und hier ungeregistered werden (Schätze weil er bei onStop nicht mehr benötigt wird)
    override fun onStop(){
        super.onStop()
        sensorManager.unregisterListener(this)
    }

    // Hier alles was mit dem Ball zu tun hat
    class BallView(context: Context, val xMax: Float, val yMax: Float) : View(context) {

        val ballPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        var ballX = 0f
        var ballY = 0f
        var screenWidth = xMax
        var screenHeight = yMax

        init {
            ballPaint.color = Color.RED
            ballPaint.style = Paint.Style.FILL
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.drawCircle(ballX, ballY, 50f, ballPaint)
        }

        // Das hier wird aufgerufen wenn die Bildschirmgröße fertig berechnet wurde?
        // w, h, oldw usw. sind anscheinend vorgegeben
        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(w, h, oldw, oldh)
            screenWidth = xMax
            screenHeight = yMax
            ballX = screenWidth / 2f // Durch 2 = Mitte
            ballY = screenHeight / 2f
        }

        // Hier wird der Ball bewegt
        fun setBallCoordinates(newBallX : Float, newBallY : Float){
            ballX = newBallX
            ballY = newBallY
            invalidate() // Wahrscheinlich die wichtigste Funktion im Code
                        // Resettet die Grafik bei jedem Aufruf um die neue Position darzustellen
        }
    }

    class WallView(context : Context, val left : Float, val top : Float, val right : Float, val bottom : Float) : View(context) {
        val wallPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        init {
            wallPaint.color = Color.GREEN
            wallPaint.style = Paint.Style.FILL
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.drawRect(left, top, right, bottom, wallPaint)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // TODO I guess, braucht man hier eigentlich nicht aber muss halt overridden werden
        return
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                // Negativ wegen Portait Mode zu Landscape Konvertierung
                val x = -event.values[1]
                val y = event.values[0]

                val newX = newBall.ballX - x * 5 // 5 = Speed, kann auch kleiner sein (oder größer)
                val newY = newBall.ballY + y * 5

                // Ist der Ball noch im Screen? Sonst -> Do nothing
                var inBounds = (newX > 0 && newX < xMax && newY > 0 && newY < yMax)
                var noCollision = (!collisionDetected(newBall, newX, newY, newWall))

                if (inBounds && noCollision) {
                    newBall.setBallCoordinates(newX, newY)
                }
            }
        }
    }

    fun collisionDetected(view1 : View, x1 : Float, y1 : Float, view2 : View) : Boolean {
	// Hier wird die "Hitbox" der Kreises als Quadrat abgespeichert damit wir es gleich mit der Wand vergleichen können
        val rect1 = Rect()
        view1.getHitRect(rect1)
        rect1.offset(x1.toInt(), y1.toInt())

	// Hier wird die "Hitbox" der Wand direkt als Boundbox abgespeichert
	// Variable müsste vllcht nochmal umbenannt werden
        val boundBox2 = Rect(newWall.left.toInt(), newWall.top.toInt(),
                            newWall.right.toInt(), newWall.bottom.toInt())


        return rect1.intersect(boundBox2)
    }
}