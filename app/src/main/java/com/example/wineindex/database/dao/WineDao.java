package com.example.wineindex.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wineindex.database.entity.Wine;

import java.util.List;

@Dao
public interface WineDao {
    @Query("SELECT * FROM wine")
    List<Wine> getAll();

    @Query("SELECT * FROM wine WHERE WineName LIKE:name")
    Wine findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWine(Wine wine);

    @Update
    void updateWine(Wine wine);

    @Delete
    void deleteWine(Wine wine);
}
