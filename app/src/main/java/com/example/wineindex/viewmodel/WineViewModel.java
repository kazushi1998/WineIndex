package com.example.wineindex.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.database.entity.WineEntity;
import com.example.wineindex.database.repository.VineyardRepository;
import com.example.wineindex.database.repository.WineRepository;

public class WineViewModel extends AndroidViewModel {
    private WineRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<WineEntity> observableClient;

    public WineViewModel(@NonNull Application application,
                             final String name, WineRepository wineRepository) {
        super(application);

        repository = wineRepository;

        applicationContext = application.getApplicationContext();

        observableClient = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClient.setValue(null);

        LiveData<WineEntity> wine = repository.getWine(name, applicationContext);

        // observe the changes of the client entity from the database and forward them
        observableClient.addSource(wine, observableClient::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String name;

        private final WineRepository repository;

        public Factory(@NonNull Application application, String name) {
            this.application = application;
            this.name = name;
            repository = WineRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new WineViewModel(application, name, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<WineEntity> getWine() {
        return observableClient;
    }

}