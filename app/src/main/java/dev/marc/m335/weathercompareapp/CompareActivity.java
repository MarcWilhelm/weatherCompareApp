package dev.marc.m335.weathercompareapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class CompareActivity extends AppCompatActivity {

    TemperatureSensorService mService;

    ApiTemperatureSensorService tempService;
    boolean mBound = false;

    boolean mBoundApi = false;

String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        Bundle exttras = getIntent().getExtras();
        assert exttras != null;
        apiKey = exttras.getString("apiKey");
        String name = exttras.getString("nameOfUser");
        TextView nameText = (TextView) findViewById(R.id.yourName);
        nameText.setText(name);

    }

    private BroadcastReceiver temperatureReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals("updateTemp")) {
                float temperature = intent.getFloatExtra("TEMPERATURE", 0.0f);
                TextView temperatureSensor = (TextView) findViewById(R.id.tempreatureSensor);
                temperatureSensor.setText(String.valueOf(temperature));
            }
        }
    };

    private BroadcastReceiver apiTemperatureReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals("updateApiTemp")) {
                double temperature = intent.getDoubleExtra("TEMPERATUREAPI", 0.0);
                TextView temperatureSensor = (TextView) findViewById(R.id.tempreatureApi);
                temperatureSensor.setText(String.valueOf(temperature));
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("updateTemp");
        IntentFilter apifilter = new IntentFilter("updateApiTemp");
        registerReceiver(temperatureReceiver, filter);
        registerReceiver(apiTemperatureReceiver, apifilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(temperatureReceiver);
        unregisterReceiver(apiTemperatureReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TemperatureSensorService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        startService(intent);

        Intent intentApi = new Intent(this, ApiTemperatureSensorService.class);
        bindService(intentApi, apiConnection, Context.BIND_AUTO_CREATE);
        intentApi.putExtra("apiKey", apiKey);
        startService(intentApi);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;

        unbindService(apiConnection);
        mBoundApi = false;
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


    private final ServiceConnection apiConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ApiTemperatureSensorService.LocalBinder binder = (ApiTemperatureSensorService.LocalBinder) service;
            tempService = binder.getService();
            mBoundApi = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBoundApi = false;
        }
    };
}
