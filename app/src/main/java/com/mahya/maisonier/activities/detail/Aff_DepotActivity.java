package com.mahya.maisonier.activities.detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.mahya.maisonier.R;
import com.mahya.maisonier.entites.Depot;
import com.mahya.maisonier.entites.Depot_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_DepotActivity extends AppCompatActivity {


    protected TextView Habitant;
    protected TextView Occupation;
    protected TextView Mois;
    protected TextView Montant;
    protected TextView DateDepot;
    protected TextView Observations;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_depot);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = 0;

        if (id != 0) {
            Depot depot = SQLite.select().from(Depot.class).where(Depot_Table.id.eq(id)).querySingle();

            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Habitant.setText(depot.getOccupation().load().getHabitant().load().getNom());
            Occupation.setText(depot.getOccupation().load().getHabitant().load().getNom());
            Montant.setText(String.valueOf(depot.getMontant()));
            DateDepot.setText(sdf.format(depot.getDateDepot()));
            Observations.setText(depot.getObservation());
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
        Habitant = (TextView) findViewById(R.id.Habitant);
        Occupation = (TextView) findViewById(R.id.Occupation);
        Mois = (TextView) findViewById(R.id.Mois);
        Montant = (TextView) findViewById(R.id.Montant);
        DateDepot = (TextView) findViewById(R.id.DateDepot);
        Observations = (TextView) findViewById(R.id.Observations);

    }
}



