package com.example.tictactoe.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tictactoe.navigation.Player

@Composable
fun GameScreen(navController: NavController, offlineMode: Boolean) {
    // Make the board a mutable state list for reactivity
    val board = remember { mutableStateListOf(
        mutableStateListOf(Player.NONE, Player.NONE, Player.NONE),
        mutableStateListOf(Player.NONE, Player.NONE, Player.NONE),
        mutableStateListOf(Player.NONE, Player.NONE, Player.NONE)
    ) }

    var currentPlayer by remember { mutableStateOf(Player.X) }
    var gameOver by remember { mutableStateOf(false) }
    var winner by remember { mutableStateOf<Player?>(null) }

    // Function to check if the game has a winner or if it's a draw
    fun checkGameStatus(): Player? {
        for (i in 0..2) {
            if (board[i][0] != Player.NONE && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return board[i][0]
            if (board[0][i] != Player.NONE && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return board[0][i]
        }
        if (board[0][0] != Player.NONE && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return board[0][0]
        if (board[0][2] != Player.NONE && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return board[0][2]

        // If all cells are filled and no winner, it's a draw
        return if (board.all { row -> row.all { cell -> cell != Player.NONE } }) Player.NONE else null
    }

    // Function to handle a player's move
    fun playerMove(row: Int, col: Int) {
        if (board[row][col] == Player.NONE && !gameOver) {
            board[row][col] = currentPlayer
            currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
            winner = checkGameStatus()
            gameOver = winner != null
        }
    }

    // AI move function (for offline mode)
    fun aiMove() {
        if (currentPlayer == Player.O && !gameOver) {
            for (i in 0..2) {
                for (j in 0..2) {
                    if (board[i][j] == Player.NONE) {
                        board[i][j] = Player.O
                        currentPlayer = Player.X
                        winner = checkGameStatus()
                        gameOver = winner != null
                        return
                    }
                }
            }
        }
    }

    // Automatically handle AI move in offline mode
    LaunchedEffect(board) {
        if (!gameOver && currentPlayer == Player.O && offlineMode) {
            aiMove()
        }
    }

    // Column layout for the game UI
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (offlineMode) "Tic Tac Toe - Offline" else "Tic Tac Toe - Online",
            style = TextStyle(fontSize = 24.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (gameOver) {
            // Show result and options to restart or go back
            winner?.let {
                Text(text = when (it) {
                    Player.X -> "Player X wins!"
                    Player.O -> "Player O wins!"
                    Player.NONE -> "It's a Draw!"
                })
            }
            Button(onClick = {
                // Reset the game
                for (i in 0..2) {
                    for (j in 0..2) {
                        board[i][j] = Player.NONE
                    }
                }
                currentPlayer = Player.X
                gameOver = false
                winner = null
            }) {
                Text("Restart Game")
            }
            Button(onClick = {
                // Navigate back to Main Screen after game over
                navController.navigate("main") {
                    popUpTo("main") { inclusive = true }
                }
            }) {
                Text("Back to Main Menu")
            }
        } else {
            // Display the board with clickable grid buttons
            Column {
                for (i in 0..2) {
                    Row {
                        for (j in 0..2) {
                            Button(
                                onClick = { playerMove(i, j) },
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(80.dp)
                            ) {
                                Text(
                                    text = when (board[i][j]) {
                                        Player.X -> "X"
                                        Player.O -> "O"
                                        else -> ""
                                    },
                                    style = TextStyle(fontSize = 24.sp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
