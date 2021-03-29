package com.example.wineindex.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.wineindex.database.AppDataBase;
import com.example.wineindex.database.async.CreateVineyard;
import com.example.wineindex.database.async.DeleteVineyard;
import com.example.wineindex.database.async.UpdateVineyard;
import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vineyards");
        return new VineyardLiveData(reference);
//        return AppDataBase.getInstance(context).vineyardDao().findByName(name);
    }

    public LiveData<List<VineyardEntity>> getAllVineyards(Context context) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vineyards");
        return new VineyardListLiveData(reference);
//        return AppDataBase.getInstance(context).vineyardDao().getAll();
    }

    public void insert(final VineyardEntity vineyard, OnAsyncEventListener callback, Context context) {
        String id = FirebaseDatabase.getInstance().getReference("vineyards").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("vineyards")
                .child(id)
                .setValue(vineyard, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
//        new CreateVineyard(context, callback).execute(vineyard);
    }

    public void update(final VineyardEntity vineyard, OnAsyncEventListener callback, Context context) {
        FirebaseDatabase.getInstance()
                .getReference("vineyards")
                .child(vineyard.getName())
                .updateChildren(vineyard.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
//        new UpdateVineyard(context, callback).execute(vineyard);
    }

    public void delete(final VineyardEntity vineyard, OnAsyncEventListener callback, Context context) {
        FirebaseDatabase.getInstance()
                .getReference("vineyards")
                .child(vineyard.getName())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
//        new DeleteVineyard(context, callback).execute(vineyard);
    }
}
