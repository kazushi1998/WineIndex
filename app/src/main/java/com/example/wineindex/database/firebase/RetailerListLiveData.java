package com.example.wineindex.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.wineindex.database.entity.RetailerEntity;
import com.example.wineindex.database.entity.VineyardEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetailerListLiveData extends LiveData<List<RetailerEntity>> {
    private static final String TAG = "RetailerLiveData";

    private final DatabaseReference reference;
    private final RetailerListLiveData.MyValueEventListener listener = new RetailerListLiveData.MyValueEventListener();

    public RetailerListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            setValue(toRetailerList(snapshot));

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e(TAG, "Can't listen to query " + reference, error.toException());

        }

        private List<RetailerEntity> toRetailerList(DataSnapshot snapshot) {
            List<RetailerEntity> retailers = new ArrayList<>();
            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                RetailerEntity entity = childSnapshot.getValue(RetailerEntity.class);
                entity.setName(childSnapshot.getKey());
                retailers.add(entity);
            }
            return retailers;
        }
    }
}
