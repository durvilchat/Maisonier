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
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Recette extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @Column(name = "depot")
    Double depot;
    @Column(name = "eau")
    Double eau;
    @Column(name = "electricite")
    Double electricite;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mois", length = 255)
    String mois;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "referenc", length = 255)
    String referenc;

    public Recette() {
    }

    public Recette(Integer id) {
        this.id = id;
    }

    public Recette(Integer id, String mois, String referenc) {
        this.id = id;
        this.mois = mois;
        this.referenc = referenc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDepot() {
        return depot;
    }

    public void setDepot(Double depot) {
        this.depot = depot;
    }

    public Double getEau() {
        return eau;
    }

    public void setEau(Double eau) {
        this.eau = eau;
    }

    public Double getElectricite() {
        return electricite;
    }

    public void setElectricite(Double electricite) {
        this.electricite = electricite;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getReferenc() {
        return referenc;
    }

    public void setReferenc(String referenc) {
        this.referenc = referenc;
    }


}
