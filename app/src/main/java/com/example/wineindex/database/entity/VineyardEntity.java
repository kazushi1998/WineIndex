package com.example.wineindex.database.entity;

import androidx.room.Entity;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

@Entity
public class VineyardEntity {
    private String name;

    private String description;

    public VineyardEntity(){
    }

    @Exclude
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        result.put("description", description);
        return result;
    }
}
