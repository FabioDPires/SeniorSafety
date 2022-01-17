package com.example.seniorsafety.controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.seniorsafety.database.Medication;
import com.example.seniorsafety.database.MedicationDao;

public class InsertAsyncTask extends AsyncTask<Medication, Void, Void> {
    private static final String TAG = "InsertAsyncTask";
    private MedicationDao mMedicamentionDao;

    public InsertAsyncTask(MedicationDao dao) {
        this.mMedicamentionDao = dao;
    }

    @Override
    protected Void doInBackground(Medication... medication) {
        Log.d(TAG, "doInBackground: thread: " + Thread.currentThread().getName());
        this.mMedicamentionDao.insertMedication(medication);
        return null;
    }
}
