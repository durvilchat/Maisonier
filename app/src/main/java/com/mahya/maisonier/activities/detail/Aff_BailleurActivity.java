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
import com.mahya.maisonier.entites.Bailleur;
import com.mahya.maisonier.entites.Bailleur_Table;
import com.mahya.maisonier.utils.Constants;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_BailleurActivity extends AppCompatActivity {


    private ImageView imgBailleur;
    private TextView Nom;
    private TextView Prenom;
    private TextView NumeroCNI;
    private TextView DateDelivranceCN;
    private TextView LieuDelivranceCNI;
    private TextView Genre;
    private TextView Titre;
    private TextView Email1;
    private TextView Email2;
    private TextView Tel1;
    private TextView Tel2;
    private TextView Tel3;
    private TextView Tel4;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_bailleur);

        initView();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("idBailleur");
        System.out.println(id);
        if (id != BailleurAdapter.idSelect) {
            Bailleur bailleur = SQLite.select().from(Bailleur.class).where(Bailleur_Table.id.eq(BailleurAdapter.idSelect)).querySingle();

            if (bailleur.getPhoto() != null) {

                imgBailleur.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + "" + bailleur.getPhoto()));
            }
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Nom.setText(bailleur.getNom());
            Prenom.setText(bailleur.getPrenom());
            NumeroCNI.setText(bailleur.getNumeroCNI());
            DateDelivranceCN.setText(sdf.format(bailleur.getDateDelivraisonCni()));
            LieuDelivranceCNI.setText(bailleur.getLieuDelivraisonCni());
            Genre.setText(bailleur.getGenre());
            Titre.setText(bailleur.getTitre());
            Email1.setText(bailleur.getEmail1());
            Email2.setText(bailleur.getEmail2());
            Tel1.setText(bailleur.getTel1());
            Tel2.setText(bailleur.getTel2());
            Tel3.setText(bailleur.getTel3());
            Tel4.setText(bailleur.getTel4());

        }

        setTitle(this.getString(R.string.detail) + " " + Nom.getText().toString());
    }

    private void initView() {
        imgBailleur = (ImageView) findViewById(R.id.imgBailleur);
        Nom = (TextView) findViewById(R.id.Nom);
        Prenom = (TextView) findViewById(R.id.Prenom);
        NumeroCNI = (TextView) findViewById(R.id.NumeroCNI);
        DateDelivranceCN = (TextView) findViewById(R.id.DateDelivranceCN);
        LieuDelivranceCNI = (TextView) findViewById(R.id.LieuDelivranceCNI);
        Genre = (TextView) findViewById(R.id.Genre);
        Titre = (TextView) findViewById(R.id.Titre);
        Email1 = (TextView) findViewById(R.id.Email1);
        Email2 = (TextView) findViewById(R.id.Email2);
        Tel1 = (TextView) findViewById(R.id.Tel1);
        Tel2 = (TextView) findViewById(R.id.Tel2);
        Tel3 = (TextView) findViewById(R.id.Tel3);
        Tel4 = (TextView) findViewById(R.id.Tel4);
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

}
