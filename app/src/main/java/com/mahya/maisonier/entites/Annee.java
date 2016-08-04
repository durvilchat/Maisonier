/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahya.maisonier.entites;


import android.support.annotation.Size;

import com.mahya.maisonier.dataBase.Maisonier;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@ModelContainer
@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Annee extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Unique
    @Column(name = "annee")
    int annee;

    @NotNull
    @Column(name = "etat")
    boolean etat;
    @Size(max = 255)
    @Column(name = "statut", length = 255)
    String statut;

    List<Mois> moisList;


    public Annee() {
    }

    public Annee(Integer id) {
        this.id = id;
    }

    public Annee(Integer id, int annee, boolean etat) {
        this.id = id;
        this.annee = annee;
        this.etat = etat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "moisList", isVariablePrivate = true)
    public List<Mois> getMoisList() {
        if (moisList == null || moisList.isEmpty()) {
            moisList = SQLite.select()
                    .from(Mois.class)
                    .where(Mois_Table.annee_id.eq(id))
                    .queryList();
        }
        return moisList;
    }

    public void setMoisList(List<Mois> moisList) {
        this.moisList = moisList;
    }

    public boolean isEtat() {
        return etat;

    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
}
