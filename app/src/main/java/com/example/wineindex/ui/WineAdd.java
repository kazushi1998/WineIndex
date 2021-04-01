package com.example.wineindex.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.wineindex.R;
import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.database.entity.WineEntity;
import com.example.wineindex.database.repository.WineRepository;
import com.example.wineindex.util.OnAsyncEventListener;
import com.example.wineindex.viewmodel.WineViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.MenuItem;
import android.widget.EditText;

import java.util.List;

public class WineAdd extends AppCompatActivity {

    private static final String TAG = "AddWine";

    private FloatingActionButton fabAccept;
    private List<VineyardEntity> vineyards;

    private String vineyardName;

    private EditText wineName;

    private WineEntity wine;
    private WineViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Add Wine");

        vineyardName = getIntent().getStringExtra("vineyardName");

        fabAccept = findViewById(R.id.floatingActionButton);

        wineName = findViewById(R.id.edit_name);

        WineViewModel.Factory factory = new WineViewModel.Factory(getApplication(), wineName.getText().toString());
        viewModel = new ViewModelProvider(this, factory).get(WineViewModel.class);
        viewModel.getWine().observe(this, wineEntity -> {
            if (wineEntity != null) {
                wine = wineEntity;
            }
        });

        fabAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWine(wineName.getText().toString(),vineyardName);
                openActivityVineyardInfo();
            }
        });
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

            case R.id.action_settings:
                openActivitySettings();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openActivityVineyardInfo() {
        Intent intent = new Intent(this, VineyardInfo.class);
        intent.putExtra("vineyardName", vineyardName);
        startActivity(intent);

    }

    public void openActivityMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openActivitySettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
    private void createWine(String wineName, String vineyardName) {

        wine = new WineEntity();
        wine.setName(wineName);
        wine.setVineyard(vineyardName);

        viewModel.createWine(wine, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createClient: success");
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createClient: failure", e);
            }
        });
    }
}