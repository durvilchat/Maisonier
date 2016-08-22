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
import com.mahya.maisonier.entites.Cite;
import com.mahya.maisonier.entites.Cite_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_CiteActivity extends AppCompatActivity {


    protected TextView Bailleur;
    protected TextView NomCite;
    protected TextView Description;
    protected TextView Tel;
    protected TextView Email;
    protected TextView PoliceCite;
    protected TextView PoliceDescription;
    protected TextView PoliceContacts;
    protected CheckBox Etat;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_cite);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = getIntent().getIntExtra("id", 0);

        if (id != 0) {
            Cite cite = SQLite.select().from(Cite.class).where(Cite_Table.id.eq(id)).querySingle();

            Bailleur.setText(cite.getBailleur().load().getNom());
            NomCite.setText(cite.getNomCite());
            Description.setText(cite.getDescription());
            Tel.setText(cite.getTels());
            Email.setText(cite.getTels());
            PoliceCite.setText(String.valueOf(cite.getPoliceCite()));
            PoliceContacts.setText(String.valueOf(cite.getPoliceContact()));
            PoliceDescription.setText(String.valueOf(cite.getPoliceDescription()));
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
        Bailleur = (TextView) findViewById(R.id.Bailleur);
        NomCite = (TextView) findViewById(R.id.NomCite);
        Description = (TextView) findViewById(R.id.Description);
        Tel = (TextView) findViewById(R.id.Tel);
        Email = (TextView) findViewById(R.id.Email);
        PoliceCite = (TextView) findViewById(R.id.PoliceCite);
        PoliceDescription = (TextView) findViewById(R.id.PoliceDescription);
        PoliceContacts = (TextView) findViewById(R.id.PoliceContacts);
        Etat = (CheckBox) findViewById(R.id.Etat);
    }
}