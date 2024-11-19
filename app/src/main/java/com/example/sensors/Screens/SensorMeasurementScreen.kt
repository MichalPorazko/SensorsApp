package com.example.sensors.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.example.sensors.Navigation.SharedViewModel

@Composable
fun SensorMeasurementScreen(
    sensorType: Int,
    onMeasurementFinished: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    val context = LocalContext.current

    var isMeasuring by remember { mutableStateOf(true) }

    /**
     * Runs Immediately
     *
     * ensures taht the screen measurement starts at the time when the screen is shown
     *  ensures that sharedViewModel.startMeasurement(context, sensorType) is called once when the screen is shown.
     * */
    LaunchedEffect(Unit) {
        sharedViewModel.startMeasurement(context, sensorType)
    }

    /**
     * is Meant for Cleanup
     *
     * ensures that when the screen is no longer visible,
     * the measurement stops
     *
     * DisposableEffect(Unit) ensures that sharedViewModel.stopMeasurement()
     * is called when the screen is no longer visible (e.g., when navigating back
     *
     * f the user stops the measurement via the button
     * DisposableEffect won t call stopMeasurement() again beaucse the screen is already disposed
     * */
    DisposableEffect(Unit) {
        onDispose {
            sharedViewModel.stopMeasurement()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Measuring...")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            isMeasuring = false
            sharedViewModel.stopMeasurement()
            onMeasurementFinished()
        }) {
            Text(text = "Stop Measurement")
        }
    }
}

