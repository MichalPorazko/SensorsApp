package com.example.sensors.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeScreen(onDoctorSelected: () -> Unit, onPatientSelected: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onDoctorSelected,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("I am a Doctor")
        }
        Button(
            onClick = onPatientSelected,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("I am a Patient")
        }
    }
}