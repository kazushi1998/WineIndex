package com.example.wineindex.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wineindex.database.entity.VineyardEntity;

import java.util.List;

@Dao
public interface VineyardDao {
    @Query("SELECT * FROM VineyardEntity")
    LiveData<List<VineyardEntity>> getAll();

    @Query("SELECT * FROM VineyardEntity WHERE VineyardName LIKE:name")
    LiveData<VineyardEntity> findByName(String name);

    @Insert
    long insert(VineyardEntity vineyard) throws SQLiteConstraintException;

    @Update
    void update(VineyardEntity vineyard);

    @Delete
    void delete(VineyardEntity vineyard);

    @Query("DELETE FROM VineyardEntity")
    void deleteAll();
}
