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
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.List;

@ModelContainer
@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class CaracteristiqueLogement extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Size(max = 255)
    @Column(name = "valeur", length = 255)
    String valeur;
    @Unique
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "caracteristique_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Caracteristique> caracteristique;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "logement_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Logement> logement;

    public CaracteristiqueLogement() {
    }

    public CaracteristiqueLogement(Integer id) {
        this.id = id;
    }

    public static List<CaracteristiqueLogement> findAll() {
        return SQLite.select().from(CaracteristiqueLogement.class).queryList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public ForeignKeyContainer<Caracteristique> getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(ForeignKeyContainer<Caracteristique> caracteristique) {
        this.caracteristique = caracteristique;
    }

    public ForeignKeyContainer<Logement> getLogement() {
        return logement;
    }

    public void setLogement(ForeignKeyContainer<Logement> logement) {
        this.logement = logement;
    }

    public void assoLogement(Logement logement1) {
        logement = new ForeignKeyContainer<>(Logement.class);
        logement.setModel(logement1);
        logement.put(Logement_Table.id, logement1.id);

    }

    public void assoCaracteristique(Caracteristique caracteristique1) {
        caracteristique = new ForeignKeyContainer<>(Caracteristique.class);
        caracteristique.setModel(caracteristique1);
        caracteristique.put(Caracteristique_Table.id, caracteristique1.id);

    }
}
