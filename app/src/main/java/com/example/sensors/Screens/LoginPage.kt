package com.example.sensors.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensors.Firebase.FirebaseRepository
import com.example.sensors.Navigation.SharedViewModel

@Composable
fun LoginPage(
    sharedViewModel: SharedViewModel,
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by sharedViewModel.authState.observeAsState()

    LaunchedEffect(authState) {
        if (authState is FirebaseRepository.AuthState.Authenticated) {
            onLoginSuccess()
        }
    }

    Column(
        // Layout code...
    ) {
        // UI elements...
        Button(onClick = {
            sharedViewModel.loginUser(email, password)
        }) {
            Text(text = "Login")
        }
        TextButton(onClick = onSignUpClick) {
            Text(text = "Don't have an account? Sign Up")
        }
    }
}
