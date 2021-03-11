package com.example.wineindex;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Vineyards {
    @PrimaryKey
   private int vineyardsId;

    @ColumnInfo(name = "VineyardName")
    private String name;

}
