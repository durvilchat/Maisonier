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

import java.util.ArrayList;
import java.util.List;
@ModelContainer
@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class TypePenalite extends BaseModel {


    public static List<TypePenalite> typePenalites = new ArrayList<>();

    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "delai")
    int delai;

    @NotNull
    @Column(name = "etat")
    boolean etat;

    @NotNull
    @Unique
    @Size(min = 1, max = 255)
    @Column(name = "libelle", length = 255)
    String libelle;

    @NotNull
    @Column(name = "taux")
    double taux;

    List<Penalite> penaliteList;

    public TypePenalite() {
    }

    public TypePenalite(Integer id) {
        this.id = id;
    }

    public TypePenalite(Integer id, int delai, boolean etat, String libelle, double taux) {
        this.id = id;
        this.delai = delai;
        this.etat = etat;
        this.libelle = libelle;
        this.taux = taux;
    }

    public static List<TypePenalite> getInitData(int size) {
        List<TypePenalite> TypePenalite = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TypePenalite.add(createRandomPerson());
        }
        return TypePenalite;
    }

    public static TypePenalite createRandomPerson() {
        TypePenalite TypePenalite = null;
        try {
            TypePenalite = typePenalites.get(0);
            typePenalites.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return TypePenalite;

    }

    public static List<TypePenalite> findAll() {
        return SQLite.select().from(TypePenalite.class).queryList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDelai() {
        return delai;
    }

    public void setDelai(int delai) {
        this.delai = delai;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "penaliteList", isVariablePrivate = true)
    public List<Penalite> getPenaliteList() {
        if (penaliteList == null || penaliteList.isEmpty()) {
            penaliteList = SQLite.select()
                    .from(Penalite.class)
                    .where(Penalite_Table.typepenalite_id.eq(id))
                    .queryList();
        }
        return penaliteList;
    }

    public void setPenaliteList(List<Penalite> penaliteList) {
        this.penaliteList = penaliteList;
    }


    @Override
    public String toString() {
        return  libelle +" "+taux +"%";
    }
}
