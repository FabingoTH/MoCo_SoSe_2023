package com.example.marboles.gamemanager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.example.marboles.LocalNavController
import com.example.marboles.TopBar

// GAMESCREEN
@Composable
fun BallScreen(viewModel : SensorViewModel) {
    val ballCoordinates by viewModel.ballCoordinates.observeAsState(Offset.Zero)
    val navController = LocalNavController.current

    Box(modifier = Modifier.zIndex(100f)) {
        TopBar()
    }

    // Playing Field = Screen Size
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    )
    {
        HoleView()
        Goal(160f, 135f)
        Ball(Modifier, ballCoordinates)
        WallView()


    }
}
