package com.mahya.maisonier.activities.detail;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.model.BailleurAdapter;
import com.mahya.maisonier.adapter.model.HabitantAdapter;
import com.mahya.maisonier.entites.Bailleur;
import com.mahya.maisonier.entites.Bailleur_Table;
import com.mahya.maisonier.entites.Habitant;
import com.mahya.maisonier.entites.Habitant_Table;
import com.mahya.maisonier.utils.Constants;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_HabitantActivity extends AppCompatActivity {


    protected ImageView imgHabitant;
    protected TextView Nom;
    protected TextView Prenom;
    protected TextView NumeroCNI;
    protected TextView Genre;
    protected TextView Titre;
    protected TextView Emails;
    protected TextView Telephone;
    protected TextView DateNaissance;
    protected TextView LieuNaissance;
    protected TextView NomPere;
    protected TextView NomMere;
    protected TextView Profession;
    private ImageView imgBailleur;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_habitant);
        initView();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = 0;

        if (id != 0) {
            Habitant habitant = SQLite.select().from(Habitant.class).where(Habitant_Table.id.eq(id)).querySingle();

            if (habitant.getPhoto() != null) {

                imgHabitant.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + "" + habitant.getPhoto()));
            }
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            Nom.setText(habitant.getNom());
            Prenom.setText(habitant.getPrenom());
            NumeroCNI.setText(habitant.getNumeroCNI());
            Genre.setText(habitant.getGenre());
            Titre.setText(habitant.getTitre());
            Emails.setText(habitant.getEmail1());
            Telephone.setText(habitant.getTel1());
            DateNaissance.setText(sdf.format(habitant.getDateNaissance()));
            LieuNaissance.setText(habitant.getLieuNaissance());
            NomPere.setText(habitant.getNomDuPere());
            NomMere.setText(habitant.getNomDeLaMere());
            Profession.setText(habitant.getProfession());
        }

        setTitle(this.getString(R.string.detail) + " " + Nom.getText().toString());
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
        imgHabitant = (ImageView) findViewById(R.id.imageView4);
        Nom = (TextView) findViewById(R.id.Nom);
        Prenom = (TextView) findViewById(R.id.Prenom);
        NumeroCNI = (TextView) findViewById(R.id.NumeroCNI);
        Genre = (TextView) findViewById(R.id.Genre);
        Titre = (TextView) findViewById(R.id.Titre);
        Emails = (TextView) findViewById(R.id.Emails);
        Telephone = (TextView) findViewById(R.id.Telephone);
        DateNaissance = (TextView) findViewById(R.id.DateNaissance);
        LieuNaissance = (TextView) findViewById(R.id.LieuNaissance);
        NomPere = (TextView) findViewById(R.id.NomPere);
        NomMere = (TextView) findViewById(R.id.NomMere);
        Profession = (TextView) findViewById(R.id.Profession);
    }
}
