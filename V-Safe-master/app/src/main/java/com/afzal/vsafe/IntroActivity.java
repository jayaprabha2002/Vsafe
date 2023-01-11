package com.afzal.vsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IntroActivity extends AppCompatActivity {
    EditText cc1,cc2,time;
    Button submitbt;
    String num1,num2;
    String Lat,Lon;

    private String flags = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        cc1 = findViewById(R.id.contact1);
        cc2 = findViewById(R.id.contact2);
        submitbt = findViewById(R.id.proceedbtn);
        checkFirstOpen();
        submitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               num1 =  cc1.getText().toString();
               num2 =  cc2.getText().toString();

               savedata(num1,num2);

                if (ContextCompat.checkSelfPermission(IntroActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    startActivity(new Intent(IntroActivity.this ,LocationActivity.class));
                    finish();
                }
                else {
                    ActivityCompat.requestPermissions(IntroActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
                }
            }
        });


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && grantResults.length > 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED ){

        }
        else {
            Toast.makeText(getApplicationContext(),"Permission denied",Toast.LENGTH_SHORT).show();
        }
    }

    private void savedata(String num1, String num2) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor myEdit = sp.edit();
        myEdit.putString("contact1", num1);
        myEdit.putString("contact2", num2);
        myEdit.apply();
        Toast.makeText(getApplicationContext(),"Contact saved",Toast.LENGTH_SHORT).show();
    }
    private void checkFirstOpen(){
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (!isFirstRun) {
            if(!foregroundServiceRunning()){
                Intent intent = new Intent(this, ShakeService.class);
                //Start Service
                startForegroundService(intent);
            }
            startActivity(new Intent(IntroActivity.this ,LocationActivity.class));
            finish();

        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun",
                false).apply();
    }

    private boolean foregroundServiceRunning() {
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(ShakeService.class.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}