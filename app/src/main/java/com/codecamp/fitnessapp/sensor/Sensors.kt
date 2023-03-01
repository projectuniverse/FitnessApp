package com.codecamp.fitnessapp.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class ProximitySensor(
   context: Context
): MovementSensor(
    context = context,
    sensorType = Sensor.TYPE_PROXIMITY,
    sensorFeature = PackageManager.FEATURE_SENSOR_PROXIMITY
)

class Accelerometer(
    context: Context
): MovementSensor(
    context = context,
    sensorType = Sensor.TYPE_ACCELEROMETER,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER
)