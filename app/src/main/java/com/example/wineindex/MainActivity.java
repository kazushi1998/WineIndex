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
            "India",
            "China",
            "Nepal",
            "Bhutan"
    };

    private String capitalNames[] = {
            "Delhi",
            "Beijing",
            "Kathmandu",
            "Thimphu"
    };


    private Integer imageid[] = {
            R.drawable.india,
            R.drawable.china,
            R.drawable.nepal,
            R.drawable.bhutan

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting header
       /* TextView textView = new TextView(this);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText("Vineyards");
*/
        ListView listView=(ListView)findViewById(android.R.id.list);
        //listView.addHeaderView(textView);

        // For populating list data
        CustomCountryList customCountryList = new CustomCountryList(this, countryNames, capitalNames, imageid);
        listView.setAdapter(customCountryList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(),"You Selected "+countryNames[position]+ " as Country",Toast.LENGTH_SHORT).show();        }
        });
    }
}