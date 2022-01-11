package com.example.seniorsafety.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.seniorsafety.R;
import com.google.firebase.auth.FirebaseAuth;

public class FallDetection extends Service implements SensorEventListener {
    private Sensor sensor;
    private SensorManager sensorManager;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.firebaseAuth=FirebaseAuth.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double vectorNorm = Math.sqrt((Math.pow(event.values[0], 2) + Math.pow(event.values[1], 2) + Math.pow(event.values[2], 2)));

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if( vectorNorm > 25.0){
                System.out.println("Possible fall");
                /*Intent emailIntent=new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL,"fabiopires1011@gmail.com");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Possible fall!");
                emailIntent.putExtra(Intent.EXTRA_TEXT,"Possible fall");*/
                this.sendSMS();



            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void sendSMS() {
        if(checkPermission(Manifest.permission.SEND_SMS)) {
            String username=this.firebaseAuth.getCurrentUser().getDisplayName();
            System.out.println("USER: "+username);
            String sms="Is it possible that "+username+" fell. Check if everything is ok";
            SmsManager man = SmsManager.getDefault();
            man.sendTextMessage("+351932835100", null, sms, null, null);
            Toast.makeText(getApplicationContext(), "SMS enviada.",Toast.LENGTH_LONG).show();

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + "+351 932 835 100"));
        } else {
            Toast.makeText(getApplicationContext(),"SMS não enviada, por favor tente de novo.", Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}
