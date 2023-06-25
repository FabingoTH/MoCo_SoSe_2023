package com.example.marboles.mvvm.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marboles.LocalNavController
import com.example.marboles.mvvm.viewModels.LevelStatus
import com.example.marboles.mvvm.viewModels.LevelViewModel


// Levelauswahl
@Composable
fun LevelChoiceScreen (levelViewModel: LevelViewModel) {
    val levelStatusList by levelViewModel.levelStatusList.observeAsState(emptyList())

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
                                onLevelClicked = { navController?.navigate("game") }
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