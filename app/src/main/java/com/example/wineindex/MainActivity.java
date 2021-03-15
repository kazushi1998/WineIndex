package com.example.wineindex;

import android.app.ListActivity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    private ListView listview;
    private FloatingActionButton buttonAdd;

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

        buttonAdd = findViewById(R.id.floatingActionButton);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAddWine();
            }
        });

        listView = (ListView) findViewById(android.R.id.list);

        // For populating list data
        VineyardList vineyardList = new VineyardList(this, vineyardName, vineyardDescription, vineyardPicture);
        listView.setAdapter(vineyardList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                openActivityVineyardInfo();
                Toast.makeText(getApplicationContext(), "You Selected " + vineyardName[position] + " as Country", Toast.LENGTH_SHORT).show();
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
        if (id == R.id.action_favorites) {
            openActivityFavorites();
            return true;
        }

        if (id == R.id.action_settings) {
            openActivitySettings();
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

    public void openActivityAddWine() {
        Intent intent = new Intent(this, AddWine.class);
        startActivity(intent);
    }
}