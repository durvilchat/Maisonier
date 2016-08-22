package com.mahya.maisonier.activities.detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahya.maisonier.R;
import com.mahya.maisonier.entites.Occupation;
import com.mahya.maisonier.entites.Occupation_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_OccupationsActivity extends AppCompatActivity {


    protected ImageView imgOccupation;
    protected TextView Habitant;
    protected TextView Logement;
    protected TextView LoyerDeBase;
    protected TextView ModeDePayement;
    protected CheckBox Etat;
    protected CheckBox PaieEau;
    protected CheckBox PaieElectricite;
    protected CheckBox PaieCable;
    protected TextView DateEntree;
    protected TextView DateSortie;
    protected TextView Description;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_occcupations);

        initView();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = getIntent().getIntExtra("id", 0);

        if (id != 0) {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Occupation occupation = SQLite.select().from(Occupation.class).where(Occupation_Table.id.eq(id)).querySingle();
            Habitant.setText(occupation.getHabitant().load().getNom());
            Logement.setText(occupation.getLogement().load().getReference());
            LoyerDeBase.setText(String.valueOf(occupation.getLoyerBase()));
            // Etat.setText(occupation.getE);
            ModeDePayement.setText(String.valueOf(occupation.getModePaiement()));
            PaieEau.setText((occupation.isPaieEau()) ? "oui" : "non");
            PaieElectricite.setText((occupation.isPaieElectricite()) ? "oui" : "non");
            PaieCable.setText((occupation.isPaieCable()) ? "oui" : "non");
            DateEntree.setText(sdf.format(occupation.getDateEntree()));
            DateSortie.setText(sdf.format(occupation.getDateSortie()));
            Description.setText(occupation.getDescription());
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
        imgOccupation = (ImageView) findViewById(R.id.imageView4);
        Habitant = (TextView) findViewById(R.id.Habitant);
        Logement = (TextView) findViewById(R.id.Logement);
        LoyerDeBase = (TextView) findViewById(R.id.LoyerDeBase);
        ModeDePayement = (TextView) findViewById(R.id.ModeDePayement);
        Etat = (CheckBox) findViewById(R.id.Etat);
        PaieEau = (CheckBox) findViewById(R.id.PaieEau);
        PaieElectricite = (CheckBox) findViewById(R.id.PaieElectricite);
        PaieCable = (CheckBox) findViewById(R.id.PaieCable);
        DateEntree = (TextView) findViewById(R.id.DateEntree);
        DateSortie = (TextView) findViewById(R.id.DateSortie);
        Description = (TextView) findViewById(R.id.Description);


    }
}



