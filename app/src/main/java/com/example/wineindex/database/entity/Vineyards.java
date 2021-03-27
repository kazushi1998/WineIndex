package com.example.wineindex.database.entity;

import androidx.annotation.NonNull;
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

    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vineyards(@NonNull int vineyardsId, String name, String info) {
        this.vineyardsId = vineyardsId;
        this.name = name;
        this.info = info;
    }
}
