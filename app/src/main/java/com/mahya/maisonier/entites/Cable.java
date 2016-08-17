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
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.annotation.UniqueGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ModelContainer
@Table(database = Maisonier.class, useBooleanGetterSetters = true, uniqueColumnGroups = {@UniqueGroup(groupNumber = 1, uniqueConflict = ConflictAction.FAIL)})
public class Cable extends BaseModel {


    public static List<Cable> cables = new ArrayList<>();
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Column(name = "date_paiement")

    Date datePaiement;

    @NotNull
    @Column(name = "montant_payer")
    double montantPayer;
    @Size(max = 255)
    @Column(name = "observation", length = 255)
    String observation;

    @Column
    @Unique(unique = false, uniqueGroups = 1)
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "mois_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Mois> mois;


    @Column
    @Unique(unique = false, uniqueGroups = 1)
    @ForeignKey(references = {@ForeignKeyReference(columnName = "occupation_id", columnType = Integer.class, foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Occupation> occupation;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "parametre_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Parametre> parametre;

    public Cable() {
    }

    public Cable(Integer id) {
        this.id = id;
    }

    public Cable(Integer id, double montantPayer) {
        this.id = id;
        this.montantPayer = montantPayer;
    }

    public static List<Cable> getCables() {
        return cables;
    }

    public static List<Cable> getInitData(int size) {
        List<Cable> Cable = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Cable.add(createRandomPerson());
        }
        return Cable;
    }

    public static Cable createRandomPerson() {
        Cable Cable = null;
        try {
            Cable = cables.get(0);
            cables.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return Cable;

    }

    public static List<Cable> findAll() {
        return SQLite.select().from(Cable.class).queryList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public double getMontantPayer() {
        return montantPayer;
    }

    public void setMontantPayer(double montantPayer) {
        this.montantPayer = montantPayer;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void assoMois(Mois mois1) {
        mois = new ForeignKeyContainer<>(Mois.class);
        mois.setModel(mois1);
        mois.put(Mois_Table.id, mois1.id);

    }

    public void assoParamentre(Parametre parametre1) {
        parametre = new ForeignKeyContainer<>(Parametre.class);
        parametre.setModel(parametre1);
        parametre.put(Parametre_Table.id, parametre1.id);

    }

    public void assoOccupation(Occupation occupation1) {
        occupation = new ForeignKeyContainer<>(Occupation.class);
        occupation.setModel(occupation1);
        occupation.put(Occupation_Table.id, occupation1.id);

    }

    public ForeignKeyContainer<Mois> getMois() {
        return mois;
    }

    public ForeignKeyContainer<Occupation> getOccupation() {
        return occupation;
    }

    public ForeignKeyContainer<Parametre> getParametre() {
        return parametre;
    }


}
