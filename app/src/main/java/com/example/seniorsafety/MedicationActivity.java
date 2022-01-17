package com.example.seniorsafety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.seniorsafety.adapters.MedicationAdapter;
import com.example.seniorsafety.adapters.ScoresAdapter;
import com.example.seniorsafety.database.Medication;
import com.example.seniorsafety.database.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MedicationActivity extends AppCompatActivity implements MedicationAdapter.OnMedicationListener {
    private RecyclerView medicationRecyclerView;
    private ArrayList<Medication> medicationList;
    private MedicationAdapter medicationAdapter;
    private Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        this.medicationRecyclerView = (RecyclerView) findViewById(R.id.medicationRecyclerView);
        this.medicationRecyclerView.addItemDecoration(new DividerItemDecoration(this.medicationRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        this.medicationList = new ArrayList<>();
        this.medicationAdapter = new MedicationAdapter(this.medicationList, this);
        this.medicationRecyclerView.setAdapter(this.medicationAdapter);
        this.repo = new Repository(this);
        this.medicationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.retrieveMedication();
        medicationAdapter.notifyDataSetChanged();

    }

    private void retrieveMedication() {
        this.repo.retrieveMedicationTask().observe(this, new Observer<List<Medication>>() {
            @Override
            public void onChanged(@Nullable List<Medication> medication) {
                if (medicationList.size() > 0) {
                    medicationList.clear();
                }
                if (medication != null) {
                    medicationList.addAll(medication);
                }
                medicationAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onMedicationClick(int position) {
        Intent intent = new Intent(MedicationActivity.this, MedicationDetailsActivity.class);
        Medication med=this.medicationList.get(position);
        System.out.println("MED: "+med.toString());
        intent.putExtra("medicationName",med.getName());
        intent.putExtra("medicationQuant",med.getQuantity());
        intent.putExtra("medicationTime",med.getTime());
        intent.putExtra("medicationID",med.getId());
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.medication_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add_medication:
                Intent loginPageIntent = new Intent(getApplicationContext(), AddMedication.class);
                startActivity(loginPageIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteMedication(Medication medication) {
        this.medicationList.remove(medication);
        this.medicationAdapter.notifyDataSetChanged();
        this.repo.deleteMedication(medication);
    }


}