package dev.marc.m335.weathercompareapp;

import static android.content.Intent.getIntent;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

import dev.marc.m335.weathercompareapp.model.WeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiTemperatureSensorService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        String apiKey = intent.getExtras().getString("apiKey");

        getTemperature(apiKey);
        return START_REDELIVER_INTENT;

    }

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

    public ApiTemperatureSensorService() {

    }

    private void getTemperature(String apiKey) {
        Call<WeatherData> call = apiService.getUser("47.376888", "8.541694", apiKey);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                int statusCode = response.code();
                System.out.println(statusCode);
                WeatherData user = response.body();
                assert user != null;
                System.out.println(user.getCurrent().getTemp());
                sendTemperatureBroadcast(user.getCurrent().getTemp());
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                System.out.println(t);
                // Log error here since request failed
            }
        });


    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        ApiTemperatureSensorService getService() {
            return ApiTemperatureSensorService.this;
        }

    }

    private void sendTemperatureBroadcast(double temperature) {
        Intent intent = new Intent("updateApiTemp");
        intent.putExtra("TEMPERATUREAPI", temperature);
        sendBroadcast(intent);
    }

}