package com.example.wineindex.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.wineindex.database.entity.VineyardEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VineyardListLiveData extends LiveData<List<VineyardEntity>> {
    private static final String TAG = "VineyardsLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public VineyardListLiveData(DatabaseReference ref) {
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
            setValue(toVineyardList(snapshot));

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e(TAG, "Can't listen to query " + reference, error.toException());

        }

        private List<VineyardEntity> toVineyardList(DataSnapshot snapshot) {
            List<VineyardEntity> vineyards = new ArrayList<>();
            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                VineyardEntity entity = childSnapshot.getValue(VineyardEntity.class);
                entity.setName(childSnapshot.getKey());
                vineyards.add(entity);
            }
            return vineyards;
        }
    }
}
