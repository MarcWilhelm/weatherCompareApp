package dev.marc.m335.weathercompareapp;

import dev.marc.m335.weathercompareapp.model.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyAPIEndpointInterface {

    @GET("https://api.openweathermap.org/data/3.0/onecall?&exclude=hourly,daily&units=metric")
    Call<WeatherData> getUser(
            @Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid
    );
}
