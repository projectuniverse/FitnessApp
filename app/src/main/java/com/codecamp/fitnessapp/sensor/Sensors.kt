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
    sensorType = Sensor.TYPE_LINEAR_ACCELERATION,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER
)

class Gyroscope(
    context: Context
): MovementSensor(
    context = context,
    sensorType = Sensor.TYPE_GYROSCOPE,
    sensorFeature = PackageManager.FEATURE_SENSOR_GYROSCOPE
)