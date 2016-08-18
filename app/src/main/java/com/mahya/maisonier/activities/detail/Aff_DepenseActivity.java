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
import com.mahya.maisonier.entites.Depense;
import com.mahya.maisonier.entites.Depense_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_DepenseActivity extends AppCompatActivity {


    protected TextView Cite;
    protected TextView Batiment;
    protected TextView Mois;
    protected TextView Bailleur;
    protected TextView Montant;
    protected TextView Description;
    protected TextView Designation;
    protected TextView DateEnregistrement;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_depense);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = 0;

        if (id != 0) {
            Depense depense = SQLite.select().from(Depense.class).where(Depense_Table.id.eq(id)).querySingle();

            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Cite.setText(depense.getBatiment().load().getCite().load().getNomCite());
            Batiment.setText(depense.getBatiment().load().getCite().load().getNomCite());
            Mois.setText(depense.getMois().load().getMois());
            Bailleur.setText(depense.getBailleur().load().getNom());
            Montant.setText(String.valueOf(depense.getMontant()));
            Description.setText(depense.getDescription());
            Designation.setText(depense.getDesignation());
            DateEnregistrement.setText(sdf.format(depense.getDateEnregistrement()));
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
        Cite = (TextView) findViewById(R.id.Cite);
        Batiment = (TextView) findViewById(R.id.Batiment);
        Mois = (TextView) findViewById(R.id.Mois);
        Bailleur = (TextView) findViewById(R.id.Bailleur);
        Montant = (TextView) findViewById(R.id.Montant);
        Description = (TextView) findViewById(R.id.Description);
        Designation = (TextView) findViewById(R.id.Designation);
        DateEnregistrement = (TextView) findViewById(R.id.DateEnregistrement);
    }
}



