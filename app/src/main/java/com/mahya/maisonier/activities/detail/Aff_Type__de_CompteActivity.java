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
import com.mahya.maisonier.entites.TypeCompte;
import com.mahya.maisonier.entites.TypeCompte_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_Type__de_CompteActivity extends AppCompatActivity {


    protected TextView Libelle;
    protected TextView Description;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_type_de_compte);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = 0;

        if (id != 0) {
            TypeCompte type_de_compte = SQLite.select().from(TypeCompte.class).where(TypeCompte_Table.id.eq(id)).querySingle();
            Libelle.setText(type_de_compte.getLibelle());
            Description.setText(type_de_compte.getDescription());
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
        Libelle = (TextView) findViewById(R.id.Libelle);
        Description = (TextView) findViewById(R.id.Description);

    }
}



