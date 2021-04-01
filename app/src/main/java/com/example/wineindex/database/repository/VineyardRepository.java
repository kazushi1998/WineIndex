package com.example.wineindex.database.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.database.firebase.VineyardListLiveData;
import com.example.wineindex.database.firebase.VineyardLiveData;
import com.example.wineindex.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
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

    public LiveData<VineyardEntity> getVineyard(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vineyards").child(name);
        return new VineyardLiveData(reference);
    }

    public LiveData<List<VineyardEntity>> getAllVineyards() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vineyards");
        return new VineyardListLiveData(reference);

    }

    }

