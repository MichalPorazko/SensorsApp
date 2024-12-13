package com.example.sensors.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController


@Composable
fun ProfileChooseScreen(navigate2Login: () -> Unit) {
    Column(
        // Layout code...
    ) {
        // UI elements...
        Button(onClick = {
            navigate2Login.invoke()
        }) {
            Text(text = "Pacjent")
        }
        Button(onClick =  {}  ) {
            Text(text = "Lekarz")
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileChooseScreenPreview() {
    rememberNavController()
    ProfileChooseScreen(navigate2Login = {  })
}