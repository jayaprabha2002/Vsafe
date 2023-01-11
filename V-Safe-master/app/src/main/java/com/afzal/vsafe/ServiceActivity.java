package com.afzal.vsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ServiceActivity extends AppCompatActivity {
    static TextView tvShakeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        if(!foregroundServiceRunning()){
            Intent intent = new Intent(this, ShakeService.class);
            //Start Service
            startForegroundService(intent);
        }


        tvShakeService = findViewById(R.id.tvShakeService);

    }

    public boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(ShakeService.class.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}