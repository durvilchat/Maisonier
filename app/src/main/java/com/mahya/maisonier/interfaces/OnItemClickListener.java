package com.mahya.maisonier.interfaces;

import android.view.View;

public interface OnItemClickListener {
    void onItemClicked(int position);

    boolean onItemLongClicked(int position);

    void onLongClick(View view, int position);
}