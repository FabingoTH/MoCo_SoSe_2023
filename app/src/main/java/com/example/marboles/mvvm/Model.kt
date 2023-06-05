package com.example.marboles.mvvm

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController

// Provisorische Größen
val playingFieldWidth = 710.dp
val playingFieldHeight = 330.dp

// GAMESCREEN

@Composable
fun BallScreen(navController: NavController, viewModel : SensorViewModel) {
    val ballCoordinates by viewModel.ballCoordinates.observeAsState(Offset.Zero)

    // Konvertiere playingFieldHeight und Width zu Float um diese unten zu vergleichen
    val widthToFloat = with(LocalDensity.current) { playingFieldWidth.toPx() }
    val heightToFloat = with(LocalDensity.current) { playingFieldHeight.toPx() }

    // Maximum Ball X und Y
    val maxBallX = ballCoordinates.x.coerceIn(0f, widthToFloat)
    val maxBallY = ballCoordinates.y.coerceIn(0f, heightToFloat)

    // Playing Field
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent))
    {
        Box(modifier = Modifier
            .size(width = playingFieldWidth, height = playingFieldHeight)
            .border(BorderStroke(5.dp, Color.Black))
            .background(Color.Transparent)
        ) {
            Ball(Modifier, ballCoordinates)
        }
    }
}

// BALL
@Composable
fun Ball(modifier: Modifier = Modifier, coordinates : Offset) {
    Box(
        modifier = modifier
            .offset(coordinates.x.dp, coordinates.y.dp)
            .size(50.dp)
            .clip(CircleShape)
            .background(Color.Red)
    )
}

// WAND
@Composable
fun Wall(modifier : Modifier = Modifier, x : Dp, y : Dp, size : Dp) {
    Box(
        modifier = modifier
            .offset(x, y)
            .size(size)
            .background(Color.Black)
    )
}