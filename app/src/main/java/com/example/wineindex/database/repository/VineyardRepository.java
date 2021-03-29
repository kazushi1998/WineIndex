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

    public LiveData<VineyardEntity> getVineyard(final String name, Context context) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vineyards");
        return new VineyardLiveData(reference);
    }

    public LiveData<List<VineyardEntity>> getAllVineyards() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("vineyard").child("Salgesch").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    System.out.println("Error getting data" +task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    System.out.println("Else" +String.valueOf(task.getResult().getValue()));
                }
            }
        });
        return new VineyardListLiveData(reference);
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
    }
}
