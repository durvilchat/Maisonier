/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahya.maisonier.entites;

import android.support.annotation.Size;

import com.mahya.maisonier.dataBase.Maisonier;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class PhotoBatiment extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "etat")
    boolean etat;

    @NotNull
    @Unique
    @Size(min = 1, max = 255)
    @Column(name = "nom", length = 255)
    String nom;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "batiment_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Batiment> batiment;

    public PhotoBatiment() {
    }

    public PhotoBatiment(Integer id) {
        this.id = id;
    }

    public PhotoBatiment(Integer id, boolean etat, String nom) {
        this.id = id;
        this.etat = etat;
        this.nom = nom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void assoBatiment(Batiment batiment1) {
        batiment = new ForeignKeyContainer<>(Batiment.class);
        batiment.setModel(batiment1);
        batiment.put(Batiment_Table.id, batiment1.id);

    }
}
