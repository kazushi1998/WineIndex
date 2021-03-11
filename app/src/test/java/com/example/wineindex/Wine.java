package com.example.wineindex;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Wine {
    @PrimaryKey
    private int wineId;

    @ColumnInfo(name = "WineName")
    private String name;

    @ColumnInfo(name = "Winery")
    private String winery;
}
