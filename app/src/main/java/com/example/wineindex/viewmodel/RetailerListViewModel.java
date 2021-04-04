package com.example.wineindex.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.wineindex.database.entity.RetailerEntity;
import com.example.wineindex.database.repository.RetailerRepository;


import java.util.List;

public class RetailerListViewModel extends AndroidViewModel {
    private RetailerRepository repository;

    private final MediatorLiveData<List<RetailerEntity>> observableVineyards;

    public RetailerListViewModel(@NonNull Application application, RetailerRepository retailerRepository) {
        super(application);

        repository = retailerRepository;

        observableVineyards = new MediatorLiveData<>();
        observableVineyards.setValue(null);

        LiveData<List<RetailerEntity>> retailers = repository.getAllRetailers();
        observableVineyards.addSource(retailers, observableVineyards::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final RetailerRepository retailerRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            retailerRepository = RetailerRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new RetailerListViewModel(application, retailerRepository);
        }
    }

    public LiveData<List<RetailerEntity>> getRetailers() {
        return observableVineyards;
    }
}