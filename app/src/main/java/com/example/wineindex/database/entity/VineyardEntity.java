package com.example.wineindex.database.entity;

import androidx.room.Entity;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

@Entity
public class VineyardEntity {
    private String name;

    private String info;

    public VineyardEntity(){
    }

    @Exclude
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
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
