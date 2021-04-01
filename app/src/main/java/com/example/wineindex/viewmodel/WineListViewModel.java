package com.example.wineindex.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.wineindex.database.entity.WineEntity;
import com.example.wineindex.database.repository.WineRepository;

import java.util.List;

public class WineListViewModel extends AndroidViewModel {
    private WineRepository repository;

    private final MediatorLiveData<List<WineEntity>> observableVineyards;

    public WineListViewModel(@NonNull Application application, WineRepository wineRepository) {
        super(application);

        repository = wineRepository;

        observableVineyards = new MediatorLiveData<>();
        observableVineyards.setValue(null);

        LiveData<List<WineEntity>> wine = repository.getAllWinesOfVineyard();
        observableVineyards.addSource(wine, observableVineyards::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final WineRepository vineyardRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            vineyardRepository = WineRepository.getInstance();
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new WineListViewModel(application, vineyardRepository);
        }
    }

    public LiveData<List<WineEntity>> getWines() {
        return observableVineyards;
    }
}