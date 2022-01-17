package com.example.seniorsafety.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.seniorsafety.controllers.DeleteAsyncTask;
import com.example.seniorsafety.controllers.InsertAsyncTask;
import com.example.seniorsafety.controllers.UpdateAsyncTask;

import java.util.List;

public class Repository {
    private MedicationDB medicationDB;

    public Repository(Context context) {
        medicationDB = MedicationDB.getInstance(context);
    }

    public void insertMedication(Medication medication){
        new InsertAsyncTask(medicationDB.getMedicationDao()).execute(medication);
    }

    public void updateMedication(Medication medication){
        new UpdateAsyncTask(medicationDB.getMedicationDao()).execute(medication);
    }

    public LiveData<List<Medication>> retrieveMedicationTask() {

        return  medicationDB.getMedicationDao().getMedication();
    }

    public void deleteMedication(Medication medication){
        new DeleteAsyncTask(medicationDB.getMedicationDao()).execute(medication);
    }
}
