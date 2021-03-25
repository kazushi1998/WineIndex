package com.example.wineindex.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wineindex.database.entity.Favorites;

import java.util.List;

@Dao
public interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    List<Favorites> getAll();

    @Query("SELECT * FROM favorites WHERE wine_Id LIKE:id")
    Favorites findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Favorites favorites);

    @Update
    void updateFavorite(Favorites favorites);

    @Delete
    void deleteFavorite(Favorites favorites);
}
