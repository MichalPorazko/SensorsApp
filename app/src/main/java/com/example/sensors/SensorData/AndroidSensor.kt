package com.example.sensors.SensorData

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

abstract class AndroidSensor(
    private val context: Context,
    private val sensorFeature: String,
    sensorType: Int
): MeasurableSensor(sensorType), SensorEventListener {

    override val doesSensorExist: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun startListening() {
        if(!doesSensorExist) {
            return
        }
        if(!::sensorManager.isInitialized && sensor == null) {
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
        }
        sensor?.let {

            /**
             * AndroidSensor class itself acts as the SensorEventListener
             * rather than delegating that role to something like SensorViewModel
             *
             * so that is why the ShareViewModel is not the listener bur the AndroidSensor class is
             * */
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun stopListening() {
        if(!doesSensorExist || !::sensorManager.isInitialized) {
            return
        }
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(!doesSensorExist) {
            return
        }
        if(event?.sensor?.type == sensorType) {
            onSensorValuesChanged?.invoke(event.values.toList())
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) = Unit
}