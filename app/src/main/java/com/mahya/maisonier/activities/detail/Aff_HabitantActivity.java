package com.mahya.maisonier.activities.detail;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahya.maisonier.R;
import com.mahya.maisonier.entites.Habitant;
import com.mahya.maisonier.entites.Habitant_Table;
import com.mahya.maisonier.utils.Constants;
import com.mahya.maisonier.utils.ImageShow;
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
    protected TextView Email1;
    protected TextView Email2;
    protected TextView Tel1;
    protected TextView Tel2;
    protected TextView Tel3;
    protected TextView Tel4;
    protected TextView DateDeNaissance;
    protected TextView LieuDeNaissance;
    protected TextView NomduPere;
    protected TextView NomdelaMere;
    protected TextView Profession;
    int id;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.aff_habitant);
        id = getIntent().getIntExtra("id", 0);
        initView();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (id != 0) {
            final Habitant habitant = SQLite.select().from(Habitant.class).where(Habitant_Table.id.eq(id)).querySingle();

            if (habitant.getPhoto() != null) {
                imgHabitant.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + "" + habitant.getPhoto()));

            }
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Nom.setText(habitant.getNom());
            Prenom.setText(habitant.getPrenom());
            NumeroCNI.setText(habitant.getNumeroCNI());
            Genre.setText(habitant.getGenre());
            Titre.setText(habitant.getTitre());
            Email1.setText(habitant.getEmail1());
            Email2.setText(habitant.getEmail2());
            Tel1.setText(habitant.getTel1());
            Tel2.setText(habitant.getTel2());
            Tel3.setText(habitant.getTel3());
            Tel4.setText(habitant.getTel4());
          //  DateDeNaissance.setText((habitant.getDateNaissance().equals(null) ? "" : sdf.format(habitant.getDateNaissance())));
            LieuDeNaissance.setText(habitant.getLieuNaissance());
            NomduPere.setText(habitant.getNomDuPere());
            NomdelaMere.setText(habitant.getNomDeLaMere());
            Profession.setText(habitant.getProfession());


            imgHabitant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Aff_HabitantActivity.this, ImageShow.class);
                    intent.putExtra("title", habitant.getNom());
                    intent.putExtra("image", habitant.getPhoto());

                    startActivity(intent);
                }
            });
        }


    }

    private TextView mOperation;
    private EditText mEmail1;
    private EditText mEmail2;
    private EditText mTel2;
    private EditText mTel3;
    private EditText mTel4;
    private Button mValider;
    private Button mAnnuler;

    public void addContact(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_contact);
        // Initialisation du formulaire


        mOperation = (TextView) dialog.findViewById(R.id.operation);
        mEmail1 = (EditText) dialog.findViewById(R.id.Email1);
        mEmail2 = (EditText) dialog.findViewById(R.id.Email2);
        mTel2 = (EditText) dialog.findViewById(R.id.tel2);
        mTel3 = (EditText) dialog.findViewById(R.id.tel3);
        mTel4 = (EditText) dialog.findViewById(R.id.tel4);
        mValider = (Button) dialog.findViewById(R.id.valider);
        mAnnuler = (Button) dialog.findViewById(R.id.annuler);

        // Click cancel to dismiss android custom dialog box
        mValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Habitant habitant = SQLite.select().from(Habitant.class).where(Habitant_Table.id.eq(id)).querySingle();
                habitant.setId(id);
                habitant.setEmail1(mEmail1.getText().toString().trim());
                habitant.setEmail2(mEmail2.getText().toString().trim());
                habitant.setTel2(mTel2.getText().toString().trim());
                habitant.setTel3(mTel3.getText().toString().trim());
                habitant.setTel4(mTel4.getText().toString().trim());
                habitant.update();
                dialog.dismiss();
            }
        });
        mAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });
        dialog.show();
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
        imgHabitant = (ImageView) findViewById(R.id.imgHabitant);
        Nom = (TextView) findViewById(R.id.Nom);
        Prenom = (TextView) findViewById(R.id.Prenom);
        NumeroCNI = (TextView) findViewById(R.id.NumeroCNI);
        Genre = (TextView) findViewById(R.id.Genre);
        Titre = (TextView) findViewById(R.id.Titre);
        Email1 = (TextView) findViewById(R.id.Email1);
        Email2 = (TextView) findViewById(R.id.Email2);
        Tel1 = (TextView) findViewById(R.id.Tel1);
        Tel2 = (TextView) findViewById(R.id.Tel2);
        Tel3 = (TextView) findViewById(R.id.Tel3);
        Tel4 = (TextView) findViewById(R.id.Tel4);
        DateDeNaissance = (TextView) findViewById(R.id.DateDeNaissance);
        LieuDeNaissance = (TextView) findViewById(R.id.LieuDeNaissance);
        NomduPere = (TextView) findViewById(R.id.NomduPere);
        NomdelaMere = (TextView) findViewById(R.id.NomdelaMere);
        Profession = (TextView) findViewById(R.id.Profession);


    }
}
