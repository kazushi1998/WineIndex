package com.example.wineindex.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wineindex.R;
import com.example.wineindex.adapter.RecyclerAdapter;
import com.example.wineindex.database.entity.FavoriteEntity;
import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.database.entity.WineEntity;
import com.example.wineindex.database.repository.VineyardRepository;
import com.example.wineindex.database.repository.WineRepository;
import com.example.wineindex.ui.AddWine.AddWine;
import com.example.wineindex.ui.MainActivity;
import com.example.wineindex.ui.Settings.Settings;
import com.example.wineindex.ui.Wines.VineyardInfo;
import com.example.wineindex.util.RecyclerViewItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
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