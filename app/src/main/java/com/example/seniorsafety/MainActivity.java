package com.example.seniorsafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button nearbyPharmaciesButton;
    private Button medicationButton;
    private Button memoryGamesButton;
    private Button emergencyContactsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.firebaseAuth=FirebaseAuth.getInstance();
        this.nearbyPharmaciesButton=(Button) findViewById(R.id.nearbyPharmacies_button);
        this.medicationButton=(Button) findViewById(R.id.medication_button);
        this.memoryGamesButton=(Button) findViewById(R.id.memoryGames_button);
        this.emergencyContactsButton=(Button) findViewById(R.id.emergencyContacts_button);

        this.nearbyPharmaciesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nearbyPharmaciesIntent=new Intent(MainActivity.this,NearbyPharmaciesActivity.class);
                startActivity(nearbyPharmaciesIntent);
            }
        });

        this.medicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent medicationIntent=new Intent(MainActivity.this,MedicationActivity.class);
                startActivity(medicationIntent);
            }
        });

        this.memoryGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent memoryGamesIntent=new Intent(MainActivity.this,MemoryGamesActivity.class);
                startActivity(memoryGamesIntent);
            }
        });

        this.emergencyContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emergencyContactsIntent=new Intent(MainActivity.this,EmergencyContactsActivity.class);
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
                Intent loginPageIntent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(loginPageIntent);
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    //left empty on purpose. Prevents to go back to the login page
    @Override
    public void onBackPressed() {
    }
}