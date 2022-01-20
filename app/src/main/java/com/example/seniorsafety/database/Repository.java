package com.example.seniorsafety.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.seniorsafety.controllers.DeleteAsyncTask;
import com.example.seniorsafety.controllers.InsertAsyncTask;
import com.example.seniorsafety.controllers.UpdateAsyncTask;
import com.example.seniorsafety.managers.MedicationReminder;

import java.util.Calendar;
import java.util.List;

public class Repository {
    private MedicationDB medicationDB;

    public Repository(Context context) {
        medicationDB = MedicationDB.getInstance(context);
    }

    public void insertMedication(Medication medication,Context context,int hour,int minute){
        new InsertAsyncTask(medicationDB.getMedicationDao()) {
            @Override
            public void postExecute(Long aLong) {
                System.out.println("VALOR DO ID: "+aLong);
                MedicationReminder mr = new MedicationReminder(context);
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);
                Medication med=new Medication(medication.getName(),medication.getQuantity(),medication.getTime());
                int requestCode=aLong.intValue();
                mr.setReminder(med, c,requestCode);
            }
        }.execute(medication);
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

    public void postExecute(long aLong) {


    }
}
