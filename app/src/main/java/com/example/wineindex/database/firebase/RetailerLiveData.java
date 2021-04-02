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

public class RetailerLiveData extends LiveData<RetailerEntity> {
    private static final String TAG = "RetailerLiveData";

    private final DatabaseReference reference;
    private final RetailerLiveData.MyValueEventListener listener = new RetailerLiveData.MyValueEventListener();

    public RetailerLiveData(DatabaseReference ref) {
        this.reference = ref;
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
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                RetailerEntity entity = dataSnapshot.getValue(RetailerEntity.class);
                entity.setName(dataSnapshot.getKey());
                entity.setWebsite(dataSnapshot.child("website").getValue().toString());
                setValue(entity);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
