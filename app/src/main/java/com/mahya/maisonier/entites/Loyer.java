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

@Table(database = Maisonier.class, useBooleanGetterSetters = true, uniqueColumnGroups = {@UniqueGroup(groupNumber = 1, uniqueConflict = ConflictAction.FAIL)})
public class Loyer extends BaseModel {


    public static List<Loyer> loyers = new ArrayList<>();
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
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Mois> mois;


    @Column
    @Unique(unique = false, uniqueGroups = 1)
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "occupation_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Occupation> occupation;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "remise_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Remise> remise;

    public Loyer() {
    }

    public Loyer(Integer id) {
        this.id = id;
    }

    public Loyer(Integer id, double montantPayer) {
        this.id = id;
        this.montantPayer = montantPayer;
    }

    public static List<Loyer> findAll() {
        return SQLite.select().from(Loyer.class).queryList();
    }

    public static List<Loyer> getInitData(int size) {
        List<Loyer> Loyer = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Loyer.add(createRandomPerson());
        }
        return Loyer;
    }

    public static Loyer createRandomPerson() {
        Loyer Loyer = null;
        try {
            Loyer = loyers.get(0);
            loyers.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return Loyer;

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

    public void assoOccupation(Occupation occupation1) {
        occupation = new ForeignKeyContainer<>(Occupation.class);
        occupation.setModel(occupation1);
        occupation.put(Occupation_Table.id, occupation1.id);

    }

    public void assMois(Mois mois1) {
        mois = new ForeignKeyContainer<>(Mois.class);
        mois.setModel(mois1);
        mois.put(Occupation_Table.id, mois1.id);

    }

    public void assRemise(Remise remise1) {
        remise = new ForeignKeyContainer<>(Remise.class);
        remise.setModel(remise1);
        remise.put(Occupation_Table.id, remise1.id);

    }

    public ForeignKeyContainer<Mois> getMois() {
        return mois;
    }

    public ForeignKeyContainer<Occupation> getOccupation() {
        return occupation;
    }

    public ForeignKeyContainer<Remise> getRemise() {
        return remise;
    }
}
