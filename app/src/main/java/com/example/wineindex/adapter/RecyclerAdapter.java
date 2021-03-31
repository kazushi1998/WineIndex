package com.example.wineindex.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wineindex.R;
import com.example.wineindex.database.entity.VineyardEntity;
import com.example.wineindex.database.entity.WineEntity;
import com.example.wineindex.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<T> mData;
    private RecyclerViewItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> listener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            listener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);
        if (item.getClass().equals(VineyardEntity.class))
            holder.textView.setText(((VineyardEntity) item).getName());
        if (item.getClass().equals(WineEntity.class))
            holder.textView.setText(((WineEntity) item).getName());
    }

    @Override
    public int getItemCount() {
        if(mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if(this.mData == null) {
            this.mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof VineyardEntity) {
                        return ((VineyardEntity) mData.get(oldItemPosition)).getName().equals(((VineyardEntity) data.get(newItemPosition)).getName());
                    }
                    if (mData instanceof WineEntity) {
                        return ((WineEntity) mData.get(oldItemPosition)).getName().equals(((WineEntity) data.get(newItemPosition)).getName());
                    }

                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if(mData instanceof VineyardEntity) {
                        VineyardEntity newVineyard = (VineyardEntity) data.get(newItemPosition);
                        VineyardEntity oldVineyard = (VineyardEntity) mData.get(newItemPosition);
                        return Objects.equals(newVineyard.getName(), oldVineyard.getName());

                    }

                    if(mData instanceof WineEntity) {
                        WineEntity newWine = (WineEntity) data.get(newItemPosition);
                        WineEntity oldWine = (WineEntity) mData.get(newItemPosition);
                        return Objects.equals(newWine.getName(), oldWine.getName());

                    }
                    return false;
                }
            });
            this.mData = data;
            result.dispatchUpdatesTo(this);
        }
    }



}
