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
        val accelerometerBtn = findViewById<Button>(R.id.accelerometer_btn)
        val directionBtn = findViewById<Button>(R.id.direction_btn)
        val shakeBtn = findViewById<Button>(R.id.shake_btn)
        val proximityBtn = findViewById<Button>(R.id.proximity_btn)
        val geolocationBtn = findViewById<Button>(R.id.geolocation_btn)

        sensorListBtn.setOnClickListener { launchActivity(SensorListActivity::class.java, 0) }
        sensorPresenceBtn.setOnClickListener { launchActivity(SensorDisponibilityActivity::class.java, SensorDisponibilityActivity.SENSOR_PRESENCE) }
        sensorAbsenceBtn.setOnClickListener { launchActivity(SensorDisponibilityActivity::class.java, SensorDisponibilityActivity.SENSOR_ABSENCE) }
        accelerometerBtn.setOnClickListener { launchActivity(AccelerometerActivity::class.java, 0) }
        directionBtn.setOnClickListener { launchActivity(DirectionActivity::class.java, 0) }
        shakeBtn.setOnClickListener { launchActivity(ShakeActivity::class.java, 0) }
        proximityBtn.setOnClickListener { launchActivity(ProximityActivity::class.java, 0) }
        geolocationBtn.setOnClickListener { launchActivity(GeolocationActivity::class.java, 0) }

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
