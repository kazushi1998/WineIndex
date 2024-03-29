package com.example.wineindex.ui;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wineindex.R;
import com.example.wineindex.adapter.RecyclerAdapter;
import com.example.wineindex.adapter.VineyardList;
import com.example.wineindex.database.AppDataBase;
import com.example.wineindex.database.DatabaseInitializer;
import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.database.repository.VineyardRepository;
import com.example.wineindex.ui.Favorites.Favorites;
import com.example.wineindex.ui.Settings.Settings;
import com.example.wineindex.ui.Wines.VineyardInfo;
import com.example.wineindex.util.RecyclerViewItemClickListener;
import com.example.wineindex.viewmodel.VineyardListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FloatingActionButton buttonAdd;

    private List<VineyardEntity> vineyards;
    private RecyclerAdapter recyclerAdapter;
    private VineyardListViewModel viewModel;

    private VineyardList vineyardList;
    private ListView listView;
    private String vineyardName[] = {
            "0Visperterminen",
            "0Varen",
            "0Salgesch",
            "0Siders"
    };

    private String vineyardDescription[] = {
            "0Europas höchster Weinberg.",
            "0Die Weininsel im Wallis.",
            "0Will wier ine räbe läbe.",
            "0Wasser predigen, Wein trinken."
    };

    private Integer vineyardPicture[] = {
            R.drawable.vy_visperterminen,
            R.drawable.vy_varen,
            R.drawable.vy_salgesch,
            R.drawable.vy_siders
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseInitializer.populateDatabase(AppDataBase.getInstance(getApplicationContext()));

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Vineyards");

        RecyclerView recyclerView = findViewById(R.id.vineyardsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        vineyards = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(position < 0) {
                    position = 0;
                }

                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + vineyards.get(position).toString());

                Intent intent = new Intent(MainActivity.this, VineyardInfo.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("vineyardName", vineyards.get(position).getName());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + vineyards.get(position).toString());
            }
        });

        buttonAdd = findViewById(R.id.floatingActionButton);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

/*
        listView = (ListView) findViewById(android.R.id.list);

        vineyardList = new VineyardList(this, vineyardName, vineyardDescription, vineyardPicture);
        listView.setAdapter(vineyardList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                */
/*openActivityVineyardInfo();
                Toast.makeText(getApplicationContext(), "You selected " + vineyards.get(position).getName() + ".", Toast.LENGTH_SHORT).show();*//*


                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + vineyards.get(position).toString());

                Intent intent = new Intent(MainActivity.this, VineyardInfo.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("vineyardName", vineyards.get(position).getName());
                startActivity(intent);
            }
        });
*/

        VineyardListViewModel.Factory factory = new VineyardListViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(VineyardListViewModel.class);
        viewModel.getVineyards().observe(this, vineyardEntities -> {
            if(vineyardEntities != null) {
                vineyards = vineyardEntities;
                recyclerAdapter.setData(vineyards);
                //vineyardList.setList(vineyards);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_vineyard); //toolbar.getMenu().findItem(R.id.action_vineyard);
        item.setVisible(false);

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
        Intent intent = new Intent(this, Favorites.class);
        startActivity(intent);
    }

    public void openActivitySettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void openActivityVineyardInfo() {
        Intent intent = new Intent(this, VineyardInfo.class);
            startActivity(intent);
    }
}