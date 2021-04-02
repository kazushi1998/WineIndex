package com.example.wineindex.database.repository;

import androidx.lifecycle.LiveData;

import com.example.wineindex.database.entity.RetailerEntity;
import com.example.wineindex.database.firebase.RetailerListLiveData;
import com.example.wineindex.database.firebase.RetailerLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RetailerRepository {
    private static RetailerRepository instance;

    private RetailerRepository() {};

    public static RetailerRepository getInstance() {
        if(instance == null) {
            synchronized (RetailerRepository.class) {
                if(instance == null) {
                    instance = new RetailerRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<RetailerEntity> getRetailer(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("retailer").child(name);
        return new RetailerLiveData(reference);
    }

    public LiveData<List<RetailerEntity>> getAllRetailers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("retailer");
        return new RetailerListLiveData(reference);
    }
}
