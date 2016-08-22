package com.mahya.maisonier.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahya.maisonier.R;

/**
 * Created by LARUMEUR on 20/08/2016.
 */

public class ImageShow extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        setTitle(getString(R.string.photo));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String bitmap = getIntent().getStringExtra("image");


        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + bitmap));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}