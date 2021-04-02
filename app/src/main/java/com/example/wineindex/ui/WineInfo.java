package com.example.wineindex.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.wineindex.R;

public class WineInfo extends AppCompatActivity {

    private TextView wineInfoName;
    private String vineyardName;

    private TextView tvRetailer;
    private String retailerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vineyardName = getIntent().getStringExtra("vineyardName");
        retailerName = getIntent().getStringExtra("retailer");

        wineInfoName = findViewById(R.id.wineInfoName);
        wineInfoName.setText(vineyardName);

        tvRetailer = findViewById(R.id.tvRetailer);
        tvRetailer.setText(retailerName);

        setTitle("Wine");
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

    public void openActivityMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openActivitySettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}