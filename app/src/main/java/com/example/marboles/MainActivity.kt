package com.example.marboles

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marboles.ui.theme.MarbolesTheme
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.example.marboles.mvvm.views.ScoreView
import com.example.marboles.mvvm.viewModels.*
import com.example.marboles.mvvm.views.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // ViewModels initialisieren, um sie mit dem Views zu verwenden
        val sensorViewModel = SensorViewModel(this)
        val levelViewModel = LevelViewModel(this)
        val scoreGameViewModel = ScoreGameViewModel(this)


        // FORCE FULLSCREEN
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // LOCK SCREEN TO LANDSCAPE MODE
            LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

            MarbolesTheme {
                WoodImage()
                val navController: NavHostController = rememberNavController()

                // im NavigationManager wird home automatisch als erste(r) screen/view aufgerufen
                NavigationManager(
                    navController,
                    levelViewModel,
                    sensorViewModel,
                    scoreGameViewModel
                )
            }
        }
    }
}


@Composable
fun NavigationManager(
    navController: NavHostController,
    levelViewModel: LevelViewModel,
    sensorViewModel: SensorViewModel,
    scoreGameViewModel: ScoreGameViewModel
) {
    var currentLevel = sensorViewModel.levelNumber.observeAsState()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    { navController.navigate("game") },
                    { navController.navigate("level") },
                    sensorViewModel,
                    scoreGameViewModel
                )
            }
            composable("score") {
                val currentLevel = sensorViewModel.levelNumber.observeAsState()
                ScoreView(scoreGameViewModel, currentLevel.value ?: 1)
            }
            composable("level") { LevelChoiceScreen(
                { navController.navigate("levelscreen") },
                levelViewModel,
                sensorViewModel) }
            composable("game") {
                val gameState by sensorViewModel.gameState.observeAsState(GameState.INGAME)
                val playEffectKey = "playingEffect"
                val winEffectKey = "winEffect"
                val gameoverEffectKey = "gameoverEffect"

                when(gameState) {
                    GameState.INGAME, GameState.PAUSED -> {
                        BallScreen(
                            sensorViewModel,
                            scoreGameViewModel,
                            { navController.navigate("home") },
                            { navController.navigate("score") },
                            currentLevel.value!!
                        )
                    }
                    GameState.WON -> {
                        // FIX: weil der SensorManager das Level schon direkt auf das nächste Level setzt,
                        // muss beim Datenbankeintrag mit -1 gerechnet werden. Muss eventuell angepasst werden.
                        scoreGameViewModel.addHighscoreForLevel(currentLevel.value!! - 1)

                        LaunchedEffect(key1 = winEffectKey) {

                            levelViewModel.unlockLevel(currentLevel.value!!)
                            navController.navigate("win")
                        }
                    }
                    GameState.GAMEOVER -> {
                        LaunchedEffect(key1 = gameoverEffectKey) {
                            navController.navigate("gameover")
                        }
                    }
                }
            }
            composable("gameover") {
                GameOverScreen(
                    scoreGameViewModel,
                    { navController.navigate("home") },
                    { navController.navigate("game") },
                    sensorViewModel
                )
            }
            composable("win") {
                WinScreen(scoreGameViewModel,
                    sensorViewModel,
                    { navController.navigate("home") },
                    { navController.navigate("game") },
                )
            }

            composable("levelscreen") {
                LevelScreen(
                    levelViewModel,
                    sensorViewModel,
                    scoreGameViewModel,
                    currentLevel.value!!,
                    { navController.navigate("game") },
                    { navController.navigate("score") },
                    { navController.navigate("home") },
                    { navController.navigate("levelscreen")}
                )
            }
        }
    }
}


// Init Local NavController
val LocalNavController = staticCompositionLocalOf<NavController?> { null }


@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current

    DisposableEffect(orientation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}