package com.mahya.maisonier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.mahya.maisonier.R;


public class Lancement extends AppCompatActivity {

    ProgressBar loading;
    int valueProgre = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancement);
        initView();
        setProgressValue(valueProgre);

    }

    void initView() {
        loading = (ProgressBar) findViewById(R.id.loadingBar);
    }

    void setProgressValue(final int progress) {

        // set the progress
        loading.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 5);

            }
        });
        thread.start();
        if (progress == 100) {

            startActivity(new Intent(this, Login.class));
        }
    }
}
