package com.example.wineindex;

import android.app.ListActivity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private ListView listview;
    private ImageButton buttonFavorites;

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

        buttonFavorites = findViewById(R.id.imageButton_star);
        buttonFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityFavorites();
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

    public void openActivityFavorites() {
        Intent intent = new Intent(this, Favorites.class);
        startActivity(intent);
    }

    public void openActivityVineyardInfo() {
        Intent intent = new Intent(this, VineyardInfo.class);
        startActivity(intent);
    }
}