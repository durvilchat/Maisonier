package com.mahya.maisonier.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahya.maisonier.R;
import com.mahya.maisonier.entites.Occupation;
import com.mahya.maisonier.entites.Occupation_Table;
import com.mahya.maisonier.utils.Constants;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AffOccupationFragment extends Fragment {

    protected ImageView imgOccupation;
    protected TextView Habitant;
    protected TextView Logement;
    protected TextView LoyerDeBase;
    protected TextView ModeDePayement;
    protected CheckBox Etat;
    protected CheckBox PaieEau;
    protected CheckBox PaieElectricite;
    protected CheckBox PaieCable;
    protected TextView DateEntree;
    protected TextView DateSortie;
    protected TextView Description;
    protected View rootView;
    int id;

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

        rootView = inflater.inflate(R.layout.aff_occcupations, null);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (id != 0) {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Occupation occupation = SQLite.select().from(Occupation.class).where(Occupation_Table.id.eq(id)).querySingle();

            if (occupation.getHabitant().load().getPhoto() != null) {

                imgOccupation.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + "" + occupation.getHabitant().load().getPhoto()));
            } else {
                imgOccupation.setImageResource(R.drawable.avatar);
            }
            Habitant.setText(occupation.getHabitant().load().getNom() + "  " + occupation.getHabitant().load().getPrenom());
            Logement.setText(occupation.getLogement().load().getReference());
            LoyerDeBase.setText(String.valueOf(occupation.getLoyerBase()));
            // Etat.setText(occupation.getE);
            ModeDePayement.setText(String.valueOf(occupation.getModePaiement()));
            PaieEau.setText((occupation.isPaieEau()) ? "oui" : "non");
            PaieElectricite.setText((occupation.isPaieElectricite()) ? "oui" : "non");
            PaieCable.setText((occupation.isPaieCable()) ? "oui" : "non");
            DateEntree.setText(sdf.format(occupation.getDateEntree()));
            DateSortie.setText(sdf.format(occupation.getDateSortie()));
            Description.setText(occupation.getDescription());
        }

    }

    private void initView(View rootView) {
        imgOccupation = (ImageView) rootView.findViewById(R.id.imageView4);
        Habitant = (TextView) rootView.findViewById(R.id.Habitant);
        Logement = (TextView) rootView.findViewById(R.id.Logement);
        LoyerDeBase = (TextView) rootView.findViewById(R.id.LoyerDeBase);
        ModeDePayement = (TextView) rootView.findViewById(R.id.ModeDePayement);
        Etat = (CheckBox) rootView.findViewById(R.id.Etat);
        PaieEau = (CheckBox) rootView.findViewById(R.id.PaieEau);
        PaieElectricite = (CheckBox) rootView.findViewById(R.id.PaieElectricite);
        PaieCable = (CheckBox) rootView.findViewById(R.id.PaieCable);
        DateEntree = (TextView) rootView.findViewById(R.id.DateEntree);
        DateSortie = (TextView) rootView.findViewById(R.id.DateSortie);
        Description = (TextView) rootView.findViewById(R.id.Description);
    }
}
