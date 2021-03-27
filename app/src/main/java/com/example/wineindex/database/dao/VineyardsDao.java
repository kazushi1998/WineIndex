package com.example.wineindex.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wineindex.database.entity.VineyardEntity;

import java.util.List;

@Dao
public interface VineyardsDao {
    @Query("SELECT * FROM VineyardEntity")
    List<VineyardEntity> getAll();

    @Query("SELECT * FROM VineyardEntity WHERE VineyardName LIKE:name")
    VineyardEntity findByName(String name);

    @Insert
    long insert(VineyardEntity vineyards) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVineyard(VineyardEntity vineyards);

    @Update
    void updateVineyards(VineyardEntity vineyards);

    @Delete
    void deleteVineyard(VineyardEntity vineyards);

    @Query("DELETE FROM VineyardEntity")
    void deleteAll();
}
