package com.mahya.maisonier.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AffHabitantFragment extends Fragment {

    int id;
    private CoordinatorLayout activityTypelogement;
    private ImageView imgHabitant;
    private TextView nom;
    private TextView prenom;
    private TextView numeroCNI;
    private TextView genre;
    private TextView titre;
    private TextView email1;
    private TextView email2;
    private TextView tel1;
    private TextView tel2;
    private TextView tel3;
    private TextView tel4;
    private TextView dateDeNaissance;
    private TextView lieuDeNaissance;
    private TextView nomduPere;
    private TextView nomdelaMere;
    private TextView profession;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments().containsKey("id")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            id = getArguments().getInt("id");

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(String.valueOf(id));
            }
        }

        return inflater.inflate(R.layout.aff_habitant, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activityTypelogement = (CoordinatorLayout) view.findViewById(R.id.activity_typelogement);
        imgHabitant = (ImageView) view.findViewById(R.id.imgHabitant);
        nom = (TextView) view.findViewById(R.id.Nom);
        prenom = (TextView) view.findViewById(R.id.Prenom);
        numeroCNI = (TextView) view.findViewById(R.id.NumeroCNI);
        genre = (TextView) view.findViewById(R.id.Genre);
        titre = (TextView) view.findViewById(R.id.Titre);
        email1 = (TextView) view.findViewById(R.id.Email1);
        email2 = (TextView) view.findViewById(R.id.Email2);
        tel1 = (TextView) view.findViewById(R.id.Tel1);
        tel2 = (TextView) view.findViewById(R.id.Tel2);
        tel3 = (TextView) view.findViewById(R.id.Tel3);
        tel4 = (TextView) view.findViewById(R.id.Tel4);
        dateDeNaissance = (TextView) view.findViewById(R.id.DateDeNaissance);
        lieuDeNaissance = (TextView) view.findViewById(R.id.LieuDeNaissance);
        nomduPere = (TextView) view.findViewById(R.id.NomduPere);
        nomdelaMere = (TextView) view.findViewById(R.id.NomdelaMere);
        profession = (TextView) view.findViewById(R.id.Profession);

        if (id != 0) {
            final Habitant habitant = SQLite.select().from(Habitant.class).where(Habitant_Table.id.eq(id)).querySingle();

            if (habitant.getPhoto() != null) {
                imgHabitant.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + "" + habitant.getPhoto()));

            }
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            nom.setText(habitant.getNom());
            prenom.setText(habitant.getPrenom());
            numeroCNI.setText(habitant.getNumeroCNI());
            genre.setText(habitant.getGenre());
            titre.setText(habitant.getTitre());
            email1.setText(habitant.getEmail1());
            email2.setText(habitant.getEmail2());
            tel1.setText(habitant.getTel1());
            tel2.setText(habitant.getTel2());
            tel3.setText(habitant.getTel3());
            tel4.setText(habitant.getTel4());
            //  DateDeNaissance.setText((habitant.getDateNaissance().equals(null) ? "" : sdf.format(habitant.getDateNaissance())));
            lieuDeNaissance.setText(habitant.getLieuNaissance());
            nomduPere.setText(habitant.getNomDuPere());
            nomdelaMere.setText(habitant.getNomDeLaMere());
            profession.setText(habitant.getProfession());


            imgHabitant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ImageShow.class);
                    intent.putExtra("title", habitant.getNom());
                    intent.putExtra("image", habitant.getPhoto());

                    startActivity(intent);
                }
            });
        }
    }
}
