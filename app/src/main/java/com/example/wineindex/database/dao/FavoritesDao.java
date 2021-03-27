package com.example.wineindex.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wineindex.database.entity.FavoriteEntity;

import java.util.List;

@Dao
public interface FavoritesDao {
    @Query("SELECT * FROM FavoriteEntity")
    List<FavoriteEntity> getAll();

    @Query("SELECT * FROM FavoriteEntity WHERE wine_Id LIKE:id")
    FavoriteEntity findById(int id);

    @Insert
    long insert(FavoriteEntity favorite) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteEntity favorite);

    @Update
    void updateFavorite(FavoriteEntity favorite);

    @Delete
    void deleteFavorite(FavoriteEntity favorite);

    @Query("DELETE FROM FavoriteEntity")
    void deleteAll();
}
