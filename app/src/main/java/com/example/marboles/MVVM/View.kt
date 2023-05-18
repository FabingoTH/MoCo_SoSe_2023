package com.example.marboles

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset

// GAME VIEW

@Composable
fun BallScreen(viewModel : SensorViewModel) {
    val ballCoordinates by viewModel.ballCoordinates.observeAsState(Offset.Zero)
    Ball(Modifier, ballCoordinates)

    val offset by viewModel.ballCoordinates.observeAsState()
}