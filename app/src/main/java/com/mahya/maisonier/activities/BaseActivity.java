package com.mahya.maisonier.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mahya.maisonier.R;

public abstract class BaseActivity extends AppCompatActivity {

    // Common options
    protected int threshold = 4;
    protected int totalPages;
    protected int itemsPerPage = 10;
    protected int initItem = 1;
    protected long networkDelay = 1500;
    protected boolean addLoadingRow = true;
    protected boolean customLoadingListItem = false;
    protected Orientation orientation = Orientation.VERTICAL;

    protected abstract void setupPagination();


    public enum LayoutManagerEnum {
        LINEAR
    }


    public enum Orientation {
        VERTICAL,
        HORIZONTAL
    }


    public static class VH extends RecyclerView.ViewHolder {
        TextView tvLoading;

        public VH(View itemView) {
            super(itemView);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading_text);
        }
    }


}