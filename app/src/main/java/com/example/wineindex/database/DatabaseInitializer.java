package com.example.wineindex.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.wineindex.database.entity.Favorites;
import com.example.wineindex.database.entity.Vineyards;
import com.example.wineindex.database.entity.Wine;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDataBase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);    //PopulateDbAsync ist eine interne klasse die hier unten gemacht wird
        task.execute();
    }

    private static void addFavorites(final AppDataBase db, int id, int wid){

        Favorites favorites = new Favorites(id,wid);
        db.favoritesDao().insert(favorites);
    }

    private static void addVineyard(final AppDataBase db, final int id, final String vineyardname, final String info){

        Vineyards vineyard = new Vineyards(id,vineyardname,info);
        db.vineyardsDao().insert(vineyard);
    }

    private static void addWine(final AppDataBase db, int id, String name, String winery){

        Wine wine = new Wine(id,name,winery);
        db.wineDao().insert(wine);
    }


    private static void populateWithTestData(AppDataBase db){
       //resetten
        db.favoritesDao().deleteAll();
        /**
         * Hier kommen die anfänglichen Favoriten rein falls es welche gibt.
         */
        //Testfavorite
        addFavorites(db,
               1,1 );

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //resetten
        db.vineyardsDao().deleteAll();

        addVineyard(db,
                1, "Visperterminen", "Europas höchster Weinberg.");
        addVineyard(db,
                2, "Varen","Die Weininsel im Wallis.");
        addVineyard(db,
                3, "Salgesch","Will wier ine räbe läbe.");
        addVineyard(db,
                4, "Siders","Wasser predigen, Wein trinken.");

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //resetten
        db.wineDao().deleteAll();
        /**
         * Hier kommen die Weine rein, via addWine die am Anfang da sein sollen
         */
        //Testwein
        addWine(db,
                1,"Geile Wein","Winery Jean-pierre");

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDataBase database;

        PopulateDbAsync(AppDataBase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }

}
