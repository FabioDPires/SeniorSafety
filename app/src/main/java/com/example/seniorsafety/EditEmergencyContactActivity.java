package com.example.seniorsafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditEmergencyContactActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private EditText nameEt;
    private EditText phoneEt;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emergency_contact);

        this.sp= PreferenceManager.getDefaultSharedPreferences(this);
        this.nameEt=findViewById(R.id.nameContactET);
        this.phoneEt=findViewById(R.id.emergencyContactET);
        this.saveButton=findViewById(R.id.saveContact_button);

        this.nameEt.setText(getIntent().getStringExtra("emergencyName"));
        this.phoneEt.setText(getIntent().getStringExtra("emergencyNumber"));

        this.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameEt.getText().toString();
                String phone=phoneEt.getText().toString();
                boolean validInfo=true;
                if(name.equals("")){
                    validInfo=false;
                    nameEt.setError("Name can't be empty");
                }

                if(phone.equals("")){
                    validInfo=false;
                    phoneEt.setError("Phone number can't be empty");
                }

                if(!validInfo){
                    return;
                }

                SharedPreferences.Editor mEditor = sp.edit();
                mEditor.putString("emergencyName", nameEt.getText().toString());
                mEditor.putString("emergencyNumber", phoneEt.getText().toString());
                mEditor.commit();
                Intent contactIntent=new Intent(EditEmergencyContactActivity.this,EmergencyContactsActivity.class);
                startActivity(contactIntent);
            }
        });
    }
}