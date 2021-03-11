package com.example.wineindex;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VineyardsDao {
    @Query("SELECT * FROM vineyards")
    List<Vineyards> getAll();

    @Query("SELECT * FROM vineyards WHERE VineyardName LIKE:name")
    Vineyards findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVineyard(Vineyards vineyards);

    @Update
    void updateVineyards(Vineyards vineyards);

    @Delete
    void deleteVineyard(Vineyards vineyards);
}
