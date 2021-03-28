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
import com.example.wineindex.database.repository.VineyardRepository;
import com.example.wineindex.util.OnAsyncEventListener;

public class VineyardViewModel  extends AndroidViewModel {
    private VineyardRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<VineyardEntity> observableClient;

    public VineyardViewModel(@NonNull Application application,
                           final String name, VineyardRepository vineyardRepository) {
        super(application);

        repository = vineyardRepository;

        applicationContext = application.getApplicationContext();

        observableClient = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClient.setValue(null);

        LiveData<VineyardEntity> client = repository.getVineyard(name, applicationContext);

        // observe the changes of the client entity from the database and forward them
        observableClient.addSource(client, observableClient::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String name;

        private final VineyardRepository repository;

        public Factory(@NonNull Application application, String name) {
            this.application = application;
            this.name = name;
            repository = VineyardRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VineyardViewModel(application, name, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<VineyardEntity> getVineyard() {
        return observableClient;
    }

    public void createVineyard(VineyardEntity client, OnAsyncEventListener callback) {
        repository.insert(client, callback, applicationContext);
    }

    public void updateVineyard(VineyardEntity client, OnAsyncEventListener callback) {
        repository.update(client, callback, applicationContext);
    }

    public void deleteVineyard(VineyardEntity client, OnAsyncEventListener callback) {
        repository.delete(client, callback, applicationContext);
    }
}
