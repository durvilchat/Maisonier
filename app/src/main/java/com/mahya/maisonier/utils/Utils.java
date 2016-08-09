package com.mahya.maisonier.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.mahya.maisonier.R;

/**
 * Created by Suleiman on 30-04-2015.
 */
public class Utils {

    static final String PREFERENCES_FILE = "maisonier_settings";
    //keep track of camera capture intent
    static final int CAMERA_CAPTURE = 1;

/*
    public static int getStatusBarHeight(Context context) {
        int height = (int) context.getResources().getDimension(R.dimen.statusbar_size);
        return height;
    }
*/
//keep track of cropping intent
final int PIC_CROP = 3;
    //keep track of gallery intent
    final int PICK_IMAGE_REQUEST = 2;
    //captured picture uri
    private Uri picUri;

    public static int getToolbarHeight(Context context) {
        int height = (int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        return height;
    }

    public static Drawable tintMyDrawable(Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }
/*
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                try {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
                    File imageFile = new File(imageFilePath);
                    picUri = Uri.fromFile(imageFile); // convert path to Uri
                    takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT,  picUri );
                    startActivityForResult(takePictureIntent, CAMERA_CAPTURE);

                } catch(ActivityNotFoundException anfe){
                    //display an error message
                    String errorMessage = "Whoops - your device doesn't support capturing images!";
                    Toast.makeText(Test.this, errorMessage, Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.gallery:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
                break;

            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //user is returning from capturing an image using the camera
            if(requestCode == CAMERA_CAPTURE){
                //get the Uri for the captured image
                Uri uri = picUri;
                //carry out the crop operation
                performCrop();
                Log.d("picUri", uri.toString());

            }

            else if(requestCode == PICK_IMAGE_REQUEST){
                picUri = data.getData();
                Log.d("uriGallery", picUri.toString());
                performCrop();
            }

            //user is returning from cropping the image
            else if(requestCode == PIC_CROP){
                //get the returned data
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                Bitmap thePic = (Bitmap) extras.get("data");
                //display the returned cropped image
                imageView.setImageBitmap(thePic);
            }

        }
    }*/
}
