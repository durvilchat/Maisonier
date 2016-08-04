/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahya.maisonier.entites;

import android.support.annotation.Size;

import com.mahya.maisonier.dataBase.Maisonier;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Caracteristique extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Unique
    @Column(name = "libelle", length = 255)
    String libelle;
    //  //@OneToMany(cascade = CascadeType.ALL, mappedBy = "caracteristique")
    List<CaracteristiqueLogement> caracteristiqueLogementList;

    public Caracteristique() {
    }

    public Caracteristique(Integer id) {
        this.id = id;
    }

    public Caracteristique(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "caracteristiqueLogementList", isVariablePrivate = true)
    public List<CaracteristiqueLogement> getCaracteristiqueLogementList() {
        if (caracteristiqueLogementList == null || caracteristiqueLogementList.isEmpty()) {
            caracteristiqueLogementList = SQLite.select()
                    .from(CaracteristiqueLogement.class)
                    .where(CaracteristiqueLogement_Table.caracteristique_id.eq(id))
                    .queryList();
        }

        return caracteristiqueLogementList;
    }

    public void setCaracteristiqueLogementList(List<CaracteristiqueLogement> caracteristiqueLogementList) {
        this.caracteristiqueLogementList = caracteristiqueLogementList;
    }


}
