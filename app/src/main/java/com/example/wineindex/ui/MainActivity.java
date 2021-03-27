package com.example.wineindex.ui;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wineindex.R;
import com.example.wineindex.adapter.RecyclerAdapter;
import com.example.wineindex.database.entity.FavoriteEntity;
import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.ui.Settings.Settings;
import com.example.wineindex.ui.Wines.VineyardInfo;
import com.example.wineindex.adapter.VineyardList;
import com.example.wineindex.util.RecyclerViewItemClickListener;
import com.example.wineindex.viewmodel.VineyardListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView listview;
    private FloatingActionButton buttonAdd;

    private List<VineyardEntity> vineyards;
    private RecyclerAdapter recyclerAdapter;
    private VineyardListViewModel viewModel;

    private ListView listView;
    private String vineyardName[] = {
            "Visperterminen",
            "Varen",
            "Salgesch",
            "Siders",
            "Visperterminen",
            "Varen",
            "Salgesch",
            "Siders"
    };

    private String vineyardDescription[] = {
            "Europas höchster Weinberg.",
            "Die Weininsel im Wallis.",
            "Will wier ine räbe läbe.",
            "Wasser predigen, Wein trinken.",
            "Europas höchster Weinberg.",
            "Die Weininsel im Wallis.",
            "Will wier ine räbe läbe.",
            "Wasser predigen, Wein trinken."
    };

    private Integer vineyardPicture[] = {
            R.drawable.vy_visperterminen,
            R.drawable.vy_varen,
            R.drawable.vy_salgesch,
            R.drawable.vy_siders,
            R.drawable.vy_visperterminen,
            R.drawable.vy_varen,
            R.drawable.vy_salgesch,
            R.drawable.vy_siders
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                System.out.println("CLICK");
            }

            @Override
            public void onItemLongClick(View v, int position) {
                System.out.println("LONG CLICK");
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

        VineyardList vineyardList = new VineyardList(this, vineyardName, vineyardDescription, vineyardPicture);
        listView.setAdapter(vineyardList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                openActivityVineyardInfo();
                Toast.makeText(getApplicationContext(), "You selected " + vineyardName[position] + ".", Toast.LENGTH_SHORT).show();
            }
        });
        */

        VineyardListViewModel.Factory factory = new VineyardListViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(VineyardListViewModel.class);
        viewModel.getVineyards().observe(this, vineyardEntities -> {
            if(vineyardEntities != null) {
                vineyards = vineyardEntities;
                recyclerAdapter.setData(vineyards);
                System.out.println(vineyards.get(0).getName());
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
        Intent intent = new Intent(this, FavoriteEntity.class);
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