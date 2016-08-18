package com.mahya.maisonier.activities.detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mahya.maisonier.R;
import com.mahya.maisonier.entites.ContratBail;
import com.mahya.maisonier.entites.ContratBail_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_ContratBailActivity extends AppCompatActivity {


    protected TextView DateEtablissement;
    protected TextView Bailleur;
    protected TextView Habitant;
    protected TextView Logement;
    protected TextView Observations;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_contrat_de_bail);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = 0;

        if (id != 0) {
            ContratBail contrat_de_bail = SQLite.select().from(ContratBail.class).where(ContratBail_Table.id.eq(id)).querySingle();

            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            DateEtablissement.setText(sdf.format(contrat_de_bail.getDateEtablissement()));
            Bailleur.setText(contrat_de_bail.getBailleur().load().getNom());
            Habitant.setText(contrat_de_bail.getOccupation().load().getHabitant().load().getNom());
            Logement.setText(contrat_de_bail.getOccupation().load().getHabitant().load().getNom());
          //  Observations.setText(contrat_de_bail.getOccupation().load().);
        }

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
        DateEtablissement = (TextView) findViewById(R.id.DateEtablissement);
        Bailleur = (TextView) findViewById(R.id.Bailleur);
        Habitant = (TextView) findViewById(R.id.Habitant);
        Logement = (TextView) findViewById(R.id.Logement);
        Observations = (TextView) findViewById(R.id.Observations);
    }
}


