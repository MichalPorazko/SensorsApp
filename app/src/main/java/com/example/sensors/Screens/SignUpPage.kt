package com.example.sensors.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
fun SignUpPage(
    sharedViewModel: SharedViewModel,
    onSignUpSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    val email by remember { mutableStateOf("") }
    val password by remember { mutableStateOf("") }
    val userType by remember { mutableStateOf("Patient") }
    val authNumber by remember { mutableStateOf("") }
    val selectedDoctor by remember { mutableStateOf("") }

//    val authState by sharedViewModel.authState.observeAsState()
//
//    LaunchedEffect(authState) {
//        if (authState is FirebaseRepository.AuthState.Authenticated) {
//            onSignUpSuccess()
//        }
//    }

    Column(
        // Layout code...
    ) {
        // UI elements...
        Button(onClick = {
            val additionalInfo = mutableMapOf<String, String>()
            if (userType == "Doctor") {
                additionalInfo["authenticationNumber"] = authNumber
            } else {
                additionalInfo["doctorId"] = selectedDoctor
            }
            sharedViewModel.signUpUser(email, password, userType, additionalInfo)
        }) {
            Text(text = "Create Account")
        }
        TextButton(onClick = onLoginClick) {
            Text(text = "Already have an account? Login")
        }
    }
}
