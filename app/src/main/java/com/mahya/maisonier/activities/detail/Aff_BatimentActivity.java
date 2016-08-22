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
import com.mahya.maisonier.entites.Batiment;
import com.mahya.maisonier.entites.Habitant_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_BatimentActivity extends AppCompatActivity {


    protected TextView Code;
    protected TextView Nom;
    protected TextView Cite;
    protected CheckBox Etat;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_batiment);
        initView();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = getIntent().getIntExtra("id", 0);

        if (id != 0) {
            Batiment batiment = SQLite.select().from(Batiment.class).where(Habitant_Table.id.eq(id)).querySingle();


            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Code.setText(batiment.getCode());
            Nom.setText(batiment.getNom());
            Cite.setText(batiment.getCite().load().getNomCite());
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
        Code = (TextView) findViewById(R.id.Code);
        Nom = (TextView) findViewById(R.id.Nom);
        Cite = (TextView) findViewById(R.id.Cite);
        Etat = (CheckBox) findViewById(R.id.Etat);
    }
}

