package com.example.wineindex.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.database.entity.WineEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WineListLiveData extends LiveData<List<WineEntity>> {
    private static final String TAG = "WinesLiveData";

    private final DatabaseReference reference;
    private final WineListLiveData.MyValueEventListener listener = new WineListLiveData.MyValueEventListener();

    public WineListLiveData(DatabaseReference ref) {
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
            setValue(toWineList(snapshot));

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e(TAG, "Can't listen to query " + reference, error.toException());

        }

        private List<WineEntity> toWineList(DataSnapshot snapshot) {
            List<WineEntity> wines = new ArrayList<>();
            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                WineEntity entity = childSnapshot.getValue(WineEntity.class);
                entity.setName(childSnapshot.getKey());
                wines.add(entity);
            }
            return wines;
        }
    }
}