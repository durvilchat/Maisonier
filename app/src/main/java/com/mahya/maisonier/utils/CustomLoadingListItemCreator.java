package com.mahya.maisonier.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahya.maisonier.R;
import com.mahya.maisonier.activities.TypelogementActivity;
import com.paginate.recycler.LoadingListItemCreator;

/**
 * Created by LARUMEUR on 31/07/2016.
 */

public class CustomLoadingListItemCreator implements LoadingListItemCreator {

    RecyclerView  recyclerView;
    public CustomLoadingListItemCreator(RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_loading_list_item, parent, false);
        return new TypelogementActivity.VH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TypelogementActivity.VH vh = (TypelogementActivity.VH) holder;
       // vh.tvLoading.setText(String.format("Total items loaded: %d.\nLoading more...", adapter.getItemCount()));

        // This is how you can make full span if you are using StaggeredGridLayoutManager
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) vh.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }
}
