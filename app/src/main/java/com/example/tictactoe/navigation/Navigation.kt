package com.example.tictactoe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tictactoe.screens.GameScreen
import com.example.tictactoe.screens.MainScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("offline") { GameScreen(navController, offlineMode = true) }
        composable("online") { GameScreen(navController, offlineMode = false) }
    }
}
