package dev.marc.m335.weathercompareapp;

import dev.marc.m335.weathercompareapp.model.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyAPIEndpointInterface {

    @GET("https://api.openweathermap.org/data/3.0/onecall?lat=33.44&lon=-94.04&exclude=hourly,daily&units=metric&appid=f75480f4a3e76d1ecfd2594d998e0786")
    Call<WeatherData> getUser();
}
