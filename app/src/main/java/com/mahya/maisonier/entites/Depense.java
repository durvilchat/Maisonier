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
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ModelContainer
@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Depense extends BaseModel {


    public static List<Depense> depenses = new ArrayList<>();
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

    public static List<Depense> getInitData(int size) {
        List<Depense> depense = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            depense.add(createRandomPerson());
        }
        return depense;

    }

    public static Depense createRandomPerson() {
        Depense depense = null;
        try {
            depense = depenses.get(0);
            depenses.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return depense;

    }

    public static List<Depense> findAll() {
        return SQLite.select().from(Depense.class).queryList();
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

    public ForeignKeyContainer<Bailleur> getBailleur() {
        return bailleur;
    }

    public ForeignKeyContainer<Batiment> getBatiment() {
        return batiment;
    }

    public ForeignKeyContainer<Mois> getMois() {
        return mois;
    }

}
