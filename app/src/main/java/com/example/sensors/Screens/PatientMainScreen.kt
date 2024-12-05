package com.example.sensors.Screens

import androidx.compose.runtime.Composable
import com.example.sensors.Navigation.SharedViewModel

@Composable
fun PatientMainScreen(
    sharedViewModel: SharedViewModel,
    onMeasurementSelected: (String) -> Unit, // Callback when a measurement is selected
    onLogout: () -> Unit // Callback for logging out
) {
    // Sample data for past measurements (in a real app, fetch from ViewModel/Database)
    val pastMeasurements = listOf(
        "Blood Pressure: 120/80 mmHg",
        "Dialysis Status: Normal",
        "Heart Rate: 72 bpm"
    )

    // UI Layout
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Patient Dashboard") },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text(text = "Logout", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Welcome Section
                Text(
                    text = "Welcome, Patient!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                // Current Health Measurements
                MeasurementOptionsSection(onMeasurementSelected)

                // Past Measurements
                PastMeasurementsSection(pastMeasurements)
            }
        }
    )
}

// Measurement Options Section
@Composable
fun MeasurementOptionsSection(onMeasurementSelected: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Perform Health Measurements",
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onMeasurementSelected("Blood Pressure") }) {
                Text(text = "Blood Pressure")
            }
            Button(onClick = { onMeasurementSelected("Dialysis Machine") }) {
                Text(text = "Dialysis Machine")
            }
            Button(onClick = { onMeasurementSelected("Heart Rate") }) {
                Text(text = "Heart Rate")
            }
        }
    }
}

// Past Measurements Section
@Composable
fun PastMeasurementsSection(measurements: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Past Measurements",
            style = MaterialTheme.typography.titleLarge
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(measurements) { measurement ->
                MeasurementCard(measurement)
            }
        }
    }
}

// Measurement Card Composable
@Composable
fun MeasurementCard(measurement: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.MonitorHeart,
                contentDescription = "Measurement Icon",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = measurement,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
