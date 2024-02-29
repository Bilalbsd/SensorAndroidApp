package com.example.sensorandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Button
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensorListBtn = findViewById<Button>(R.id.sensor_list_btn)
        val sensorPresenceBtn = findViewById<Button>(R.id.sensor_list_presence)
        val sensorAbsenceBtn = findViewById<Button>(R.id.sensor_list_absence)

        sensorListBtn.setOnClickListener { launchActivity(SensorListActivity::class.java, 0) }
        sensorPresenceBtn.setOnClickListener { launchActivity(SensorDisponibilityActivity::class.java, SensorDisponibilityActivity.SENSOR_PRESENCE) }
        sensorAbsenceBtn.setOnClickListener { launchActivity(SensorDisponibilityActivity::class.java, SensorDisponibilityActivity.SENSOR_ABSENCE) }

        requestLocationPermissions()
    }

    private fun launchActivity(classToLaunch: Class<*>, parameter: Int) {
        val intent = Intent(this, classToLaunch)
        intent.putExtra("param_intent", parameter)
        startActivity(intent)
    }

    private fun requestLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 101)
        }
    }
}
