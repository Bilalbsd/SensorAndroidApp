package com.example.sensorandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import java.util.ArrayList

class SensorListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_list)

        val sensorListTitle: TextView = findViewById(R.id.title_sensor_list)
        val sensorsListView: ListView = findViewById(R.id.sensors_list)
        sensorListTitle.setText(R.string.sensor_list)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        val sensorsListStr = ArrayList<String>()
        val sensorListSize = sensorList.size
        for (i in 0 until sensorListSize) {
            sensorsListStr.add(sensorList[i].name)
        }
        val adapterSensorsList =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, sensorsListStr)

        sensorsListView.adapter = adapterSensorsList
    }
}