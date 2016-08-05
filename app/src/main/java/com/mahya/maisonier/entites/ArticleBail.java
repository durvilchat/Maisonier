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

import java.util.ArrayList;
import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class ArticleBail extends BaseModel {


    public static List<ArticleBail> articleBails = new ArrayList<>();
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "etat")
    boolean etat;

    @NotNull
    @Unique
    @Size(min = 1, max = 255)
    @Column(name = "libelle", length = 255)
    String libelle;

    @NotNull
    @Unique
    @Column(name = "numero")
    int numero;

    List<Rubrique> rubriqueList;


    public ArticleBail() {
    }

    public ArticleBail(Integer id) {
        this.id = id;
    }

    public ArticleBail(Integer id, boolean etat, String libelle, int numero) {
        this.id = id;
        this.etat = etat;
        this.libelle = libelle;
        this.numero = numero;
    }

    public static List<ArticleBail> getInitData(int size) {
        List<ArticleBail> articleBails = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            articleBails.add(createRandomPerson());
        }
        return articleBails;
    }

    public static ArticleBail createRandomPerson() {
        ArticleBail batiment = null;
        try {
            batiment = articleBails.get(0);
            articleBails.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return batiment;

    }

    public static List<ArticleBail> findAll() {
        return SQLite.select().from(ArticleBail.class).queryList();
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

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "rubriqueList", isVariablePrivate = true)
    public List<Rubrique> getMyRubrique() {
        if (rubriqueList == null || rubriqueList.isEmpty()) {
            rubriqueList = SQLite.select()
                    .from(Rubrique.class)
                    .where(Rubrique_Table.articlebail_id.eq(id))
                    .queryList();
        }
        return rubriqueList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "rubriqueList", isVariablePrivate = true)
    public List<Rubrique> getRubriqueList() {
        if (rubriqueList == null || rubriqueList.isEmpty()) {
            rubriqueList = SQLite.select()
                    .from(Rubrique.class)
                    .where(Rubrique_Table.articlebail_id.eq(id))
                    .queryList();
        }
        return rubriqueList;
    }

    public void setRubriqueList(List<Rubrique> rubriqueList) {
        this.rubriqueList = rubriqueList;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
