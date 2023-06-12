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

    // Playing Field = Screen Size
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent))
    {
        Goal()
        Ball(Modifier, ballCoordinates)
        WallView()


    }
}

@Composable
fun Goal(){
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(left = 250f, top = 200f) {
            drawCircle(Color.DarkGray, radius = 30.dp.toPx())
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
@Composable
fun WallView() {
    Canvas( modifier = Modifier ) {

        // OG Wand
        drawRect(
            color = Color.Black,
            size = Size(width = 50.dp.toPx(), height = 150.dp.toPx()),
            topLeft = Offset(x = 40.dp.toPx(), y = 60.dp.toPx())
        )
        drawRect(
            color = Color.Red,
            size = Size(width = 250.dp.toPx(), height = 50.dp.toPx()),
            topLeft = Offset(x = 40.dp.toPx(), y = 10.dp.toPx())
        )
        drawRect(
            color = Color.Blue,
            size = Size(width = 290.dp.toPx(), height = 50.dp.toPx()),
            topLeft = Offset(x = -200.dp.toPx(), y = -40.dp.toPx())
        )
        drawRect(
            color = Color.Green,
            size = Size(width = 50.dp.toPx(), height = 50.dp.toPx()),
            topLeft = Offset(x = -160.dp.toPx(), y = 70.dp.toPx())
        )
    }
}




