package com.example.wineindex;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wineindex.CustomCountryList;
import com.example.wineindex.R;


public class MainActivity extends ListActivity {
    private ImageButton buttonFavorites;

    private ListView listView;
    private String countryNames[] = {
            "Visperterminen",
            "Varen",
            "Salgesch",
            "Siders"
    };

    private String capitalNames[] = {
            "Europas höchster Weinberg.",
            "Die Weininsel im Wallis.",
            "Will wier ine räbe läbe.",
            "Wasser predigen, Wein trinken."
    };

    private Integer imageid[] = {
            R.drawable.vy_visperterminen,
            R.drawable.vy_varen,
            R.drawable.vy_salgesch,
            R.drawable.vy_siders
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonFavorites = findViewById(R.id.imageButton_star);
        buttonFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityFavorites();
            }
        });

        ListView listView = (ListView) findViewById(android.R.id.list);

        // For populating list data
        CustomCountryList customCountryList = new CustomCountryList(this, countryNames, capitalNames, imageid);
        listView.setAdapter(customCountryList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                openActivityVineyardInfo();
                Toast.makeText(getApplicationContext(), "You Selected " + countryNames[position] + " as Country", Toast.LENGTH_SHORT).show();
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