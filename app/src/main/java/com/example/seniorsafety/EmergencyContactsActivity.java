package com.example.seniorsafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SharedMemory;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class EmergencyContactsActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private TextView emergencyNumberTV;
    private TextView nameTV;
    private Button editEmergencyContactButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);
        this.nameTV=findViewById(R.id.nameContactTV);
        this.emergencyNumberTV=findViewById(R.id.emergencyContactTV);
        this.editEmergencyContactButton=findViewById(R.id.emergencyContacts_button);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String emergencyNumber=sp.getString("emergencyNumber","");
        String emergencyName=sp.getString("emergencyName","");
        if(emergencyNumber.equals("")){
            this.nameTV.setVisibility(View.INVISIBLE);
            this.emergencyNumberTV.setText("No emergency contact defined");
            this.editEmergencyContactButton.setText("Add");
        }
        else{
            this.nameTV.setText(emergencyName);
            this.emergencyNumberTV.setText(emergencyNumber);
            this.editEmergencyContactButton.setText("Edit");
        }
        this.editEmergencyContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editContactIntent=new Intent(EmergencyContactsActivity.this,EditEmergencyContactActivity.class);
                editContactIntent.putExtra("emergencyName",emergencyName);
                editContactIntent.putExtra("emergencyNumber",emergencyNumber);
                startActivity(editContactIntent);
            }
        });
    }
}