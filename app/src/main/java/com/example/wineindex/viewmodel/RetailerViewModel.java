package com.example.wineindex.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.wineindex.database.entity.RetailerEntity;
import com.example.wineindex.database.repository.RetailerRepository;


public class RetailerViewModel extends AndroidViewModel {
    private RetailerRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<RetailerEntity> observableClient;

    public RetailerViewModel(@NonNull Application application,
                             final String name, RetailerRepository retailerRepository) {
        super(application);

        repository = retailerRepository;

        applicationContext = application.getApplicationContext();

        observableClient = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClient.setValue(null);

        LiveData<RetailerEntity> client = repository.getRetailer(name);

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

        private final RetailerRepository repository;

        public Factory(@NonNull Application application, String name) {
            this.application = application;
            this.name = name;
            repository = RetailerRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new RetailerViewModel(application, name, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<RetailerEntity> getRetailer() {
        return observableClient;
    }

}
