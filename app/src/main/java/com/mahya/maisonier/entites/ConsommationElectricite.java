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
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.Date;
import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class ConsommationElectricite extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "ancien_index")
    int ancienIndex;
    @Column(name = "date_paiement")

    Date datePaiement;
    @Column(name = "date_releve")

    Date dateReleve;
    @Size(max = 255)
    @Column(name = "description", length = 255)
    String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montant_payer")
    Double montantPayer;

    @NotNull
    @Column(name = "nouveau_index")
    int nouveauIndex;
    @Size(max = 255)
    @Column(name = "observation", length = 255)
    String observation;
    //@OneToMany(mappedBy = "consommationElectricite")
    List<ConsommationElectricite> consommationElectriciteList;
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "consommationelectricite_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<ConsommationElectricite> consommationElectricite;
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "habitant_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Habitant> habitant;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "logement_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Logement> logement;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "mois_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Mois> mois;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "parametre_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Parametre> parametre;

    public ConsommationElectricite() {
    }

    public ConsommationElectricite(Integer id) {
        this.id = id;
    }

    public ConsommationElectricite(Integer id, int ancienIndex, int nouveauIndex) {
        this.id = id;
        this.ancienIndex = ancienIndex;
        this.nouveauIndex = nouveauIndex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAncienIndex() {
        return ancienIndex;
    }

    public void setAncienIndex(int ancienIndex) {
        this.ancienIndex = ancienIndex;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Date getDateReleve() {
        return dateReleve;
    }

    public void setDateReleve(Date dateReleve) {
        this.dateReleve = dateReleve;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMontantPayer() {
        return montantPayer;
    }

    public void setMontantPayer(Double montantPayer) {
        this.montantPayer = montantPayer;
    }

    public int getNouveauIndex() {
        return nouveauIndex;
    }

    public void setNouveauIndex(int nouveauIndex) {
        this.nouveauIndex = nouveauIndex;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "consommationElectriciteList", isVariablePrivate = true)
    public List<ConsommationElectricite> getConsommationElectriciteList() {
        if (consommationElectriciteList == null || consommationElectriciteList.isEmpty()) {
            consommationElectriciteList = SQLite.select()
                    .from(ConsommationElectricite.class)
                    .where(ConsommationElectricite_Table.consommationelectricite_id.eq(id))
                    .queryList();
        }
        return consommationElectriciteList;
    }

    public void setConsommationElectriciteList(List<ConsommationElectricite> consommationElectriciteList) {
        this.consommationElectriciteList = consommationElectriciteList;
    }

    public void assoConsomElec(ConsommationElectricite electricite) {
        consommationElectricite = new ForeignKeyContainer<>(ConsommationElectricite.class);
        consommationElectricite.setModel(electricite);
        consommationElectricite.put(ConsommationElectricite_Table.id, electricite.id);

    }


    public void assoHabitant(Habitant habitant1) {
        habitant = new ForeignKeyContainer<>(Habitant.class);
        habitant.setModel(habitant1);
        habitant.put(Habitant_Table.id, habitant1.id);

    }

    public void assoLogement(Logement logement1) {
        logement = new ForeignKeyContainer<>(Logement.class);
        logement.setModel(logement1);
        logement.put(Logement_Table.id, logement1.id);

    }

    public void assoParamentre(Parametre parametre1) {
        parametre = new ForeignKeyContainer<>(Parametre.class);
        parametre.setModel(parametre1);
        parametre.put(Parametre_Table.id, parametre1.id);

    }

    public void assoMois(Mois mois1) {
        mois = new ForeignKeyContainer<>(Mois.class);
        mois.setModel(mois1);
        mois.put(Mois_Table.id, mois1.id);

    }


}
