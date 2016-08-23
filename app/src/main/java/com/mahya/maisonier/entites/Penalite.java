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
public class Penalite extends BaseModel {


    public static List<Penalite> penalites = new ArrayList<>();
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "date_paiement")

    Date datePaiement;

    @NotNull
    @Column(name = "etat")
    boolean etat;

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
    @Unique(unique = false, uniqueGroups = 1)
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "mois_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Mois> mois;


    @Column
    @Unique(unique = false, uniqueGroups = 1)
    @ForeignKey(references = {@ForeignKeyReference(columnName = "occupation_id",
            columnType = Integer.class, foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Occupation> occupation;

    @Column
    @Unique(unique = false, uniqueGroups = 1)
    @ForeignKey(references = {@ForeignKeyReference(columnName = "typepenalite_id",
            columnType = Integer.class, foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<TypePenalite> typePenalite;

    public Penalite() {
    }

    public Penalite(Integer id) {
        this.id = id;
    }

    public Penalite(Integer id, Date datePaiement, boolean etat, double montant, double montantPayer) {
        this.id = id;
        this.datePaiement = datePaiement;
        this.etat = etat;
        this.montant = montant;
        this.montantPayer = montantPayer;
    }

    public static List<Penalite> findAll() {
        return SQLite.select().from(Penalite.class).queryList();
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

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
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

    public void assoOccupation(Occupation occupation1) {
        occupation = new ForeignKeyContainer<>(Occupation.class);
        occupation.setModel(occupation1);
        occupation.put(Occupation_Table.id, occupation1.id);

    }

    public void assoTypePenalite(TypePenalite typePenalite1) {
        typePenalite = new ForeignKeyContainer<>(TypePenalite.class);
        typePenalite.setModel(typePenalite1);
        typePenalite.put(Occupation_Table.id, typePenalite1.id);

    }

    public void assoMois(Mois mois1) {
        mois = new ForeignKeyContainer<>(Mois.class);
        mois.setModel(mois1);
        mois.put(Mois_Table.id, mois1.id);

    }

}
