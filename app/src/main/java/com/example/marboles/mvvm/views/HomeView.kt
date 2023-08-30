package com.example.marboles.mvvm.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marboles.GameState
import com.example.marboles.mvvm.viewModels.ScoreGameViewModel
import com.example.marboles.mvvm.viewModels.SensorViewModel

@Composable
fun HomeScreen(onClickPlay: () -> Unit, onClickLevel: () -> Unit, sensorViewModel : SensorViewModel, gameViewModel : ScoreGameViewModel) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleText(title = "Marboles")
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(60.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        sensorViewModel.changeGameState("ingame")
                        // Quick Fix für Pause State Problem
                        if(gameViewModel.isPaused.value == true){
                            gameViewModel.changePausedState()
                        }
                        onClickPlay()
                    },
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent))

                {
                    Text(text = "Play", color = Color.Black, fontSize = 30.sp)
                }

                Button(
                    onClick = {
                        sensorViewModel.changeGameState("paused")
                        onClickLevel()
                    },
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent))

                {
                    Text(text = "Level", color = Color.Black, fontSize = 30.sp)
                }
            }
        }
    }
}

@Composable
fun TitleText(title : String) {
    Box(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(100.dp))
            .padding(50.dp, 10.dp)
    ) {
        Text(
            title.uppercase(),
            style = TextStyle(
                color = Color.Black,
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                letterSpacing = 20.sp
            )
        )
    }
}