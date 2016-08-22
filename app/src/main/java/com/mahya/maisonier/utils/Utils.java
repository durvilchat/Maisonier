package com.mahya.maisonier.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.mahya.maisonier.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.mahya.maisonier.utils.Constants.patch;

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

    public static String saveToInternalStorage(Bitmap bitmap, String filename) throws IOException {

        OutputStream output;
        filename = filename.trim();

        // Find the SD Card path
        File filepath = Environment.getExternalStorageDirectory();

        // Create a new folder in SD Card
        File dir = new File(filepath.getAbsolutePath() + "" + patch);
        dir.mkdirs();

        // Create a name for the saved image
        File file = new File(dir, "img" + filename + ".png");

        // Show a toast message on successful save
        try {

            output = new FileOutputStream(file);

            // Compress into png format image from 0% - 100%
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "img" + filename + ".png";
    }

    public static String currentDate(DatePicker datePicker) {
        StringBuilder mcurrentDate = new StringBuilder();
        int month = datePicker.getMonth() + 1;
        mcurrentDate.append(datePicker.getDayOfMonth() + "/" + month + "/" + datePicker.getYear());
        return mcurrentDate.toString();
    }


    protected void sendEmail(Activity activity) {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            activity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            //   Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            // Toast.makeText(activity.class, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void sendSMSMessage(Activity activity, String tel, String msg) {
        Log.i("Send SMS", "");

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(tel, null, msg, null, null);
            Toast.makeText(activity.getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
