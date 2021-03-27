package com.example.wineindex.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wineindex.database.dao.FavoriteDao;
import com.example.wineindex.database.dao.VineyardDao;
import com.example.wineindex.database.dao.WineDao;
import com.example.wineindex.database.entity.FavoriteEntity;
import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.database.entity.WineEntity;

import java.util.concurrent.Executors;

@Database(entities = {FavoriteEntity.class, VineyardEntity.class, WineEntity.class}, version =1)

public abstract class AppDataBase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static AppDataBase instance;

    private static final String DATABASE_NAME = "wineIndex-database";


    public abstract FavoriteDao favoriteDao();

    public abstract VineyardDao vineyardDao();

    public abstract WineDao wineDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDataBase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDataBase.class) {
                if (instance == null) {
                    instance = buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /**
     *Datenbank machen, neue Instance, SQLite datenbank erst wirklich beim ersten aufruf
     */

    private static AppDataBase buildDatabase(final Context appContext) {
        Log.i(TAG, "Database will be initialized.");
        return Room.databaseBuilder(appContext, AppDataBase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            AppDataBase database = AppDataBase.getInstance(appContext);
                            initializeDemoData(database);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }


    public static void initializeDemoData(final AppDataBase database) {
        Executors.newSingleThreadExecutor().execute(() -> {
            database.runInTransaction(() -> {
                Log.i(TAG, "Wipe database.");
                database.vineyardDao().deleteAll();
                database.favoriteDao().deleteAll();
                database.wineDao().deleteAll();

                DatabaseInitializer.populateDatabase(database);
            });
        });
    }

    /**
    *Prüfung Datenbank da oder no nüt
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            Log.i(TAG, "Database initialized.");
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }



}
