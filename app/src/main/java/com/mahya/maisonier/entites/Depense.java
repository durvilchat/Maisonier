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
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.Date;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Depense extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Column(name = "date_enregistrement")

    Date dateEnregistrement;
    @Size(max = 255)
    @Column(name = "description", length = 255)
    String description;
    @Size(max = 255)
    @Column(name = "designation", length = 255)
    String designation;

    @NotNull
    @Column(name = "montant")
    double montant;
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "bailleur_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Bailleur> bailleur;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "batiment_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Batiment> batiment;
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "mois_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Mois> mois;

    public Depense() {
    }

    public Depense(Integer id) {
        this.id = id;
    }

    public Depense(Integer id, double montant) {
        this.id = id;
        this.montant = montant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(Date dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void assoMois(Mois mois1) {
        mois = new ForeignKeyContainer<>(Mois.class);
        mois.setModel(mois1);
        mois.put(Mois_Table.id, mois1.id);

    }

    public void assoBailleur(Bailleur bailleur1) {
        bailleur = new ForeignKeyContainer<>(Bailleur.class);
        bailleur.setModel(bailleur1);
        bailleur.put(Bailleur_Table.id, bailleur1.id);

    }

    public void assoBatiment(Batiment batiment1) {
        batiment = new ForeignKeyContainer<>(Batiment.class);
        batiment.setModel(batiment1);
        batiment.put(Batiment_Table.id, batiment1.id);

    }

}
