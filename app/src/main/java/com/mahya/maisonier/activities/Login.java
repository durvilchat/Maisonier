package com.mahya.maisonier.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mahya.maisonier.R;
import com.mahya.maisonier.utils.Utils;

public class Login extends AppCompatActivity implements View.OnClickListener {
    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    public ProgressDialog progress;
    protected EditText login;
    protected EditText mdp;
    protected Button btnCnx;
    boolean isUserFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(Login.this, PREF_USER_FIRST_TIME, "true"));

        Intent introIntent = new Intent(Login.this, Presentation.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);

        if (isUserFirstTime)
            startActivity(introIntent);
        super.setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_cnx) {
            if (login.getText().toString().trim().isEmpty()) {
                login.setError("Erreur");
                return;
            }


            if (mdp.getText().toString().trim().isEmpty()) {
                mdp.setError("Erreur");
                return;
            }


            new Connexion().execute();

        }
    }

    void initView() {
        login = (EditText) findViewById(R.id.login);
        mdp = (EditText) findViewById(R.id.mdp);
        btnCnx = (Button) findViewById(R.id.btn_cnx);
        btnCnx.setOnClickListener(Login.this);
    }


    private class Connexion extends AsyncTask {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(Login.this, "Authentification",
                    "Veillez patienter", true);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }).start();

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (login.getText().toString().trim().equals("demo") && mdp.getText().toString().trim().equals("demo")) {

                startActivity(new Intent(Login.this, MainActivity.class));
            } else {
                AlertDialog error = new AlertDialog.Builder(Login.this)
                        .setMessage("Mot de passe ou Login incorrect")
                        .setIcon(R.drawable.ic_error)
                        .setTitle("Erreur")
                        .setNeutralButton("OK", null)
                        .setCancelable(false)
                        .create();
                error.show();
            }
            progress.dismiss();

        }

        @Override
        protected void onCancelled(Object o) {
            super.onCancelled(o);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            progress.dismiss();

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            return null;
        }
    }
}
