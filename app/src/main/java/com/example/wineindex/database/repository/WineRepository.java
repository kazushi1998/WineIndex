package com.example.wineindex.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.database.entity.WineEntity;
import com.example.wineindex.database.firebase.WineListLiveData;
import com.example.wineindex.database.firebase.WineLiveData;
import com.example.wineindex.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class WineRepository {
    public static WineRepository instance;

    public WineRepository() {};

    public static WineRepository getInstance() {
        if(instance == null) {
            synchronized (WineRepository.class) {
                if(instance == null) {
                    instance = new WineRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<WineEntity> getWine(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("wines").child(name);
        return new WineLiveData(reference);
    }

    public LiveData<List<WineEntity>> getAllWinesOfVineyard() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("wines");
        return new WineListLiveData(reference);
    }

    public void insert(final WineEntity wine, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance().getReference("wines").child(wine.getName());
        FirebaseDatabase.getInstance()
                .getReference("wines")
                .child(wine.getName())
                .setValue(wine, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final WineEntity wine, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("wines")
                .child(wine.getName())
                .updateChildren(wine.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final  WineEntity wine, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("wines")
                .child(wine.getName())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
