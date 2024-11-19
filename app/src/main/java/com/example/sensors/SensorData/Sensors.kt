package com.example.sensors.SensorData

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class Accelerometer(
    context: Context
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_ACCELEROMETER
)

class Gyroscope(
    context: Context
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_GYROSCOPE,
    sensorType = Sensor.TYPE_ACCELEROMETER
)