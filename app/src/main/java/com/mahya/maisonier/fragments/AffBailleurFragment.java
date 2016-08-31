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
import com.mahya.maisonier.entites.Bailleur;
import com.mahya.maisonier.entites.Bailleur_Table;
import com.mahya.maisonier.utils.Constants;
import com.mahya.maisonier.utils.ImageShow;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AffBailleurFragment extends Fragment {

    private CoordinatorLayout activityTypelogement;
    private ImageView imgBailleur;
    private TextView nom;
    private TextView prenom;
    private TextView numeroCNI;
    private TextView dateDelivranceCN;
    private TextView lieuDelivranceCNI;
    private TextView genre;
    private TextView titre;
    private TextView email1;
    private TextView email2;
    private TextView tel1;
    private TextView tel2;
    private TextView tel3;
    private TextView tel4;

    private int id;

    public AffBailleurFragment() {
    }


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

        return inflater.inflate(R.layout.aff_bailleur, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgBailleur = (ImageView) view.findViewById(R.id.imgBailleur);
        nom = (TextView) view.findViewById(R.id.Nom);
        prenom = (TextView) view.findViewById(R.id.Prenom);
        numeroCNI = (TextView) view.findViewById(R.id.NumeroCNI);
        dateDelivranceCN = (TextView) view.findViewById(R.id.DateDelivranceCN);
        lieuDelivranceCNI = (TextView) view.findViewById(R.id.LieuDelivranceCNI);
        genre = (TextView) view.findViewById(R.id.Genre);
        titre = (TextView) view.findViewById(R.id.Titre);
        email1 = (TextView) view.findViewById(R.id.Email1);
        email2 = (TextView) view.findViewById(R.id.Email2);
        tel1 = (TextView) view.findViewById(R.id.Tel1);
        tel2 = (TextView) view.findViewById(R.id.Tel2);
        tel3 = (TextView) view.findViewById(R.id.Tel3);
        tel4 = (TextView) view.findViewById(R.id.Tel4);

        if (id != 0) {
            final Bailleur bailleur = SQLite.select().from(Bailleur.class).where(Bailleur_Table.id.eq(id)).querySingle();

            if (bailleur.getPhoto() != null) {

                imgBailleur.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + "" + bailleur.getPhoto()));
            } else {
                imgBailleur.setImageResource(R.drawable.avatar);
            }
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            nom.setText(bailleur.getNom());
            prenom.setText(bailleur.getPrenom());
            numeroCNI.setText(bailleur.getNumeroCNI());
            dateDelivranceCN.setText((bailleur.getDateDelivraisonCni() == null) ? "aucune" : sdf.format(bailleur.getDateDelivraisonCni()));
            lieuDelivranceCNI.setText(bailleur.getLieuDelivraisonCni());
            genre.setText(bailleur.getGenre());
            titre.setText(bailleur.getTitre());
            email1.setText(bailleur.getEmail1());
            email2.setText(bailleur.getEmail2());
            tel1.setText(bailleur.getTel1());
            tel2.setText(bailleur.getTel2());
            tel3.setText(bailleur.getTel3());
            tel4.setText(bailleur.getTel4());
            imgBailleur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);
                    intent.putExtra("image", bailleur.getPhoto());

                    startActivity(intent);
                }
            });

        }

    }

}
