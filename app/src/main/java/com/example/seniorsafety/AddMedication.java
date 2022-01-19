package com.example.seniorsafety;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.seniorsafety.database.Medication;
import com.example.seniorsafety.database.Repository;
<<<<<<< HEAD
import com.example.seniorsafety.managers.MedicationReminder;

import java.util.Calendar;

=======
>>>>>>> fb8d49e0dcf9ed998d2f5b4960e30f0a179d2c6b
public class AddMedication extends AppCompatActivity {
    private EditText nameET, quantET;
    private TimePicker tp;
    private Button saveButton;
    private Repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        this.repo = new Repository(this);
<<<<<<< HEAD
        this.nameET = (EditText) findViewById(R.id.etMedName);
        this.quantET = (EditText) findViewById(R.id.etMedQuant);
        this.tp = (TimePicker) findViewById(R.id.timePicker1);
        this.tp.setIs24HourView(true);
        this.saveButton = (Button) findViewById(R.id.save_medication);
        String mode = getIntent().getStringExtra("mode");
        if (mode.equals("edit")) {
            this.saveButton.setText("Edit");
            String currentName = getIntent().getStringExtra("currentName");
            int currentQuant = getIntent().getIntExtra("currentQuant", 0);
            String formatedQuant = Integer.toString(currentQuant);
            this.nameET.setText(currentName);
            this.quantET.setText(formatedQuant);
        }

        this.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String quant = quantET.getText().toString();
                int quant_aux = 0;
                boolean validInfo = true;

                if (name.equals("")) {
=======
        this.nameET=(EditText) findViewById(R.id.etMedName);
        this.quantET= (EditText) findViewById(R.id.etMedQuant);
        this.tp=(TimePicker) findViewById(R.id.timePicker1);
        this.tp.setIs24HourView(true);
        this.saveButton=(Button) findViewById(R.id.save_medication);
        String mode=getIntent().getStringExtra("mode");
        if(mode.equals("edit")){
            this.saveButton.setText("Edit");
            String currentName=getIntent().getStringExtra("currentName");
            int currentQuant=getIntent().getIntExtra("currentQuant",0);
            String formatedQuant=Integer.toString(currentQuant);
            this.nameET.setText(currentName);
            this.quantET.setText(formatedQuant);
        }
        this.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameET.getText().toString();
                String quant=quantET.getText().toString();
                boolean validInfo=true;
                if(name.equals("")){
>>>>>>> fb8d49e0dcf9ed998d2f5b4960e30f0a179d2c6b
                    nameET.setError("Medication name can't be empty");
                    validInfo = false;
                }
<<<<<<< HEAD

                if (quant.equals("")) {
=======
                if(quant.equals("")){
>>>>>>> fb8d49e0dcf9ed998d2f5b4960e30f0a179d2c6b
                    quantET.setError("Please provide medication quantity");
                    validInfo = false;
                } else {
                    quant_aux = Integer.parseInt(quant);
                    if (quant_aux <= 0) {
                        quantET.setError("Quantity must be a positive value");
                        validInfo = false;
                    }
                }
<<<<<<< HEAD
                
                if (!validInfo) {
                    return;
                }

                int hour = tp.getCurrentHour();
                int min = tp.getCurrentMinute();
                String time = hour + ":" + min;
                if (mode.equals("edit")) {
                    Medication med = new Medication(name, quant_aux, time);
                    int id = getIntent().getIntExtra("id", 0);
                    med.setId(id);
                    repo.updateMedication(med);
                } else {
                    MedicationReminder mr = new MedicationReminder(getApplicationContext());
                    Calendar c = Calendar.getInstance();
                    System.out.println("HORA: " + hour);
                    c.set(Calendar.HOUR, hour);
                    c.set(Calendar.MINUTE, min);
                    if (hour > 12) {
                        c.add(Calendar.HOUR, 12);
                    }
                    repo.insertMedication(new Medication(name, quant_aux, time));
                    mr.setReminder(new Medication(name, quant_aux, time), c);
                }
                Intent medicationIntent = new Intent(AddMedication.this, MedicationActivity.class);
=======
                int quant_aux=Integer.parseInt(quant);
                if(quant_aux<=0){
                    quantET.setError("Quantity must be a positive value");
                    validInfo=false;
                }
                if(!validInfo){
                    return;
                }
                int hour = tp.getCurrentHour();
                int min = tp.getCurrentMinute();
                String time=hour+":"+min;
                if(mode.equals("edit")){
                    Medication med=new Medication(name,quant_aux,time);
                    int id=getIntent().getIntExtra("id",0);
                    med.setId(id);
                    repo.updateMedication(med);
                }
                else{
                    repo.insertMedication(new Medication(name,quant_aux,time));
                }
                Intent medicationIntent=new Intent(AddMedication.this,MedicationActivity.class);
>>>>>>> fb8d49e0dcf9ed998d2f5b4960e30f0a179d2c6b
                startActivity(medicationIntent);
            }
        });
    }
}