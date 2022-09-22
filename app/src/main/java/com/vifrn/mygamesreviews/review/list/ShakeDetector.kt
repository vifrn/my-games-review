package com.vifrn.mygamesreviews.review.list

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.lang.Math.abs
import java.lang.Math.sqrt

class ShakeDetector(application: Application) {

    private val sensorManager: SensorManager? = application.getSystemService(SensorManager::class.java)
    private val shakeListener = ShakeListener()

    val shakeAmount = shakeListener.shakeAmount
    var isCapturing : Boolean = false

    fun setBaseAmount (amount : Float) {
        shakeListener.shakeAmount.value = amount
    }

    fun startCaptureShakeMovement () {
        shakeListener.shakeAmount.value = 0f
        sensorManager!!.registerListener(shakeListener,
            sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        isCapturing = true
    }

    fun stopCaptureShakeMovement() {
        sensorManager!!.unregisterListener(shakeListener)
        isCapturing = false
    }

    class ShakeListener : SensorEventListener {
        var shakeAmount = MutableLiveData<Float>()

        private var currentAcceleration = 0f
        private var lastAcceleration = 0f

        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            lastAcceleration = currentAcceleration

            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat() * 0.01f
            val delta: Float = (currentAcceleration - lastAcceleration)

            if(currentAcceleration > 0.1f) {
                shakeAmount.value = shakeAmount.value?.plus(currentAcceleration + abs(delta))
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }
}

