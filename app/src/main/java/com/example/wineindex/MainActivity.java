package com.example.wineindex;

import android.app.ListActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wineindex.CustomCountryList;
import com.example.wineindex.R;


public class MainActivity extends ListActivity {

    private ListView listView;
    private String countryNames[] = {
            "Visperterminen",
            "Varen",
            "Salgesch",
            "Sierre"
    };

    private String capitalNames[] = {
            "Europas höchster Weinberg.",
            "Die Weininsel im Wallis.",
            "Will wier ine räbe läbe.",
            "Wasser predigen, Wein trinken."
    };

    private Integer imageid[] = {
            R.drawable.bhutan,
            R.drawable.bhutan,
            R.drawable.bhutan,
            R.drawable.bhutan
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(android.R.id.list);

        // For populating list data
        CustomCountryList customCountryList = new CustomCountryList(this, countryNames, capitalNames, imageid);
        listView.setAdapter(customCountryList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "You Selected " + countryNames[position] + " as Country", Toast.LENGTH_SHORT).show();
            }
        });
    }
}