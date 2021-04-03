package com.example.wineindex.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wineindex.R;
import com.example.wineindex.database.entity.RetailerEntity;
import com.example.wineindex.database.entity.WineEntity;
import com.example.wineindex.database.repository.WineRepository;
import com.example.wineindex.util.OnAsyncEventListener;
import com.example.wineindex.viewmodel.RetailerListViewModel;
import com.example.wineindex.viewmodel.WineViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class WineEdit extends AppCompatActivity {

    private static final String TAG = "WineEdit";

    private TextView tvWineName;
    private EditText etDescription;
    private String wineName;
    private String retailer;
    private String description;
    private Spinner sRetailer;
    private WineEntity wine;

    private WineRepository wineRepository;

    private String vineyardName;

    private WineViewModel wineViewModel;

    private FloatingActionButton deleteButton;
    private FloatingActionButton updateButton;

    private Toast toast;

    private RetailerListViewModel listViewModel;

    public WineEdit() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Edit Wine");

        wineName = getIntent().getStringExtra("wineName");
        description = getIntent().getStringExtra("wineDescription");
        retailer = getIntent().getStringExtra("retailer");
        vineyardName = getIntent().getStringExtra("vineyardName");

        wine = (WineEntity) getIntent().getSerializableExtra("wineEntity");

        tvWineName = findViewById(R.id.wineEdit_wineName);
        etDescription = findViewById(R.id.wineEdit_description);
        updateButton = findViewById(R.id.wineEdit_updateButton);
        deleteButton = findViewById(R.id.wineEdit_deleteButton);


        updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                wineName = tvWineName.getText().toString();
                retailer = sRetailer.getSelectedItem().toString();
                description = etDescription.getText().toString();
                saveChanges(wineName, retailer, description);
                openActivityVineyardInfo();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WineViewModel.Factory factory = new WineViewModel.Factory(getApplication(), wineName);
                wineViewModel = new ViewModelProvider(WineEdit.this, factory).get(WineViewModel.class);

                    toast = Toast.makeText(WineEdit.this, "The Wine has been deleted", Toast.LENGTH_SHORT);
                    toast.show();
                    openActivityVineyardInfo();
                    wineViewModel.deleteWine(wine, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Delete Wine: success");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "Delete Wine: failure", e);
                        }
                    });
                    return;

                }

            }
        );

        List<String> retailerNames = new ArrayList<String>();
        retailerNames.add("Coop");
        RetailerListViewModel.Factory factory = new RetailerListViewModel.Factory(getApplication());
        listViewModel = new ViewModelProvider(this, factory).get(RetailerListViewModel.class);
        listViewModel.getRetailers().observe(this, retailersEntities -> {
            if (retailersEntities != null) {
                for (RetailerEntity retailer : retailersEntities) {
                    if (!retailer.getName().equals("Coop")) {
                        retailerNames.add(retailer.getName());
                    }
                }
            }
        });
        sRetailer = findViewById(R.id.wineEdit_sRetailer);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, retailerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sRetailer.setAdapter(adapter);

        sRetailer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(sRetailer.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tvWineName.setText(wineName);
        etDescription.setText(description);
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
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

    public void openActivityVineyardInfo() {
        Intent intent = new Intent(this, VineyardInfo.class);
        intent.putExtra("vineyardName", vineyardName);
        startActivity(intent);
    }
    public void saveChanges(String wineName, String retailer, String description) {

        WineViewModel.Factory factory = new WineViewModel.Factory(getApplication(), wineName);
        wineViewModel = new ViewModelProvider(this, factory).get(WineViewModel.class);

        if (tvWineName.getText().toString().equals("")) {
            toast = Toast.makeText(WineEdit.this, "No Winename was entered", Toast.LENGTH_SHORT);
            toast.show();
            tvWineName.requestFocus();
            return;
        }

        wine.setName(wineName);
        wine.setDescription(description);
        wine.setRetailer(retailer);
        wine.setVineyard(vineyardName);
        System.out.println(wine.getName().toString());
        wineViewModel.updateWine(wine, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateClient: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateClient: failure", e);
            }
        });
    }

}