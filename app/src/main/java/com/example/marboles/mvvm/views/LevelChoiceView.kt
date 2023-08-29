package com.example.marboles.mvvm.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marboles.GameState
import com.example.marboles.LocalNavController
import com.example.marboles.mvvm.viewModels.LevelStatus
import com.example.marboles.mvvm.viewModels.LevelViewModel
import com.example.marboles.mvvm.viewModels.SensorViewModel


// Levelauswahl
@Composable
fun LevelChoiceScreen (
    onClickLevelScreen: () -> Unit,
    levelViewModel: LevelViewModel,
    sensorViewModel : SensorViewModel
) {
    val levelStatusList by levelViewModel.levelStatusList.observeAsState(emptyList())
    val currentLevel by sensorViewModel.levelNumber.observeAsState()

    levelViewModel.unlockLevel(1)

    val navController = LocalNavController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.85f),
                shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(100.dp, 30.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 30.dp, 0.dp, 50.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MenuTitle(label = "Level")
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        levelStatusList.forEach { levelStatus ->
                            LevelButton(
                                levelStatus = levelStatus,
                                onLevelClicked = {
                                    sensorViewModel.setLevelManually(levelStatus.levelNumber)
                                    println("Level has been set manually to $currentLevel")
                                    // navController?.navigate("game")
                                    onClickLevelScreen()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LevelButton(
    levelStatus: LevelStatus,
    onLevelClicked: () -> Unit
) {
    val textColor = if (levelStatus.isUnlocked) Color.Black else Color.LightGray
    TextButton(
        onClick = onLevelClicked,
        modifier = Modifier,
        enabled = levelStatus.isUnlocked
    ) {
        Text(
            text = "${levelStatus.levelNumber}",
            fontSize = 20.sp,
            color = textColor
        )
    }
}

@Composable
fun LevelScreen(
    levelViewModel: LevelViewModel,
    sensorViewModel : SensorViewModel,
    levelNumber : Int,
    onClickGame : () -> Unit,
    onClickHighscore : () -> Unit,
    onClickHome : () -> Unit,
    onClickLevelScreen : () -> Unit
){
    val levelStatusList by levelViewModel.levelStatusList.observeAsState(emptyList())
    val currentLevel by sensorViewModel.levelNumber.observeAsState()

    val nextColour = if(levelStatusList[currentLevel!! + 1].isUnlocked) Color.Black else Color.LightGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.85f),
                shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(80.dp, 30.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        MenuTitle(label = "Level $currentLevel")
                    }

                    if((levelNumber - 1) > 0){ // If Lvl1 dont even render previous
                        Row(horizontalArrangement = Arrangement.End) {
                            TextButton(
                                onClick = {
                                    sensorViewModel.setLevelManually(levelNumber - 1)
                                    println("Level has been set manually to $currentLevel")
                                    onClickLevelScreen()
                                },
                                modifier = Modifier,
                            ) {
                                Text(
                                    text = "<",
                                    fontSize = 50.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }

                    // Back Button
                    Row(horizontalArrangement = Arrangement.Start) {
                        TextButton(
                            onClick = {
                                sensorViewModel.setLevelManually(levelNumber + 1)
                                println("Level has been set manually to $currentLevel")
                                onClickLevelScreen()
                            },
                            modifier = Modifier,
                            enabled = levelStatusList[currentLevel!! + 1].isUnlocked
                        ) {
                            Text(
                                text = ">",
                                fontSize = 50.sp,
                                color = nextColour
                            )
                        }
                    }

                    // Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Play Button
                        Button(
                            modifier = Modifier
                                .width(150.dp)
                                .height(80.dp)
                                .padding(10.dp),
                            onClick = {
                                sensorViewModel.gameState.value = GameState.INGAME
                                onClickGame()
                            }
                        ) {
                            Text(text = "Game", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }

                        // Score Button
                        Button(
                            modifier = Modifier
                                .width(150.dp)
                                .height(80.dp)
                                .padding(10.dp),
                            onClick = {
                                onClickHighscore()
                            }
                        ) {
                            Text(text = "Score", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }

                        // Home Button
                        Button(
                            modifier = Modifier
                                .width(150.dp)
                                .height(80.dp)
                                .padding(10.dp),
                            onClick = {
                                onClickHome()
                            }
                        ) {
                            Text(text = "Home", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
