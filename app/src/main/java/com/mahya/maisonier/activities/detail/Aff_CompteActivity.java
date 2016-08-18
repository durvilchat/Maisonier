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
import com.mahya.maisonier.entites.Compte;
import com.mahya.maisonier.entites.Compte_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_CompteActivity extends AppCompatActivity {


    protected TextView TypeCompte;
    protected TextView Occupation;
    protected TextView Solde;
    protected TextView Date;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_compte);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = 0;

        if (id != 0) {
            Compte compte = SQLite.select().from(Compte.class).where(Compte_Table.id.eq(id)).querySingle();

            TypeCompte.setText(compte.getTypeCompte().load().getLibelle());
            Occupation.setText(compte.getOccupation().load().getHabitant().load().getNom());
            Solde.setText(String.valueOf(compte.getSolde()));
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
        TypeCompte = (TextView) findViewById(R.id.TypeCompte);
        Occupation = (TextView) findViewById(R.id.Occupation);
        Solde = (TextView) findViewById(R.id.Solde);
        Date = (TextView) findViewById(R.id.Date);
    }
}


