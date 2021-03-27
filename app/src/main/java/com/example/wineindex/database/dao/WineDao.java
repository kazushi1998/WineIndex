package com.example.wineindex.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wineindex.database.entity.WineEntity;

import java.util.List;

@Dao
public interface WineDao {
    @Query("SELECT * FROM WineEntity")
    List<WineEntity> getAll();

    @Query("SELECT * FROM WineEntity WHERE WineName LIKE:name")
    WineEntity findByName(String name);

    @Insert
    long insert(WineEntity wine) throws SQLiteConstraintException;

    @Update
    void update(WineEntity wine);

    @Delete
    void delete(WineEntity wine);

    @Query("DELETE FROM WineEntity")
    void deleteAll();
}
