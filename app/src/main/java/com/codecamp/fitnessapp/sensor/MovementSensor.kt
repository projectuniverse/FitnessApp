package com.codecamp.fitnessapp.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

abstract class MovementSensor(
    private val context: Context,
    private val sensorType: Int,
    private val sensorFeature: String
): SensorEventListener {

    private val sensorExists: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    private var onValueChange: ((List<Float>) -> Unit)? = null

    fun startListening(listener: ((List<Float>) -> Unit)) {
        if (!sensorExists) {
            return
        }

        onValueChange = listener

        if (sensor == null) {
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
        }

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (!sensorExists || event == null) {
            return
        }

        onValueChange?.invoke(event.values.toList())
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}