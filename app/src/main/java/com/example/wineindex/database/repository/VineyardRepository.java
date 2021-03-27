package com.example.wineindex.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.wineindex.database.AppDataBase;
import com.example.wineindex.database.async.CreateVineyard;
import com.example.wineindex.database.async.DeleteVineyard;
import com.example.wineindex.database.async.UpdateClient;
import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.util.OnAsyncEventListener;

import java.util.List;

public class VineyardRepository {
    private static VineyardRepository instance;

    private VineyardRepository() {};

    public static VineyardRepository getInstance() {
        if(instance == null) {
            synchronized (VineyardRepository.class) {
                if(instance == null) {
                    instance = new VineyardRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<VineyardEntity> getVineyard(final String name, Context context) {
        return AppDataBase.getInstance(context).vineyardDao().findByName(name);
    }

    public LiveData<List<VineyardEntity>> getAllVineyards(Context context) {
        return AppDataBase.getInstance(context).vineyardDao().getAll();
    }

    public void insert(final VineyardEntity vineyard, OnAsyncEventListener callback, Context context) {
        new CreateVineyard(context, callback).execute(vineyard);
    }

    public void update(final VineyardEntity vineyard, OnAsyncEventListener callback, Context context) {
        new UpdateClient(context, callback).execute(vineyard);
    }

    public void delete(final VineyardEntity vineyard, OnAsyncEventListener callback, Context context) {
        new DeleteVineyard(context, callback).execute(vineyard);
    }
}
