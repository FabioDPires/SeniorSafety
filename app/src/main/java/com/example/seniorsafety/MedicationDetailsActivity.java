package com.example.seniorsafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.seniorsafety.database.Medication;
import com.example.seniorsafety.database.Repository;

public class MedicationDetailsActivity extends AppCompatActivity {
    private TextView nameTV,quantTV,timeTV;
    private Button deleteButton,editButton;
    private Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_details);
        this.repo = new Repository(this);

        String name=getIntent().getStringExtra("medicationName");
        int quant=getIntent().getIntExtra("medicationQuant",0);
        String quantAux=Integer.toString(quant);
        String time=getIntent().getStringExtra("medicationTime");
        int id= getIntent().getIntExtra("medicationID",0);

        this.nameTV=(TextView) findViewById(R.id.medicationNameDetails);
        this.nameTV.setText(name);
        this.quantTV=(TextView) findViewById(R.id.medicationQuantityDetails);
        this.quantTV.setText("Quantity: "+quantAux);
        this.timeTV=(TextView) findViewById(R.id.medicationTimeDetails);
        this.timeTV.setText(time);

        this.editButton=(Button) findViewById(R.id.editMedButton);
        this.deleteButton=(Button) findViewById(R.id.deleteMedButton);
        this.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Medication Deleted");
                Medication med=new Medication(name,quant,time);
                med.setId(id);
                repo.deleteMedication(med);
                Intent medicationListIntent=new Intent(MedicationDetailsActivity.this,MedicationActivity.class);
                startActivity(medicationListIntent);
            }
        });

        this.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("MedicationEdited");
                Intent medicationAddIntent=new Intent(MedicationDetailsActivity.this,AddMedication.class);
                medicationAddIntent.putExtra("mode","edit");
                medicationAddIntent.putExtra("currentName",name);
                medicationAddIntent.putExtra("currentQuant",quant);
                medicationAddIntent.putExtra("id",id);
                startActivity(medicationAddIntent);
            }
        });
    }
}