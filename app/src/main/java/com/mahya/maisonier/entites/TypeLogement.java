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
public class TypeLogement extends BaseModel {


    public static List<TypeLogement> typelogs = new ArrayList<>();
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @NotNull
    @Unique
    @Size(min = 1, max = 255)
    @Column(name = "code", length = 255)
    String code;
    @Size(max = 255)
    @Column(name = "description", length = 255)
    String description;
    @NotNull
    @Column(name = "etat")
    boolean etat;
    @NotNull
    @Size(min = 1, max = 255)
    @Unique
    @Column(name = "libelle", length = 255)
    String libelle;
    List<Logement> logementList;

    public TypeLogement() {
    }

    public TypeLogement(Integer id) {
        this.id = id;
    }

    public TypeLogement(Integer id, String code, boolean etat, String libelle) {
        this.id = id;
        this.code = code;
        this.etat = etat;
        this.libelle = libelle;
    }

    public TypeLogement(String code, boolean etat, String libelle) {

        this.code = code;
        this.etat = etat;
        this.libelle = libelle;
    }

    public static List<TypeLogement> getInitData(int size) {
        List<TypeLogement> typeLogements = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            typeLogements.add(createRandomPerson());
        }
        return typeLogements;
    }

    public static TypeLogement createRandomPerson() {
        TypeLogement typeLogement = null;
        try {
            typeLogement = typelogs.get(0);
            typelogs.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return typeLogement;

    }

    public static List<TypeLogement> findAll() {
        return SQLite.select().from(TypeLogement.class).queryList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "logementList", isVariablePrivate = true)
    public List<Logement> getLogementList() {
        if (logementList == null || logementList.isEmpty()) {
            logementList = SQLite.select()
                    .from(Logement.class)
                    .where(Logement_Table.typelogement_id.eq(id))
                    .queryList();
        }
        return logementList;
    }

    public void setLogementList(List<Logement> logementList) {
        this.logementList = logementList;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
}
