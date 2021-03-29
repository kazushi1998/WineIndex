package com.example.wineindex.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.wineindex.adapter.VineyardList;
import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.database.repository.VineyardRepository;

import java.util.List;

public class VineyardListViewModel extends AndroidViewModel {
    private VineyardRepository repository;

    private final MediatorLiveData<List<VineyardEntity>> observableVineyards;

    public VineyardListViewModel(@NonNull Application application, VineyardRepository vineyardRepository) {
        super(application);

        repository = vineyardRepository;

        observableVineyards = new MediatorLiveData<>();
        observableVineyards.setValue(null);

        LiveData<List<VineyardEntity>> vineyards = repository.getAllVineyards();
        observableVineyards.addSource(vineyards, observableVineyards::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final VineyardRepository vineyardRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            vineyardRepository = VineyardRepository.getInstance();
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new VineyardListViewModel(application, vineyardRepository);
        }
    }

    public LiveData<List<VineyardEntity>> getVineyards() {
        return observableVineyards;
    }
}
