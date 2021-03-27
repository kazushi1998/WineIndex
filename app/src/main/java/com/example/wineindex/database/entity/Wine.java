package com.example.wineindex.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Wine {
    @PrimaryKey
    private int wineId;

    @ColumnInfo(name = "WineName")
    private String name;

    public int getWineId() {
        return wineId;
    }

    public void setWineId(int wineId) {
        this.wineId = wineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWinery() {
        return winery;
    }

    public void setWinery(String winery) {
        this.winery = winery;
    }

    @ColumnInfo(name = "Winery")
    private String winery;

    public Wine(@NonNull int wineId, String name, String winery) {
        this.wineId=wineId;
        this.name=name;
        this.winery=winery;

    }
}
