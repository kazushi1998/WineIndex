package com.example.wineindex.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

@Entity
public class VineyardEntity {
    @PrimaryKey
    private int vineyardsId;

    public int getVineyardsId() {
        return vineyardsId;
    }

    public void setVineyardsId(int vineyardsId) {
        this.vineyardsId = vineyardsId;
    }

    @Exclude
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

    public VineyardEntity(@NonNull int vineyardsId, String name, String info) {
        this.vineyardsId = vineyardsId;
        this.name = name;
        this.info = info;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof VineyardEntity)) return false;
        VineyardEntity o = (VineyardEntity) obj;
        return o.getName().equals(this.getName());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("info", info);
        return result;
    }
}
