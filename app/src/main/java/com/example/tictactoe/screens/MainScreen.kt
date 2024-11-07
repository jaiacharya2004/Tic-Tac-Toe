package com.example.tictactoe.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(navController: NavController) {
    // Layout for MainScreen with options to navigate to offline or online mode
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Tic Tac Toe", style = androidx.compose.ui.text.TextStyle(fontSize = 24.sp))

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navController.navigate("offline") }) {
            Text(text = "Play Offline")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("online") }) {
            Text(text = "Play Online")
        }
    }
}
