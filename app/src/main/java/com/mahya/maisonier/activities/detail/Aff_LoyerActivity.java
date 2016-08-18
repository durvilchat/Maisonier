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
import com.mahya.maisonier.entites.Loyer;
import com.mahya.maisonier.entites.Loyer_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_LoyerActivity extends AppCompatActivity {


    protected TextView Occupation;
    protected TextView Mois;
    protected TextView MontantPaye;
    protected TextView DatePayement;
    protected TextView Observations;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_loyer);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = 0;

        if (id != 0) {
            Loyer loyer = SQLite.select().from(Loyer.class).where(Loyer_Table.id.eq(id)).querySingle();

            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Occupation.setText(loyer.getOccupation().load().getHabitant().load().getNom());
            Mois.setText(loyer.getMois().load().getMois());
            MontantPaye.setText(String.valueOf(loyer.getMontantPayer()));
            DatePayement.setText(sdf.format(loyer.getDatePaiement()));
            Observations.setText(loyer.getObservation());

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
        Occupation = (TextView) findViewById(R.id.Occupation);
        Mois = (TextView) findViewById(R.id.Mois);
        MontantPaye = (TextView) findViewById(R.id.MontantPaye);
        DatePayement = (TextView) findViewById(R.id.DatePayement);
        Observations = (TextView) findViewById(R.id.Observations);

    }
}



