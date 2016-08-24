package com.mahya.maisonier.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.mahya.maisonier.R;
import com.mahya.maisonier.utils.content.ContentItem;

import java.util.ArrayList;

/**
 * Created by LARUMEUR on 20/08/2016.
 */

public class ImageShow extends ActionBarActivity {

    // Keep reference to the ShareActionProvider from the menu
    private ShareActionProvider mShareActionProvider;

     static String  image;
    private final ArrayList<ContentItem> mItems = getSampleContent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        image = getIntent().getStringExtra("image");
        setContentView(R.layout.image);
        setTitle(getString(R.string.photo));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + image));
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

    // BEGIN_INCLUDE(get_sap)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource
        getMenuInflater().inflate(R.menu.share, menu);
        // Retrieve the share menu item
        MenuItem shareItem = menu.findItem(R.id.menu_share);

        // Now get the ShareActionProvider from the item
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        // Get the ViewPager's current item position and set its ShareIntent.
        //int currentViewPagerItem = ((ViewPager) findViewById(R.id.viewpager)).getCurrentItem();
        setShareIntent(0);

        return super.onCreateOptionsMenu(menu);
    }

    private void setShareIntent(int position) {
        // BEGIN_INCLUDE(update_sap)
        if (mShareActionProvider != null) {
            // Get the currently selected item, and retrieve it's share intent
            ContentItem item = mItems.get(position);
            Intent shareIntent = item.getShareIntent(ImageShow.this);

            // Now update the ShareActionProvider with the new share intent
            mShareActionProvider.setShareIntent(shareIntent);
        }
        // END_INCLUDE(update_sap)
    }


    /**
     * @return An ArrayList of ContentItem's to be displayed in this sample
     */
    static ArrayList<ContentItem> getSampleContent() {
        ArrayList<ContentItem> items = new ArrayList<ContentItem>();

        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE,image));
        System.out.println(image);
        return items;
    }
}