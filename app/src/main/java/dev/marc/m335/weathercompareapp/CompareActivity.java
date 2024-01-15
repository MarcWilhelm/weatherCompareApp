package dev.marc.m335.weathercompareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dev.marc.m335.weathercompareapp.model.WeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CompareActivity extends AppCompatActivity {

    TemperatureSensorService mService;
    boolean mBound = false;


    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    public static final String BASE_URL = "https://api.openweathermap.org/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    MyAPIEndpointInterface apiService =
            retrofit.create(MyAPIEndpointInterface.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        Bundle exttras = getIntent().getExtras();

        if (exttras != null) {
            String name = exttras.getString("name");
            TextView nameText = (TextView) findViewById(R.id.yourName);
            nameText.setText(name);

        }
        getTemperature();
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

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("updateTemp");
        registerReceiver(temperatureReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(temperatureReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TemperatureSensorService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        startService(intent);
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

    private void getTemperature() {
        System.out.println("Temperature");

        Call<WeatherData> call = apiService.getUser();
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                int statusCode = response.code();
                WeatherData user = response.body();
                assert user != null;

                TextView temperatureApi = (TextView) findViewById(R.id.tempreatureApi);
                temperatureApi.setText(String.valueOf(user.getCurrent().getTemp()));

            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                System.out.println(t);
                // Log error here since request failed
            }
        });



    }
}
