package com.example.seniorsafety.controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.seniorsafety.database.Medication;
import com.example.seniorsafety.database.MedicationDao;

public abstract class InsertAsyncTask extends AsyncTask<Medication, Void, Long> {
    private static final String TAG = "InsertAsyncTask";
    private MedicationDao mMedicamentionDao;

    public InsertAsyncTask(MedicationDao dao) {
        this.mMedicamentionDao = dao;
    }

    @Override
    protected Long doInBackground(Medication... medication) {
        Log.d(TAG, "doInBackground: thread: " + Thread.currentThread().getName());
        long[] id=this.mMedicamentionDao.insertMedication(medication);
        System.out.println("ID: "+id[0]);
        return id[0];
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        postExecute(aLong);
    }

    public abstract void postExecute(Long aLong);
}
