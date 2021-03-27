package com.example.wineindex.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.wineindex.database.entity.Vineyards;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDataBase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);    //PopulateDbAsync ist eine interne klasse die hier unten gemacht wird
        task.execute();
    }

    private static void addVineyard(final AppDataBase db, final int id, final String vineyardname, final String info){

        Vineyards vineyard = new Vineyards(id,vineyardname,info);
        db.vineyardsDao().insert(vineyard);

    }

    private static void populateWithTestData(AppDataBase db){
        db.favoritesDao().deleteAll();


        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

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

        db.wineDao().deleteAll();

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
