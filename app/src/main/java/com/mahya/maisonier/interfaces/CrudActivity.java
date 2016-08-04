package com.mahya.maisonier.interfaces;


import android.view.View;

/**
 * Created by LARUMEUR on 31/07/2016.
 */

public interface CrudActivity {

    /**
     * methode du formulaire de modification
     *
     * @param id
     */

    void modifier(final int id);


    void supprimer(final int id);


    void ajouter(View view);


    void detail(final int i);

}
