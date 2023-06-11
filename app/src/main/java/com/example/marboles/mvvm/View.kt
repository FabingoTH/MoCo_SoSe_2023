package com.example.marboles.mvvm

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.marboles.R

// GAMESCREEN
@Composable
fun BallScreen(navController: NavController, viewModel : SensorViewModel) {
    val ballCoordinates by viewModel.ballCoordinates.observeAsState(Offset.Zero)

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent))
    {
        Ball(Modifier, ballCoordinates)
        // Hier zeichne ich alle Wände die in der Wall Liste sind
        for(wall in walls){
            WallView(wall.wallRightX, wall.wallLeftX, wall.wallTopY, wall.wallBottomY)
        }
    }
}

// BALL
@Composable
fun Ball(modifier: Modifier = Modifier, coordinates : Offset) {
    Image(
        modifier = Modifier
            .offset(coordinates.x.dp, coordinates.y.dp)
            .size(50.dp)
            .clip(CircleShape),
        painter = painterResource(id = R.drawable.marble),
        contentDescription = "Murmel",
        contentScale = ContentScale.Fit
    )
}

// WAND
// (Zu WallView umbenannt weil es sonst mit der Wall Klasse im Konflikt steht)
@Composable
fun WallView(rightX : Float, leftX : Float, topY : Float, bottomY : Float) {
    Canvas( modifier = Modifier ) {
        drawRect(
            color = Color.Black,
            size = Size(width = (leftX - rightX).dp.toPx(), height = (topY - bottomY).dp.toPx()),
            topLeft = Offset(x = leftX, y = topY)
        )
    }
}

@Composable
fun Goal(){
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(left = 110f, top = 50f) {
            drawCircle(Color.Red, radius = 25.dp.toPx())
        }
    }
}

