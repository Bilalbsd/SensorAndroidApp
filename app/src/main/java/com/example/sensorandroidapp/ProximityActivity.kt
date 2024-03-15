package com.example.sensorandroidapp
import android.content.Context
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ProximityActivity : AppCompatActivity() {

    private var reliable = true
    private val PROXIMITY_THRESHOLD = 3.0
    private lateinit var sensorManager: SensorManager
    private lateinit var proximity: Sensor
    private lateinit var ivProximity: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proximity)

        ivProximity = findViewById(R.id.state_proximity)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!!

        val sel = object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                if (reliable) {
                    if (sensorEvent.sensor.type == Sensor.TYPE_PROXIMITY) {
                        val bm = if (sensorEvent.values[0] <= PROXIMITY_THRESHOLD) {
                            BitmapFactory.decodeResource(resources, R.drawable.zoom)
                        } else {
                            BitmapFactory.decodeResource(resources, R.drawable.none)
                        }
                        ivProximity.setImageBitmap(bm)
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                reliable =
                    accuracy == SensorManager.SENSOR_STATUS_ACCURACY_HIGH || accuracy == SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM
            }
        }

        val supported =
            sensorManager.registerListener(sel, proximity, SensorManager.SENSOR_DELAY_UI)
        if (!supported) {
            sensorManager.unregisterListener(sel, proximity)
        }
    }
}