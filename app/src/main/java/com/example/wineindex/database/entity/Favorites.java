package com.example.wineindex.database.entity;

import androidx.annotation.NonNull;
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

    public int getFavId() {
        return favId;
    }

    public void setFavId(int favId) {
        this.favId = favId;
    }

    @ColumnInfo(name ="wine_Id")
    public int wine_Id;

    public Favorites(@NonNull int favId, int wine_Id) {
      this.favId = favId;
      this.wine_Id = wine_Id;
    }
}
