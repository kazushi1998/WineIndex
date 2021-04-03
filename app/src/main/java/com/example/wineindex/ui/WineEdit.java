package com.example.wineindex.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.wineindex.R;
import com.example.wineindex.database.entity.RetailerEntity;
import com.example.wineindex.database.entity.WineEntity;
import com.example.wineindex.viewmodel.RetailerListViewModel;

import java.util.ArrayList;
import java.util.List;

public class WineEdit extends AppCompatActivity {

    private EditText etWineName;
    private EditText etDescription;
    private String wineName;
    private String retailer;
    private String description;
    private Spinner sRetailer;
    private WineEntity wine;

    private RetailerListViewModel listViewModel;

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
//        Intent i = getIntent();
//        wine = i.getParcelableExtra("wine");



        etWineName = findViewById(R.id.wineEdit_wineName);
        etDescription = findViewById(R.id.wineEdit_description);

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

//        int selectionPosition= adapter.getPosition(retailer);
//        sRetailer.setSelection(selectionPosition);

//        sRetailer.setSelection(((ArrayAdapter<String>)sRetailer.getAdapter()).getPosition(retailer));

//        int position = getIndex(sRetailer,retailer);
//        sRetailer.setSelection(position);

//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, retailerNames);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sRetailer.setAdapter(adapter2);
//        if (retailer != null) {
//            int spinnerPosition = adapter2.getPosition(retailer);
//            sRetailer.setSelection(spinnerPosition);
//        }

        etWineName.setText(wineName);
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
}