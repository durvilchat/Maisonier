package com.mahya.maisonier.activities;

import android.Manifest;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.mahya.maisonier.utils.Constants;

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
        file = new File(filepath+"/"+Constants.patch, FILE_FOLDER);
        if (!file.exists()) {
            file.mkdirs();
        }

    }
    public String getFolderBat(String folder) {

        String bat="batiments";
        File bati = new File(filepath+"/"+Constants.patch,bat );


        if (!bati.exists()) {
            bati.mkdirs();
        }

       File file = new File(bati.getPath(), folder);

        if (!file.exists()) {
            file.mkdirs();
        }

        return file.getPath();
    }

    public String getFolderLog(String folder) {

        String log="logements";
        File bati = new File(file.getPath(),log );


        if (!bati.exists()) {
            bati.mkdirs();
        }

        file = new File(bati.getPath(), folder);

        if (!file.exists()) {
            file.mkdirs();
        }

        return file.getPath();
    }


}