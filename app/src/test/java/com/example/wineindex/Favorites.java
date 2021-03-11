package com.example.wineindex;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Wine.class,
                                  parentColumns = "wineId",
                                  childColumns = "wine_Id" ))

public class Favorites {
    @PrimaryKey
    private int favId;

    @ColumnInfo(name ="wine_Id")
    public int wine_Id;
}
