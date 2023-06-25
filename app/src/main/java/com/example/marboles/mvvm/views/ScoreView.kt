package com.example.marboles.mvvm.Views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.marboles.database.Highscore
import com.example.marboles.mvvm.viewModels.ScoreViewModel
import com.example.marboles.mvvm.views.MenuTitle

import kotlinx.coroutines.launch

@Composable
fun ScoreScreen(scoreViewModel: ScoreViewModel) {

    val coroutineScope = rememberCoroutineScope()

    val highscoreList: List<Highscore>? by scoreViewModel.highscores.observeAsState()

    Button(
        modifier = Modifier
            .offset(80.dp, 80.dp)
            .zIndex(1f),
        onClick = {
            coroutineScope.launch {
                scoreViewModel.addHighscore() // normalerweise übergeben
            }
        }
    ) {
        Text(text = "Highscore hinzufügen", fontSize = 20.sp)
    }

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
                        MenuTitle(label = "Highscores")
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        if(highscoreList==null){
                            Text("no highscores yet")
                        } else {
                            for(element in highscoreList!!){
                                ScoreEntry(datum = element.date, score = element.score.toString())
                            }



                            }
                        }
                    }
                }
            }
        }
    }


@Composable
fun ScoreEntry(datum: String, score: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = datum, color = Color.Black, fontSize = 30.sp)
        Text(text = score, color = Color.Black, fontSize = 30.sp)
    }
}

