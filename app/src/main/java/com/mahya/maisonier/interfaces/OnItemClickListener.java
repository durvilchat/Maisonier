package com.mahya.maisonier.interfaces;

import android.view.View;

public interface OnItemClickListener {
    void onItemClicked(View view, int position);

    public void onLongClick(View view, int position);

}