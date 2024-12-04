package com.example.sensors.Navigation

// SharedViewModel.kt
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.sensors.Firebase.FirebaseRepository
import com.example.sensors.SensorData.Accelerometer
import com.example.sensors.SensorData.Gyroscope
import com.example.sensors.SensorData.MeasurableSensor
import com.example.sensors.SensorData.SensorResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class SharedViewModel(private val firebaseRepository: FirebaseRepository) : ViewModel() {


    private val _sensorResults = MutableStateFlow<List<SensorResult>>(emptyList())
    val sensorResults: StateFlow<List<SensorResult>> = _sensorResults

    private var currentSensor: MeasurableSensor? = null
    private var currentSensorType: Int = 0


    private var isMeasuring = false
    private var maxValues = listOf<Float>()

    // for the sensors
    private val gravity = FloatArray(3) { 0f }
    private val linearAcceleration = FloatArray(3) { 0f }
    private val NS2S = 1.0f / 1000000000.0f
    private val deltaRotationVector = FloatArray(4) { 0f }
    private var timestamp: Float = 0f
    private val EPSILON = 0.000000001f

    fun startMeasurement(context: Context, sensorType: Int) {

        /**
         * if a user rpeatedly click this buttons twice, there will be no measurements running
         * in parallel just one
         *
         * */
        if (isMeasuring) return
        isMeasuring = true


        currentSensorType = sensorType
        maxValues = listOf(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE)

        currentSensor = when (sensorType) {
            android.hardware.Sensor.TYPE_ACCELEROMETER -> Accelerometer(context)
            android.hardware.Sensor.TYPE_GYROSCOPE -> Gyroscope(context)
            else -> null
        }

        currentSensor?.let { sensor ->

            /**
             * setOnSensorValuesChangedListener -> listen for NEw sensor values when they are updated
             *
             * whenever the sensor provides new data -> setOnSensorValuesChangedListener data to process_Data
             *
             * This keeps the sensor-specific details separate from other parts of the app (for example SensorViewModel)
             * */

            sensor.setOnSensorValuesChangedListener { values ->
                when (sensorType) {
                    android.hardware.Sensor.TYPE_ACCELEROMETER -> processAccelerometerData(values)
                    android.hardware.Sensor.TYPE_GYROSCOPE -> processGyroscopeData(values)
                }
            }
            sensor.startListening()
        }
    }

    fun stopMeasurement() {
        Log.d("SharedViewModel", "stopMeasurement called") // Log 1
        if (!isMeasuring) {
            Log.d("SharedViewModel", "Measurement already stopped") // Log 2
            return
        }
        isMeasuring = false

        currentSensor?.stopListening()
        Log.d("SharedViewModel", "Stopped listening to sensor: $currentSensor") // Log 3

        val sensorName = when (currentSensorType) {
            android.hardware.Sensor.TYPE_ACCELEROMETER -> "Accelerometer"
            android.hardware.Sensor.TYPE_GYROSCOPE -> "Gyroscope"
            else -> "Unknown"
        }
        val result = SensorResult(sensorName, maxValues)
        Log.d("SharedViewModel", "Sensor result created: $result") // Log 4

        firebaseRepository.saveSensorResult(sensorName, result)

        addSensorValue(result)
        Log.d("SharedViewModel", "Added sensor result to state flow") // Log 5


        Log.d("FirebaseRepository", "Saving to collection: $maxValues") // Log 6

        // Reset variables
        currentSensor = null
        currentSensorType = 0
        maxValues = listOf()
    }


    private fun addSensorValue(result: SensorResult) {
        _sensorResults.value += result
    }

    private fun processAccelerometerData(values: List<Float>) {
        val alpha = 0.8f

        gravity[0] = alpha * gravity[0] + (1 - alpha) * values[0]
        gravity[1] = alpha * gravity[1] + (1 - alpha) * values[1]
        gravity[2] = alpha * gravity[2] + (1 - alpha) * values[2]

        linearAcceleration[0] = values[0] - gravity[0]
        linearAcceleration[1] = values[1] - gravity[1]
        linearAcceleration[2] = values[2] - gravity[2]

        // Update maxValues
        maxValues = maxValues.mapIndexed { index, maxValue ->
            maxOf(maxValue, linearAcceleration[index])
        }
    }

    private fun processGyroscopeData(values: List<Float>) {
        val eventTimestamp = System.nanoTime().toFloat()
        if (timestamp != 0f) {
            val dT = (eventTimestamp - timestamp) * NS2S
            var axisX = values[0]
            var axisY = values[1]
            var axisZ = values[2]

            val omegaMagnitude = sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ)

            if (omegaMagnitude > EPSILON) {
                axisX /= omegaMagnitude
                axisY /= omegaMagnitude
                axisZ /= omegaMagnitude
            }

            val thetaOverTwo = omegaMagnitude * dT / 2.0f
            val sinThetaOverTwo = sin(thetaOverTwo)
            val cosThetaOverTwo = cos(thetaOverTwo)
            deltaRotationVector[0] = sinThetaOverTwo * axisX
            deltaRotationVector[1] = sinThetaOverTwo * axisY
            deltaRotationVector[2] = sinThetaOverTwo * axisZ
            deltaRotationVector[3] = cosThetaOverTwo

            // Here, you might process the deltaRotationVector further.
            // For simplicity, we'll update maxValues with raw values.
            maxValues = maxValues.mapIndexed { index, maxValue ->
                maxOf(maxValue, values[index])
            }
        }
        timestamp = eventTimestamp
    }
}
