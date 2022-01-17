package com.example.seniorsafety.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Medication.class}, version = 2)
public abstract class MedicationDB extends RoomDatabase {

    public static final String DATABASE_NAME = "medication_db";

    private static MedicationDB instance;

    public static MedicationDB getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MedicationDB.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract MedicationDao getMedicationDao();
}