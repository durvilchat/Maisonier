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
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Batiment extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Unique
    @Size(min = 1, max = 255)
    @Column(name = "code", length = 255)
    String code;
    @NotNull

    @Column(name = "etat")
    boolean etat;
    @Size(max = 255)
    @Column(name = "nom", length = 255)
    String nom;
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "cite_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Cite> cite;
    List<Logement> logementList;
    List<PhotoBatiment> photoBatimentList;
    List<Depense> depenseList;


    public Batiment() {
    }

    public Batiment(Integer id) {
        this.id = id;
    }

    public Batiment(Integer id, String code, boolean etat) {
        this.id = id;
        this.code = code;
        this.etat = etat;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public ForeignKeyContainer<Cite> getCite() {
        return cite;
    }

    public void setCite(ForeignKeyContainer<Cite> cite) {
        this.cite = cite;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "logementList", isVariablePrivate = true)
    public List<Logement> getLogementList() {
        if (logementList == null || logementList.isEmpty()) {
            logementList = SQLite.select()
                    .from(Logement.class)
                    .where(Logement_Table.batiment_id.eq(id))
                    .queryList();
        }
        return logementList;
    }

    public void setLogementList(List<Logement> logementList) {
        this.logementList = logementList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "photoBatimentList", isVariablePrivate = true)
    public List<PhotoBatiment> getPhotoBatimentList() {
        if (photoBatimentList == null || photoBatimentList.isEmpty()) {
            photoBatimentList = SQLite.select()
                    .from(PhotoBatiment.class)
                    .where(PhotoBatiment_Table.batiment_id.eq(id))
                    .queryList();
        }
        return photoBatimentList;
    }

    public void setPhotoBatimentList(List<PhotoBatiment> photoBatimentList) {
        this.photoBatimentList = photoBatimentList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "depenseList", isVariablePrivate = true)
    public List<Depense> getDepenseList() {
        if (depenseList == null || depenseList.isEmpty()) {
            depenseList = SQLite.select()
                    .from(Depense.class)
                    .where(Depense_Table.batiment_id.eq(id))
                    .queryList();
        }
        return depenseList;
    }

    public void setDepenseList(List<Depense> depenseList) {
        this.depenseList = depenseList;
    }

    public void assoCite(Cite cite1) {
        cite = new ForeignKeyContainer<>(Cite.class);
        cite.setModel(cite1);
        cite.put(Cite_Table.id, cite1.id);

    }
}
