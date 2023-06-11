package com.example.marboles.mvvm

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.marboles.LocalNavController
import com.example.marboles.MenuTitle
import com.example.marboles.R
import com.example.marboles.TopBar
import kotlinx.coroutines.delay

// GAMESCREEN
@Composable
fun BallScreen(viewModel : SensorViewModel) {
    val ballCoordinates by viewModel.ballCoordinates.observeAsState(Offset.Zero)
    val navController = LocalNavController.current

    // Playing Field = Screen Size
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopBar()
            }
        }

        Ball(Modifier, ballCoordinates)
        Wall(40f, 90f, 160f, 60f)
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
fun Wall(left : Float, right : Float, top : Float, bottom : Float) {
    Canvas( modifier = Modifier ) {
        val leftX = left
        val rightX = right
        val topY = top
        val bottomY = bottom

        drawRect(
            color = Color.Black,
            size = Size(width = rightX - leftX, height = bottomY - topY),
            topLeft = Offset(x = rightX, y = topY)
        )
    }
}

@Composable
fun ScoreTimer() {

}

@Composable
fun formatTimer(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}
