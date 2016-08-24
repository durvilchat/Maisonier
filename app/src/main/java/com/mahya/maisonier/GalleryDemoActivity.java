package com.mahya.maisonier;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.mahya.maisonier.entites.Habitant;
import com.mahya.maisonier.entites.Habitant_Table;
import com.mahya.maisonier.utils.Constants;
import com.mahya.maisonier.utils.content.ContentItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class GalleryDemoActivity extends AppCompatActivity {
    // Keep reference to the ShareActionProvider from the menu
    private ShareActionProvider mShareActionProvider;

    static String image;
    private final ArrayList<ContentItem> mItems = getSampleContent();

    private ImageView selectedImageView;

    private ImageView leftArrowImageView;

    private ImageView rightArrowImageView;

    private Gallery gallery;

    private int selectedImagePosition = 0;

    private List<String> drawables;

    private GalleryImageAdapter galImageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        drawables = new ArrayList<>();
        List<Habitant> habitants = SQLite.select(Habitant_Table.photo).from(Habitant.class).queryList();
        for (Habitant habitant : habitants
                ) {
            drawables.add(habitant.getPhoto());
        }
        setupUI();
    }

    private void setupUI() {

        selectedImageView = (ImageView) findViewById(R.id.selected_imageview);
        leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
        rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);
        gallery = (Gallery) findViewById(R.id.gal);

        leftArrowImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (selectedImagePosition > 0) {
                    --selectedImagePosition;

                }

                gallery.setSelection(selectedImagePosition, false);
            }
        });

        rightArrowImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (selectedImagePosition < drawables.size() - 1) {
                    ++selectedImagePosition;

                }

                gallery.setSelection(selectedImagePosition, false);

            }
        });

        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                selectedImagePosition = pos;

                if (selectedImagePosition > 0 && selectedImagePosition < drawables.size() - 1) {

                    leftArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_enabled));
                    rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_enabled));

                } else if (selectedImagePosition == 0) {

                    leftArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_disabled));

                } else if (selectedImagePosition == drawables.size() - 1) {

                    rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_disabled));
                }

                changeBorderForSelectedImage(selectedImagePosition);
                setShareIntent(selectedImagePosition);
                setSelectedImage(selectedImagePosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        galImageAdapter = new GalleryImageAdapter(this, drawables);

        gallery.setAdapter(galImageAdapter);

        if (drawables.size() > 0) {

            gallery.setSelection(selectedImagePosition, false);

        }

        if (drawables.size() == 1) {

            rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_disabled));
        }

    }

    private void changeBorderForSelectedImage(int selectedItemPos) {

        int count = gallery.getChildCount();

        for (int i = 0; i < count; i++) {

            ImageView imageView = (ImageView) gallery.getChildAt(i);
            imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_border));
            imageView.setPadding(3, 3, 3, 3);

        }

        ImageView imageView = (ImageView) gallery.getSelectedView();
        imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_image_border));
        imageView.setPadding(3, 3, 3, 3);
    }


    private void setSelectedImage(int selectedImagePosition) {
        selectedImageView.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + drawables.get(selectedImagePosition)));

        selectedImageView.setScaleType(ScaleType.FIT_XY);

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


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.pic) {
            final String[] option = new String[]{"Camera", "Galery"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Selectionner la source");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.select_dialog_item, option);
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();


        }

        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent(int position) {
        // BEGIN_INCLUDE(update_sap)
        if (mShareActionProvider != null) {
            // Get the currently selected item, and retrieve it's share intent
            ContentItem item = mItems.get(position);
            Intent shareIntent = item.getShareIntent(GalleryDemoActivity.this);

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
        for (Habitant s : Habitant.findAll()
                ) {

            items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, s.getPhoto()));
        }
        return items;
    }


}