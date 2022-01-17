package com.example.seniorsafety.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.seniorsafety.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;

public class FallDetection extends Service implements SensorEventListener {
    private Sensor sensor;
    private SensorManager sensorManager;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences sp;
    private boolean firstTime;
    private long lastFall;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.sp= PreferenceManager.getDefaultSharedPreferences(this);
        this.firstTime = true;
        this.lastFall = System.currentTimeMillis();
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
        //double vectorNorm = Math.sqrt((Math.pow(event.values[0], 2) + Math.pow(event.values[1], 2) + Math.pow(event.values[2], 2)));
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double loX = event.values[0];
            double loY = event.values[1];
            double loZ = event.values[2];

            double loAccelerationReader = Math.sqrt(Math.pow(loX, 2)
                    + Math.pow(loY, 2)
                    + Math.pow(loZ, 2));

            if (loAccelerationReader > 0.2d && loAccelerationReader < 0.6) {
            //if (vectorNorm > 25.0) {
                //If it is the first time the accelerometer detects a possible fall
                //we want it to warn it right away
                if (firstTime) {
                    this.firstTime = false;
                    this.lastFall = System.currentTimeMillis();
                    System.out.println("Possible fall");
                    this.sendSMS();
                }
                //if a previous detection has been made we need to check if the interval has passed
                //this way we prevent multiple detections in a small ammount of time
                else {
                    if (System.currentTimeMillis() >= (this.lastFall + 1 * 1000 * 60)) { //multiply by 1000 to get milliseconds
                        System.out.println("TIME PASSED");
                        this.lastFall = System.currentTimeMillis();
                        System.out.println("Possible fall");
                        this.sendSMS();
                    }
                }
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void sendSMS() {
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            String username = this.firebaseAuth.getCurrentUser().getDisplayName();
            String sms = "Is it possible that " + username + " fell. Check if everything is ok";
            String number=sp.getString("emergencyNumber","");
            System.out.println("Number " +number);

            if(number.equals("")){
                Toast.makeText(getApplicationContext(), "You don't have a emergency contact. Please add one", Toast.LENGTH_LONG).show();
                return;
            }

            SmsManager man = SmsManager.getDefault();
            man.sendTextMessage(number, null, sms, null, null);
            Toast.makeText(getApplicationContext(), "A possible fall was detected. We sent a message to your emergency contact.", Toast.LENGTH_LONG).show();

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
        } else {
            Toast.makeText(getApplicationContext(), "There is no permission to send a text", Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}
