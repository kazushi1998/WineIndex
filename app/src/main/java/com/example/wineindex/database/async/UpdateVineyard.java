package com.example.wineindex.database.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.wineindex.database.AppDataBase;
import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.util.OnAsyncEventListener;

public class UpdateVineyard extends AsyncTask<VineyardEntity, Void, Void> {
    private AppDataBase dataBase;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateVineyard(Context context, OnAsyncEventListener callback) {
        dataBase = AppDataBase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(VineyardEntity... vineyards) {
        try {
            for(VineyardEntity vineyard : vineyards) {
                dataBase.vineyardDao().update(vineyard);
            }
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(callback != null) {
            if(exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
