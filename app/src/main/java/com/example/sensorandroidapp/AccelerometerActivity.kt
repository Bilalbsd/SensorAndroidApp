package com.example.sensorandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.constraintlayout.widget.ConstraintLayout


class AccelerometerActivity : AppCompatActivity() {
    private var reliable = true
    private val THRESHOLD_VAL = 0.5f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)

        val colorPlayground: ConstraintLayout = findViewById(R.id.accelerometer_color)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        val sel = object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                if (reliable) {
                    val valSum = sensorEvent.values.sum()
                    val color = when {
                        valSum > THRESHOLD_VAL -> Color.RED
                        valSum < -THRESHOLD_VAL -> Color.GREEN
                        else -> Color.BLACK
                    }
                    colorPlayground.setBackgroundColor(color)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                reliable = accuracy == SensorManager.SENSOR_STATUS_ACCURACY_HIGH ||
                        accuracy == SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM
            }
        }

        val supported =
            sensorManager.registerListener(sel, accelerometer, SensorManager.SENSOR_DELAY_UI)
        if (!supported) {
            sensorManager.unregisterListener(sel, accelerometer)
        }
    }
}
