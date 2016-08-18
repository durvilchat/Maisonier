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
import com.mahya.maisonier.entites.Charge;
import com.mahya.maisonier.entites.Charge_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static com.mahya.maisonier.R.string.Charges;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_ChargeActivity extends AppCompatActivity {


    protected TextView Habitant;
    protected TextView Mois;
    protected TextView Designation;
    protected TextView Montant;
    protected TextView MontantPaye;
    protected TextView Observation;
    protected TextView DataPayement;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_charges);

        initView();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = 0;

        if (id != 0) {
            Charge charge = SQLite.select().from(Charge.class).where(Charge_Table.id.eq(id)).querySingle();

            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Habitant.setText(charge.getOccupation().load().getHabitant().load().getNom());
            Mois.setText(charge.getMois().load().getMois());
            Designation.setText(charge.getDesignation());
            Montant.setText(String.valueOf(charge.getMontant()));
            MontantPaye.setText(String.valueOf(charge.getMontantPayer()));
            Observation.setText(charge.getObservation());
            DataPayement.setText(sdf.format(charge.getDatePaiement()));

        }


    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        Habitant = (TextView) findViewById(R.id.Habitant);
        Mois = (TextView) findViewById(R.id.Mois);
        Designation = (TextView) findViewById(R.id.Designation);
        Montant = (TextView) findViewById(R.id.Montant);
        MontantPaye = (TextView) findViewById(R.id.MontantPaye);
        Observation = (TextView) findViewById(R.id.Observation);
        DataPayement = (TextView) findViewById(R.id.DataPayement);
    }
}

