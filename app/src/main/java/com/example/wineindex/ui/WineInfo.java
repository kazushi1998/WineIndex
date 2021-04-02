package com.example.wineindex.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wineindex.R;
import com.example.wineindex.adapter.RecyclerAdapter;
import com.example.wineindex.database.entity.RetailerEntity;
import com.example.wineindex.database.entity.WineEntity;
import com.example.wineindex.viewmodel.RetailerListViewModel;
import com.example.wineindex.viewmodel.RetailerViewModel;
import com.example.wineindex.viewmodel.WineListViewModel;

import java.util.List;

public class WineInfo extends AppCompatActivity {

    private TextView wineInfoName;
    private String vineyardName;

    private Button tvRetailer;
    private String retailerName;

    private TextView tvDescription;
    private String description;

    private RetailerEntity retailer;
    private RecyclerAdapter recyclerAdapter;
    private RetailerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vineyardName = getIntent().getStringExtra("vineyardName");
        retailerName = getIntent().getStringExtra("retailerName");
        description = getIntent().getStringExtra("description");

        wineInfoName = findViewById(R.id.wineInfoName);
        wineInfoName.setText(vineyardName);

        tvRetailer = (Button)findViewById(R.id.tvRetailer);
        tvRetailer.setText(retailerName);

        tvDescription = findViewById(R.id.tvWineDescription);
        tvDescription.setText(description);

        RetailerViewModel.Factory factoryRetailer = new RetailerViewModel.Factory(getApplication(),retailerName);
        viewModel = new ViewModelProvider(this, factoryRetailer).get(RetailerViewModel.class);
        viewModel.getRetailer().observe(this, retailerEntity -> {
            retailer=retailerEntity;
        });

        tvRetailer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+retailer.getWebsite().toString()));

                startActivity(browserIntent);
            }
        });


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