package com.example.wineindex.ui.Wines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wineindex.R;
import com.example.wineindex.adapter.RecyclerAdapter;
import com.example.wineindex.database.entity.FavoriteEntity;
import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.database.entity.WineEntity;
import com.example.wineindex.ui.Settings.Settings;
import com.example.wineindex.ui.AddWine.AddWine;
import com.example.wineindex.ui.MainActivity;
import com.example.wineindex.util.RecyclerViewItemClickListener;
import com.example.wineindex.viewmodel.VineyardViewModel;
import com.example.wineindex.viewmodel.WineListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VineyardInfo extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private VineyardViewModel viewModel;

    private VineyardEntity vineyard;

    private TextView tvVineyardName;
    private TextView tvDescription;

    private static final String TAG = "MainActivity";

    private List<WineEntity> wines;
    private RecyclerAdapter recyclerAdapter;
    private WineListViewModel listviewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vineyard_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String vineyardName = getIntent().getStringExtra("vineyardName");

        RecyclerView recyclerView = findViewById(R.id.winesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        wines = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + wines.get(position).toString());

                Intent intent = new Intent(VineyardInfo.this, MainActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("vineyardName", wines.get(position).getName());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + wines.get(position).toString());
            }
        });

        WineListViewModel.Factory factoryWine = new WineListViewModel.Factory(getApplication());
        listviewModel = new ViewModelProvider(this, factoryWine).get(WineListViewModel.class);
        listviewModel.getWines().observe(this, wineEntities -> {
            if (wineEntities != null) {
                wines = wineEntities;
                recyclerAdapter.setData(wines);
            }
        });

        tvVineyardName = findViewById(R.id.textView11);
        tvDescription = findViewById(R.id.tvDescription);

        VineyardViewModel.Factory factory = new VineyardViewModel.Factory(getApplication(), vineyardName);
        viewModel = ViewModelProviders.of(this, factory).get(VineyardViewModel.class);
        viewModel.getVineyard().observe(this, vineyardEntity -> {
            if (vineyardEntity != null) {
                vineyard = vineyardEntity;
                updateContent();
            }
        });
        fabAdd = findViewById(R.id.floatingActionButton);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAddWine();
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_vineyard:
                openActivityMain();
                break;
            case R.id.action_favorites:
                openActivityFavorites();
                break;
            case R.id.action_settings:
                openActivitySettings();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void openActivityFavorites() {
        Intent intent = new Intent(this, FavoriteEntity.class);
        startActivity(intent);
    }

    public void openActivitySettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void openActivityMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openActivityAddWine() {
        Intent intent = new Intent(this, AddWine.class);
        startActivity(intent);
    }

    private void updateContent() {
        if (vineyard != null) {
            tvVineyardName.setText(vineyard.getName());
            tvDescription.setText(vineyard.getDescription());

        }
    }

}