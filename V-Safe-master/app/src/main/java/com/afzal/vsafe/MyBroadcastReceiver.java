package com.afzal.vsafe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent sericeIntent = new Intent(context, ShakeService.class);
            //Start Service
            context.startForegroundService(sericeIntent);
        }
    }
}
