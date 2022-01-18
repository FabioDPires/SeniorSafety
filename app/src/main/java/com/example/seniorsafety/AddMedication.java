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
public class AddMedication extends AppCompatActivity {
    private EditText nameET,quantET;
    private TimePicker tp;
    private Button saveButton;
    private Repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        this.repo = new Repository(this);
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
                    nameET.setError("Medication name can't be empty");
                    validInfo=false;
                }
                if(quant.equals("")){
                    quantET.setError("Please provide medication quantity");
                    validInfo=false;
                }
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
                startActivity(medicationIntent);
            }
        });
    }
}