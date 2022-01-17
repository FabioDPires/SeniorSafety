package com.example.seniorsafety.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MedicationDao {

    @Insert
    long[] insertMedication(Medication... medication);

    @Query("SELECT * FROM medication")
    LiveData<List<Medication>> getMedication();

    @Delete
    int delete(Medication... medication);

    @Update
    int update(Medication... medication);

}