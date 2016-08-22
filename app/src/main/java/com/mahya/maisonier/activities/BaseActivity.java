package com.mahya.maisonier.activities;

import android.Manifest;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String filepath = Environment.getExternalStorageDirectory().getPath();
    private static final String FILE_FOLDER = "Maisonier";
    public static File file;
    // Common options
    public int REQUEST_CODE_ASK_PERMISSIONS = 123;
    public boolean isPDFFromHTML = false;
    String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private int mPreviousVisibleItem;

    public void getFile() {
        file = new File(filepath, FILE_FOLDER);
        if (!file.exists()) {
            file.mkdirs();
        }

    }


}