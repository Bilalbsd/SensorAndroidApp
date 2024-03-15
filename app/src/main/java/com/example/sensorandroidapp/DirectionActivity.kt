package com.example.sensorandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.TextView
import kotlin.math.abs
import kotlin.math.sqrt


class DirectionActivity : AppCompatActivity() {

    private var reliable = true
    private val THRESHOLD_VAL = 2.0f
    private var nextTiming = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direction)

        val directionValueTv: TextView = findViewById(R.id.direction_value)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        val sel = object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                if (reliable && System.currentTimeMillis() > nextTiming) {
                    nextTiming = System.currentTimeMillis() + 400
                    val magnitude = sqrt(sensorEvent.values[0] * sensorEvent.values[0] +
                            sensorEvent.values[1] * sensorEvent.values[1] +
                            sensorEvent.values[2] * sensorEvent.values[2])

                    directionValueTv.text = when {
                        magnitude > THRESHOLD_VAL -> {
                            val maxAbsId = getMaxAbsoluteValueId(sensorEvent.values)
                            when (maxAbsId) {
                                0 -> if (sensorEvent.values[maxAbsId] > 0) "►" else "◄"
                                1 -> if (sensorEvent.values[maxAbsId] > 0) "▲" else "▼"
                                else -> if (sensorEvent.values[maxAbsId] > 0) "⤵" else "⤴"
                            }
                        }
                        else -> "∅"
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                reliable = accuracy == SensorManager.SENSOR_STATUS_ACCURACY_HIGH || accuracy == SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM
            }
        }

        val supported = sensorManager.registerListener(sel, accelerometer, SensorManager.SENSOR_DELAY_UI)
        if (!supported) {
            sensorManager.unregisterListener(sel, accelerometer)
        }
    }

    private fun getMaxAbsoluteValueId(values: FloatArray): Int {
        val absoluteValues = values.map(::abs)
        return absoluteValues.indexOf(absoluteValues.maxOrNull())
    }
}
