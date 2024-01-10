package dev.marc.m335.weathercompareapp;

import static dev.marc.m335.weathercompareapp.TemperatureSensorService.getTemperatureFromSensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CompareActivity extends AppCompatActivity {

    TemperatureSensorService mService;
    boolean mBound = false;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TemperatureSensorService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        getTemperatureFromSensor();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }

    private final ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            TemperatureSensorService.LocalBinder binder = (TemperatureSensorService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        TextView temperatureSensor = (TextView) findViewById(R.id.tempreatureSensor);
        temperatureSensor.setText("1");

        Bundle exttras = getIntent().getExtras();

        if (exttras != null) {
            String name = exttras.getString("name");
            TextView nameText = (TextView) findViewById(R.id.yourName);
            nameText.setText(name);
        }
    }
}