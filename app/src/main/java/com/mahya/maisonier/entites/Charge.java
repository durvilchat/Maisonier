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

import java.util.Date;
import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Charge extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Column(name = "date_paiement")

    Date datePaiement;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "designation", length = 255)
    String designation;

    @NotNull
    @Column(name = "montant")
    double montant;

    @NotNull
    @Column(name = "montant_payer")
    double montantPayer;
    @Size(max = 255)
    @Column(name = "observation", length = 255)
    String observation;
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "mois_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Mois> mois;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "occupation_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Occupation> occupation;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "typecharge_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<TypeCharge> typeCharge;

    public Charge() {
    }

    public Charge(Integer id) {
        this.id = id;
    }

    public Charge(Integer id, String designation, double montant, double montantPayer) {
        this.id = id;
        this.designation = designation;
        this.montant = montant;
        this.montantPayer = montantPayer;
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

    public void assoTypeCharge(TypeCharge typeCharge1) {
        typeCharge = new ForeignKeyContainer<>(TypeCharge.class);
        typeCharge.setModel(typeCharge1);
        typeCharge.put(TypeCharge_Table.id, typeCharge1.id);

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

    public ForeignKeyContainer<TypeCharge> getTypeCharge() {
        return typeCharge;
    }

    @Override
    public String toString() {
        return designation ;
    }

    public static List<Charge>findAll(){
        return SQLite.select().from(Charge.class).queryList();
    }
}
