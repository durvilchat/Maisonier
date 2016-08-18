package com.mahya.maisonier.activities.detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mahya.maisonier.R;
import com.mahya.maisonier.entites.Logement;
import com.mahya.maisonier.entites.Logement_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_LogementActivity extends AppCompatActivity {


    protected TextView Type;
    protected TextView Batiment;
    protected TextView Ref;
    protected CheckBox Etat;
    protected TextView PrixMin;
    protected TextView PrixActuel;
    protected TextView PrixMax;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_logement);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = 0;

        if (id != 0) {
            Logement logement = SQLite.select().from(Logement.class).where(Logement_Table.id.eq(id)).querySingle();

            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Ref.setText(logement.getReference());
            PrixMax.setText(String.valueOf(logement.getPrixMax()));
            PrixMin.setText(String.valueOf(logement.getPrixMin()));
            Type.setText(logement.getTypeLogement().load().getCode());
            Batiment.setText(logement.getBatiment().load().getCite().load().getNomCite());
           // Etat.setText(logement.);
           // PrixActuel.setText(String.valueOf(logement.getBatiment().load().));
        }

        initView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        Type = (TextView) findViewById(R.id.Type);
        Batiment = (TextView) findViewById(R.id.Batiment);
        Ref = (TextView) findViewById(R.id.Ref);
        Etat = (CheckBox) findViewById(R.id.Etat);
        PrixMin = (TextView) findViewById(R.id.PrixMin);
        PrixActuel = (TextView) findViewById(R.id.PrixActuel);
        PrixMax = (TextView) findViewById(R.id.PrixMax);

    }
}



