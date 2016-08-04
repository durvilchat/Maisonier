/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahya.maisonier.entites;


import android.support.annotation.Size;

import com.mahya.maisonier.dataBase.Maisonier;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.annotation.UniqueGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true, uniqueColumnGroups = {@UniqueGroup(groupNumber = 1, uniqueConflict = ConflictAction.FAIL)})
public class Rubrique extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "etat")
    boolean etat;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "libelle", length = 255)
    String libelle;

    @NotNull
    @Unique(unique = false, uniqueGroups = 1)
    @Column(name = "numero")
    int numero;

    @NotNull
    @Column(name = "valeur")
    boolean valeur;
    @Unique(unique = false, uniqueGroups = 1)
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "articlebail_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<ArticleBail> articleBail;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "rubrique")
    List<ContratRubrique> contratRubriqueList;

    public Rubrique() {
    }

    public Rubrique(Integer id) {
        this.id = id;
    }

    public Rubrique(Integer id, boolean etat, String libelle, int numero, boolean valeur) {
        this.id = id;
        this.etat = etat;
        this.libelle = libelle;
        this.numero = numero;
        this.valeur = valeur;
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

    public boolean isValeur() {
        return valeur;
    }

    public void setValeur(boolean valeur) {
        this.valeur = valeur;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "contratRubriqueList", isVariablePrivate = true)
    public List<ContratRubrique> getContratRubrique() {
        if (contratRubriqueList == null || contratRubriqueList.isEmpty()) {
            contratRubriqueList = SQLite.select()
                    .from(ContratRubrique.class)
                    .where(ContratRubrique_Table.rubrique_id.eq(id))
                    .queryList();
        }
        return contratRubriqueList;
    }

    public ForeignKeyContainer<ArticleBail> getArticleBail() {
        return articleBail;
    }

    public void setArticleBail(ForeignKeyContainer<ArticleBail> articleBail) {
        this.articleBail = articleBail;
    }


    public void setContratRubriqueList(List<ContratRubrique> contratRubriqueList) {
        this.contratRubriqueList = contratRubriqueList;
    }

    public void assocArticleBail(ArticleBail bail) {
        articleBail = new ForeignKeyContainer<>(ArticleBail.class);
        articleBail.setModel(bail);
        articleBail.put(ArticleBail_Table.id, bail.id);

    }
}
