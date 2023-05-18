package com.example.marboles.mvvm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.navigation.NavController
import com.example.marboles.Ball

// GAME VIEW

@Composable
fun BallScreen(navController: NavController, viewModel : SensorViewModel) {
    val ballCoordinates by viewModel.ballCoordinates.observeAsState(Offset.Zero)
    Ball(Modifier, ballCoordinates)

    val offset by viewModel.ballCoordinates.observeAsState()
}