package com.example.seniorsafety.controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.seniorsafety.database.Medication;
import com.example.seniorsafety.database.MedicationDao;

public class UpdateAsyncTask extends AsyncTask<Medication, Void, Void> {
    private static final String TAG = "UpdateAsyncTask";
    private MedicationDao mMedicationDao;

    public UpdateAsyncTask(MedicationDao dao){
        this.mMedicationDao= dao;
    }

    @Override
    protected Void doInBackground(Medication... medication) {
        Log.d(TAG, "doInBackground: thread: "+ Thread.currentThread().getName());
       this.mMedicationDao.update(medication);
        return null;
    }
}
