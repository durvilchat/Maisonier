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
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Depot extends BaseModel {


    public static List<Depot> depots = new ArrayList<>();
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "compensation")
    boolean compensation;

    @NotNull
    @Column(name = "date_depot")

    Date dateDepot;

    @Column(name = "montant")
    Double montant;
    @Size(max = 255)
    @Column(name = "observation", length = 255)
    String observation;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "mois_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Mois> mois;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "occupation_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Occupation> occupation;

    public Depot() {
    }

    public Depot(Integer id) {
        this.id = id;
    }

    public Depot(Integer id, boolean compensation, Date dateDepot) {
        this.id = id;
        this.compensation = compensation;
        this.dateDepot = dateDepot;
    }

    public static List<Depot> getDepots() {
        return depots;
    }

    public static List<Depot> getInitData(int size) {
        List<Depot> Depot = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Depot.add(createRandomPerson());
        }
        return Depot;
    }

    public static Depot createRandomPerson() {
        Depot Depot = null;
        try {
            Depot = depots.get(0);
            depots.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return Depot;

    }

    public static List<Depot> findAll() {
        return SQLite.select().from(Depot.class).queryList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isCompensation() {
        return compensation;
    }

    public void setCompensation(boolean compensation) {
        this.compensation = compensation;
    }

    public Date getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(Date dateDepot) {
        this.dateDepot = dateDepot;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
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

    public void assoMois(Mois mois1) {
        mois = new ForeignKeyContainer<>(Mois.class);
        mois.setModel(mois1);
        mois.put(Mois_Table.id, mois1.id);

    }

    public ForeignKeyContainer<Mois> getMois() {
        return mois;
    }

    public ForeignKeyContainer<Occupation> getOccupation() {
        return occupation;
    }


}
