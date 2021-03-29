package com.example.wineindex.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wineindex.R;
import com.example.wineindex.database.entity.VineyardEntity;

import java.util.List;

// Source: https://java2blog.com/android-custom-listview-with-images-text-example/

public class VineyardList extends ArrayAdapter {
    private String[] vineyardNames;
    private String[] vineyardDescription;
    private Integer[] imageid;
    private Activity context;
    private List<VineyardEntity> vineyards;

    public VineyardList(Activity context, String[] vineyardNames, String[] vineyardDescription, Integer[] imageid) {
        super(context, R.layout.row_item, vineyardNames);
        this.context = context;
        this.vineyardNames = vineyardNames;
        this.vineyardDescription = vineyardDescription;
        this.imageid = imageid;

    }

    public void setList(List<VineyardEntity> vineyards) {
        this.vineyards = vineyards;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(R.layout.row_item, null, true);
        TextView textViewName = (TextView) row.findViewById(R.id.textViewName);
        TextView textViewDescription = (TextView) row.findViewById(R.id.textViewDescription);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.imageViewFlag);

        textViewName.setText(vineyards.get(position).getName());
        textViewDescription.setText(vineyards.get(position).getInfo());
        imageFlag.setImageResource(imageid[position]);
        return row;
    }
}