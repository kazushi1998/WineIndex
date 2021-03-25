package com.example.wineindex.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Vineyards {
    @PrimaryKey
   private int vineyardsId;

    public int getVineyardsId() {
        return vineyardsId;
    }

    public void setVineyardsId(int vineyardsId) {
        this.vineyardsId = vineyardsId;
    }

    @ColumnInfo(name = "VineyardName")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
