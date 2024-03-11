package com.example.sensorandroidapp
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class ShakeActivity : AppCompatActivity() {

    private var reliable = true
    private val SHAKE_THRESHOLD = 150.0
    private var lastUpdate: Long = 0
    private var last_x = 0f
    private var last_y = 0f
    private var last_z = 0f
    private var torchMode = false
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private val sel = object : SensorEventListener {
        override fun onSensorChanged(sensorEvent: SensorEvent) {
            if (reliable && sensorEvent.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val curTime = System.currentTimeMillis()
                if (curTime - lastUpdate > 500) {
                    val diffTime = (curTime - lastUpdate)
                    lastUpdate = curTime

                    val x = sensorEvent.values[0]
                    val y = sensorEvent.values[1]
                    val z = sensorEvent.values[2]

                    val speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000

                    if (speed > SHAKE_THRESHOLD) {
                        toggleTorchMode()
                    }

                    last_x = x
                    last_y = y
                    last_z = z
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            reliable = accuracy == SensorManager.SENSOR_STATUS_ACCURACY_HIGH || accuracy == SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!


        val supported = sensorManager.registerListener(sel, accelerometer, SensorManager.SENSOR_DELAY_UI)
        if (!supported) {
            sensorManager.unregisterListener(sel, accelerometer)
        }
    }

    private fun toggleTorchMode() {
        torchMode = !torchMode
        val handler = Handler()
        val camManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        handler.post {
            try {
                val cameraId = camManager.cameraIdList[0]
                camManager.setTorchMode(cameraId, torchMode)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(sel, accelerometer)
    }
}
