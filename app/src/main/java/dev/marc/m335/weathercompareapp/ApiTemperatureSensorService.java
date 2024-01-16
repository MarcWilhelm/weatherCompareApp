package dev.marc.m335.weathercompareapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dev.marc.m335.weathercompareapp.model.WeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiTemperatureSensorService extends Service implements SensorEventListener {

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("getsExecutedApiTemp");
        getTemperature();
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

    private void getTemperature() {
        System.out.println("getTemperature()gets executed");
        Call<WeatherData> call = apiService.getUser();
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                int statusCode = response.code();
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    private void sendTemperatureBroadcast(double temperature) {
        Intent intent = new Intent("updateApiTemp");
        intent.putExtra("TEMPERATUREAPI", temperature);
        sendBroadcast(intent);
    }

}