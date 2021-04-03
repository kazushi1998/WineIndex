package com.example.wineindex.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class WineEntity implements Serializable {

    private String name;

    private String vineyard;

    private String retailer;

    private String description;

    @Exclude
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVineyard() {
        return vineyard;
    }

    public void setVineyard(String vineyard) {
        this.vineyard = vineyard;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public WineEntity(@NonNull String name, String vineyard, String retailer, String description) {
        this.name=name;
        this.vineyard=vineyard;
        this.retailer=retailer;
        this.description=description;
    }

    public WineEntity(){

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("vineyard", vineyard);
        result.put("description",description);
        result.put("retailer",retailer);
        return result;
    }
}
