package com.example.wineindex.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.wineindex.database.dao.FavoritesDao;
import com.example.wineindex.database.dao.VineyardsDao;
import com.example.wineindex.database.dao.WineDao;
import com.example.wineindex.database.entity.Favorites;
import com.example.wineindex.database.entity.Vineyards;
import com.example.wineindex.database.entity.Wine;

@Database(entities = {Favorites.class, Vineyards.class, Wine.class}, version =1)

public abstract class AppDataBase extends RoomDatabase {
    public abstract FavoritesDao favoritesDao();
    public abstract VineyardsDao vineyardsDao();
    public abstract WineDao wineDao();

}
