package com.example.sensorandroidapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SensorDisponibilityActivity : AppCompatActivity() {

    companion object {
        const val SENSOR_PRESENCE = 0
        const val SENSOR_ABSENCE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_list)

        val sensorListTitle: TextView = findViewById(R.id.title_sensor_list)
        val sensorsListView: ListView = findViewById(R.id.sensors_list)

        val typeSensors = hashMapOf(
            Sensor.TYPE_ACCELEROMETER to Sensor.STRING_TYPE_ACCELEROMETER,
            Sensor.TYPE_ACCELEROMETER_UNCALIBRATED to Sensor.STRING_TYPE_ACCELEROMETER_UNCALIBRATED,
            Sensor.TYPE_AMBIENT_TEMPERATURE to Sensor.STRING_TYPE_AMBIENT_TEMPERATURE,
            Sensor.TYPE_GAME_ROTATION_VECTOR to Sensor.STRING_TYPE_GAME_ROTATION_VECTOR,
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR to Sensor.STRING_TYPE_GEOMAGNETIC_ROTATION_VECTOR,
            Sensor.TYPE_GRAVITY to Sensor.STRING_TYPE_GRAVITY,
            Sensor.TYPE_GYROSCOPE to Sensor.STRING_TYPE_GYROSCOPE,
            Sensor.TYPE_GYROSCOPE_UNCALIBRATED to Sensor.STRING_TYPE_GYROSCOPE_UNCALIBRATED,
            Sensor.TYPE_HEART_BEAT to Sensor.STRING_TYPE_HEART_BEAT,
            Sensor.TYPE_HEART_RATE to Sensor.STRING_TYPE_HEART_RATE,
            Sensor.TYPE_HINGE_ANGLE to Sensor.STRING_TYPE_HINGE_ANGLE,
            Sensor.TYPE_LIGHT to Sensor.STRING_TYPE_LIGHT,
            Sensor.TYPE_LINEAR_ACCELERATION to Sensor.STRING_TYPE_LINEAR_ACCELERATION,
            Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT to Sensor.STRING_TYPE_LOW_LATENCY_OFFBODY_DETECT,
            Sensor.TYPE_MAGNETIC_FIELD to Sensor.STRING_TYPE_MAGNETIC_FIELD,
            Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED to Sensor.STRING_TYPE_MAGNETIC_FIELD_UNCALIBRATED,
            Sensor.TYPE_MOTION_DETECT to Sensor.STRING_TYPE_MOTION_DETECT
        )

        val paramIntent = intent.getIntExtra("param_intent", SENSOR_ABSENCE)

        val sensorsListStr = ArrayList<String>()

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        typeSensors.forEach { (key, value) ->
            val currSensor = sensorManager.getDefaultSensor(key)
            if ((currSensor != null && paramIntent == SENSOR_PRESENCE) ||
                (currSensor == null && paramIntent == SENSOR_ABSENCE)
            ) {
                sensorsListStr.add(value)
            }
        }

        sensorListTitle.text = when (paramIntent) {
            SENSOR_PRESENCE -> getString(R.string.sensor_presence)
            SENSOR_ABSENCE -> getString(R.string.sensor_absence)
            else -> ""
        }

        val adapterSensorsList =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, sensorsListStr)
        sensorsListView.adapter = adapterSensorsList
    }
}