package com.example.wineindex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.FloatArrayEvaluator;
import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.MenuItem;

public class AddWine extends AppCompatActivity {
    private FloatingActionButton fabAccept;
    private ImageButton buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wine);

        fabAccept = findViewById(R.id.floatingActionButton);
        fabAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVineyardInfoAccept();
            }
        });

        buttonBack = findViewById(R.id.imageButton_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVineyardInfoBack();
            }
        });
    }
    public void openVineyardInfoBack() {
        Intent intent = new Intent(this, VineyardInfo.class);
        startActivity(intent);
    }

    public void openVineyardInfoAccept(){
        Intent intent = new Intent(this, VineyardInfo.class);
        startActivity(intent);
    }

        public void openActivityFavorites () {
            Intent intent = new Intent(this, Favorites.class);
            startActivity(intent);
        }

        public void openActivitySettings () {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }

}