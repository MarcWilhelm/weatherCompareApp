package dev.marc.m335.weathercompareapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;


public class TemperatureSensorService extends Service implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor ambientTemperatureSensor;


    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        ambientTemperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (ambientTemperatureSensor != null) {
            sensorManager.registerListener(this, ambientTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
            sendTemperatureBroadcast(25);
        } else {
            Toast.makeText(this, "Temperature Sensor Nicht Ereichbar oder Vorhanden", Toast.LENGTH_SHORT).show();
        }

    /*    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            System.out.println(longitude);
        }
*/

    }

    private void sendTemperatureBroadcast(float temperature) {
        Intent intent = new Intent("updateTemp");
        intent.putExtra("TEMPERATURE", temperature);
        sendBroadcast(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float temperature = event.values[0];
            sendTemperatureBroadcast(temperature); // Send temperature updates as they change
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister sensor listener
        sensorManager.unregisterListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public TemperatureSensorService() {
    }

    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        TemperatureSensorService getService() {
            return TemperatureSensorService.this;
        }

    }
}