package dev.marc.m335.weathercompareapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class TemperatureSensorService extends Service {
    public TemperatureSensorService() {
    }

    private final IBinder binder = new LocalBinder();

    public int onStartCommand(Intent intent, int flags, int startId) {
        Double height = intent.getExtras().getDouble("height");
        Double weight = intent.getExtras().getDouble("weight");
        // return START_STICKY;$
        System.out.println(weight);
        System.out.println(height);

        return flags;

    }

    public static Integer getTemperatureFromSensor() {
        System.out.println("Gets Executed");
        return -5;
    }

    public class LocalBinder extends Binder {
        TemperatureSensorService getService() {
            return TemperatureSensorService.this;
        }

    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}