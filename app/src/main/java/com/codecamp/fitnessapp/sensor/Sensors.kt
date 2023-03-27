package com.codecamp.fitnessapp.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor


class Gyroscope(
    context: Context
): MovementSensor(
    context = context,
    sensorType = Sensor.TYPE_GYROSCOPE,
    sensorFeature = PackageManager.FEATURE_SENSOR_GYROSCOPE
)