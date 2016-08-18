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
import com.mahya.maisonier.entites.Cable;
import com.mahya.maisonier.entites.Cable_Table;
import com.mahya.maisonier.entites.Habitant_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_Gerer_cableActivity extends AppCompatActivity {


    protected TextView Habitant;
    protected TextView Parametres;
    protected TextView Mois;
    protected TextView MontantPaye;
    protected TextView DatePayement;
    protected TextView Observations;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_gerer_cable);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = 0;

        if (id != 0) {
            Cable gerer_cable  = SQLite.select().from(Cable.class).where(Cable_Table.id.eq(id)).querySingle();

            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Habitant.setText(gerer_cable.getOccupation().load().getHabitant().load().getNom());
            Parametres.setText(gerer_cable.getParametre().load().getId());
            Mois.setText(gerer_cable.getMois().load().getMois());
            MontantPaye.setText(String.valueOf(gerer_cable.getDatePaiement()));
            DatePayement.setText(sdf.format(gerer_cable.getDatePaiement()));
            Observations.setText(gerer_cable.getObservation());
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
        Parametres = (TextView) findViewById(R.id.Parametres);
        Mois = (TextView) findViewById(R.id.Mois);
        MontantPaye = (TextView) findViewById(R.id.MontantPaye);
        DatePayement = (TextView) findViewById(R.id.DatePayement);
        Observations = (TextView) findViewById(R.id.Observations);

    }
}



