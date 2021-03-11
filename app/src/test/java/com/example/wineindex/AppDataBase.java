package com.example.wineindex;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Favorites.class,Vineyards.class,Wine.class}, version =1)

public abstract class AppDataBase extends RoomDatabase {
    public abstract FavoritesDao favoritesDao();
    public abstract VineyardsDao vineyardsDao();
    public abstract WineDao wineDao();

}
