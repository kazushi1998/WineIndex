package com.example.wineindex.ui.Wines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wineindex.R;
import com.example.wineindex.database.entity.FavoriteEntity;
import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.ui.Settings.Settings;
import com.example.wineindex.ui.AddWine.AddWine;
import com.example.wineindex.ui.MainActivity;
import com.example.wineindex.viewmodel.VineyardViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VineyardInfo extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private VineyardViewModel viewModel;

    private VineyardEntity vineyard;

    private TextView tvVineyardName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vineyard_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String vineyardName = getIntent().getStringExtra("vineyardName");

        tvVineyardName = findViewById(R.id.textView11);

        VineyardViewModel.Factory factory = new VineyardViewModel.Factory(getApplication(), vineyardName);
        viewModel = ViewModelProviders.of(this,factory).get(VineyardViewModel.class);
        viewModel.getVineyard().observe(this,vineyardEntity -> {
            if(vineyardEntity != null){
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

        }
    }

}