package com.example.seniorsafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.seniorsafety.services.FallDetection;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST = 1;
    private FirebaseAuth firebaseAuth;
    private Button nearbyPharmaciesButton;
    private Button medicationButton;
    private Button memoryGamesButton;
    private Button emergencyContactsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.startFallDetectionService();
        //this.checkForSmsPermission();
        this.checkForPermissions();
        this.firebaseAuth = FirebaseAuth.getInstance();
        System.out.println("Current logged user: " + firebaseAuth.getCurrentUser().getUid());
        System.out.println("NOME: " + firebaseAuth.getCurrentUser().getDisplayName());
        System.out.println("PHONE NUMBER" + firebaseAuth.getCurrentUser().getPhoneNumber());
        this.nearbyPharmaciesButton = (Button) findViewById(R.id.nearbyPharmacies_button);
        this.medicationButton = (Button) findViewById(R.id.medication_button);
        this.memoryGamesButton = (Button) findViewById(R.id.memoryGames_button);
        this.emergencyContactsButton = (Button) findViewById(R.id.emergencyContacts_button);

        this.nearbyPharmaciesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nearbyPharmaciesIntent = new Intent(MainActivity.this, NearbyPharmaciesActivity.class);
                startActivity(nearbyPharmaciesIntent);
            }
        });

        this.medicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent medicationIntent = new Intent(MainActivity.this, MedicationActivity.class);
                startActivity(medicationIntent);
            }
        });

        this.memoryGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent memoryGamesIntent = new Intent(MainActivity.this, MemoryGamesActivity.class);
                startActivity(memoryGamesIntent);
            }
        });

        this.emergencyContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emergencyContactsIntent = new Intent(MainActivity.this, EmergencyContactsActivity.class);
                startActivity(emergencyContactsIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_logout:
                this.firebaseAuth.signOut();
                Intent loginPageIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginPageIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //left empty on purpose. Prevents to go back to the login page
    @Override
    public void onBackPressed() {
    }

    private void startFallDetectionService() {
        System.out.println("VEIO AQUI");
        Intent serviceIntent = new Intent(this, FallDetection.class);
        startService(serviceIntent);
    }

    private void stopFallDetectionService() {
        Intent serviceIntent = new Intent(this, FallDetection.class);
        stopService(serviceIntent);
    }



    private boolean checkForPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        Manifest.permission.SEND_SMS
                }, MY_PERMISSIONS_REQUEST);
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        Manifest.permission.SEND_SMS
                }, MY_PERMISSIONS_REQUEST);
            }
            return false;
        } else {
            return true;
        }
    }
}