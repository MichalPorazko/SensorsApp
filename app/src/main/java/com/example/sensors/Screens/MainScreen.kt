package com.example.sensors.Screens

import android.hardware.Sensor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import com.example.sensors.SensorData.SensorResult

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(
    sensorResults: List<SensorResult>,
    sensorFunction: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Instruction Text
        Text(
            text = "Choose a sensor for measurement",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Buttons for Sensors
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    sensorFunction(Sensor.TYPE_ACCELEROMETER)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Accelerometer")
            }
            Button(
                onClick = { sensorFunction(Sensor.TYPE_GYROSCOPE) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Gyroscope")
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Table Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Sensor", modifier = Modifier.weight(1f))
            Text(text = "Max X", modifier = Modifier.weight(1f))
            Text(text = "Max Y", modifier = Modifier.weight(1f))
            Text(text = "Max Z", modifier = Modifier.weight(1f))
        }

        HorizontalDivider()

        // Table Data
        LazyColumn {
            items(sensorResults) { result ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = result.sensorName, modifier = Modifier.weight(1f))
                    Text(
                        text = result.maxValues.getOrNull(0)?.toString() ?: "-",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = result.maxValues.getOrNull(1)?.toString() ?: "-",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = result.maxValues.getOrNull(2)?.toString() ?: "-",
                        modifier = Modifier.weight(1f)
                    )
                }
                HorizontalDivider()
            }
        }
    }
}

