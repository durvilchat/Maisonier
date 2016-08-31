package com.mahya.maisonier;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.mahya.maisonier.activities.BaseActivity;
import com.mahya.maisonier.adapter.GalleryImageAdapter;
import com.mahya.maisonier.entites.Batiment;
import com.mahya.maisonier.entites.Batiment_Table;
import com.mahya.maisonier.entites.Logement;
import com.mahya.maisonier.entites.Logement_Table;
import com.mahya.maisonier.entites.PhotoBatiment;
import com.mahya.maisonier.entites.PhotoLogement;
import com.mahya.maisonier.utils.Constants;
import com.mahya.maisonier.utils.Utils;
import com.mahya.maisonier.utils.content.ContentItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GalleryDemoActivity extends BaseActivity {
    static String image;
    static int type;
    private static List<String> drawables = new ArrayList<>();
    int id;
    String nom;
    // Keep reference to the ShareActionProvider from the menu
    private ShareActionProvider mShareActionProvider;
    private ArrayList<ContentItem> mItems = null;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ImageView selectedImageView;
    private ImageView leftArrowImageView;
    private ImageView rightArrowImageView;
    private Gallery gallery;
    private String userChoosenTask;
    private int selectedImagePosition = 0;
    private GalleryImageAdapter galImageAdapter;

    /**
     * @return An ArrayList of ContentItem's to be displayed in this sample
     */
    static ArrayList<ContentItem> getSampleContent() {
        ArrayList<ContentItem> items = new ArrayList<ContentItem>();
        for (String s : drawables
                ) {

            items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, s, type));
        }
        return items;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_main);
        type = getIntent().getIntExtra("type", 0);
        nom = getIntent().getStringExtra("nom");
        id = getIntent().getIntExtra("id", 0);

        if (type == 1) {
            drawables.clear();

            for (PhotoBatiment photoBatiment : SQLite.select().from(Batiment.class).where(Batiment_Table.id.eq(id)).querySingle().getPhotoBatimentList()) {
                drawables.add(photoBatiment.getNom());
                mItems = getSampleContent();
            }
        } else if (type == 2) {
            drawables.clear();
            for (PhotoLogement photoBatiment : SQLite.select().from(Logement.class).where(Logement_Table.id.eq(id)).querySingle().getPhotoLogementList()) {
                drawables.add(photoBatiment.getNom());
                mItems = getSampleContent();
            }
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        if (!drawables.isEmpty()){

            setupUI();
        }
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

        galImageAdapter = new GalleryImageAdapter(this, drawables,type);

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
        if(type==1){

            selectedImageView.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch +"batiments/"+ drawables.get(selectedImagePosition)));

        }else {

            selectedImageView.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch +"logements/"+ drawables.get(selectedImagePosition)));

        }
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
          film(id);
        }


         return super.onOptionsItemSelected(item);
    }

    public  void film(int id){
        if (id==0)
            this.id=id;
        final CharSequence[] items = {"Camera", "Galerie",
                "Annuler"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(GalleryDemoActivity.this);
        builder.setTitle("Source de l'image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utils.checkPermission(GalleryDemoActivity.this);

                if (items[item].equals("Camera")) {
                    userChoosenTask = "Camera";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Galerie")) {
                    userChoosenTask = "Galerie";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void onCaptureImageResult(Intent data) {


        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
        File destination;
        if (type==1){

             destination = new File(getFolderBat(nom) + "/"+nom + System.currentTimeMillis() + ".jpg");
            PhotoBatiment p =new PhotoBatiment();
            p.setNom(nom+"/"+nom+ System.currentTimeMillis() + ".jpg");
            p.assoBatiment(SQLite.select().from(Batiment.class).where(Batiment_Table.id.eq(id)).querySingle());
            p.save();

        }else {

            destination = new File(getFolderLog(nom) + "/"+nom + System.currentTimeMillis() + ".jpg");
            PhotoLogement log=new PhotoLogement();
            log.assoLogement(SQLite.select().from(Logement.class).where(Logement_Table.id.eq(id)).querySingle());
            log.setNom(nom+"/"+nom+System.currentTimeMillis() + ".jpg");
            log.save();
        }

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //  ivImage.setImageBitmap(bm);
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


}