package com.mahya.maisonier.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mahya.maisonier.entites.Parametre;
import com.mahya.maisonier.entites.Parametre_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ParametrageActivity extends AppCompatActivity implements View.OnClickListener {

    protected EditText entretientEau;
    protected EditText prixUEau;
    protected EditText entretientElect;
    protected EditText prixUElect;
    protected EditText cable;
    protected EditText tva;
    protected CheckBox casse;
    protected CheckBox majuscule;
    protected CheckBox etat;
    protected Button valider;
    protected Button annuler;
    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(com.mahya.maisonier.R.layout.parametrage);
        setTitle(com.mahya.maisonier.R.string.parametrage);
        initView();
        Parametre p = SQLite.select().from(Parametre.class).orderBy(Parametre_Table.id, false).limit(1).querySingle();
        if (p != null) {

            entretientEau.setText(String.valueOf(p.getEntretientEau()));
            entretientElect.setText(String.valueOf(p.getEntretientElectricite()));
            prixUEau.setText(String.valueOf(p.getPrixUnitaireEau()));
            prixUElect.setText(String.valueOf(p.getPrixUnitaireElectricite()));
            tva.setText(String.valueOf(p.getTVA()));
            cable.setText(String.valueOf(p.getCablage()));
            etat.setChecked(p.isEtat());

        } else {
            entretientEau.setText("0.0");
            entretientElect.setText("0.0");
            prixUEau.setText("0.0");
            prixUElect.setText("0.0");
            tva.setText("0.0");
            cable.setText("0.0");
        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == com.mahya.maisonier.R.id.valider) {

            Parametre parametre = new Parametre();
            try {
                parametre.setDateEnregistrement(sdf.parse(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            parametre.setCablage(Double.parseDouble(cable.getText().toString().trim()));
            parametre.setEtat(etat.isChecked());
            parametre.setPrixUnitaireEau(Double.parseDouble(prixUEau.getText().toString().trim()));
            parametre.setEntretientEau(Double.parseDouble(entretientEau.getText().toString().trim()));
            parametre.setEntretientElectricite(Double.parseDouble(entretientElect.getText().toString().trim()));
            parametre.setPrixUnitaireElectricite(Double.parseDouble(prixUElect.getText().toString().trim()));
            parametre.setTVA(Double.parseDouble(tva.getText().toString().trim()));
            parametre.save();

        } else if (view.getId() == com.mahya.maisonier.R.id.annuler) {

        }
    }

    private void initView() {
        entretientEau = (EditText) findViewById(com.mahya.maisonier.R.id.entretientEau);
        prixUEau = (EditText) findViewById(com.mahya.maisonier.R.id.prixUEau);
        entretientElect = (EditText) findViewById(com.mahya.maisonier.R.id.entretientElect);
        prixUElect = (EditText) findViewById(com.mahya.maisonier.R.id.prixUElect);
        cable = (EditText) findViewById(com.mahya.maisonier.R.id.cable);
        tva = (EditText) findViewById(com.mahya.maisonier.R.id.tva);
        casse = (CheckBox) findViewById(com.mahya.maisonier.R.id.casse);
        majuscule = (CheckBox) findViewById(com.mahya.maisonier.R.id.majuscule);
        etat = (CheckBox) findViewById(com.mahya.maisonier.R.id.etat);
        valider = (Button) findViewById(com.mahya.maisonier.R.id.valider);
        valider.setOnClickListener(ParametrageActivity.this);
        annuler = (Button) findViewById(com.mahya.maisonier.R.id.annuler);
        annuler.setOnClickListener(ParametrageActivity.this);
    }
}
