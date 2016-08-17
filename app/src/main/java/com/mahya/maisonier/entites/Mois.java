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
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.annotation.UniqueGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.ArrayList;
import java.util.List;

@ModelContainer
@Table(database = Maisonier.class, useBooleanGetterSetters = true, uniqueColumnGroups = {@UniqueGroup(groupNumber = 1, uniqueConflict = ConflictAction.FAIL)})
public class Mois extends BaseModel {


    public static List<Mois> moises = new ArrayList<>();
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Size(max = 255)
    @Unique(unique = false, uniqueGroups = 1)
    @Column(name = "mois", length = 255)
    String mois;

    Integer moisId;

    List<Penalite> penaliteList;

    List<Depot> depotList;

    List<Facture> factureList;

    List<Cable> cableList;

    List<Loyer> loyerList;

    List<ConsommationEau> consommationEauList;

    List<ConsommationElectricite> consommationElectriciteList;


    List<Charge> chargeList;

    List<Depense> depenseList;
    @Column
    @Unique(unique = false, uniqueGroups = 1)
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "annee_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Annee> annee;

    public Mois() {
    }

    public Mois(Integer id) {
        this.id = id;
    }

    public static List<Mois> getMoises() {
        return moises;
    }

    public static void setMoises(List<Mois> moises) {
        Mois.moises = moises;
    }

    public static List<Mois> getInitData(int size) {
        List<Mois> articleBails = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            articleBails.add(createRandomPerson());
        }
        return articleBails;
    }

    public static Mois createRandomPerson() {
        Mois batiment = null;
        try {
            batiment = moises.get(0);
            moises.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return batiment;

    }

    public static List<Mois> findAll() {
        return SQLite.select().from(Mois.class).queryList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "penaliteList", isVariablePrivate = true)
    public List<Penalite> getPenaliteList() {
        if (penaliteList == null || penaliteList.isEmpty()) {
            penaliteList = SQLite.select()
                    .from(Penalite.class)
                    .where(Penalite_Table.mois_id.eq(id))
                    .queryList();
        }
        return penaliteList;
    }

    public void setPenaliteList(List<Penalite> penaliteList) {
        this.penaliteList = penaliteList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "depotList", isVariablePrivate = true)
    public List<Depot> getDepotList() {
        if (depotList == null || depotList.isEmpty()) {
            depotList = SQLite.select()
                    .from(Depot.class)
                    .where(Depot_Table.mois_id.eq(id))
                    .queryList();
        }
        return depotList;
    }

    public void setDepotList(List<Depot> depotList) {
        this.depotList = depotList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "factureList", isVariablePrivate = true)
    public List<Facture> getFactureList() {
        if (factureList == null || factureList.isEmpty()) {
            factureList = SQLite.select()
                    .from(Facture.class)
                    .where(Facture_Table.mois_id.eq(id))
                    .queryList();
        }
        return factureList;
    }

    public void setFactureList(List<Facture> factureList) {
        this.factureList = factureList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "cableList", isVariablePrivate = true)
    public List<Cable> getCableList() {
        if (cableList == null || cableList.isEmpty()) {
            cableList = SQLite.select()
                    .from(Cable.class)
                    .where(Cable_Table.mois_id.eq(id))
                    .queryList();
        }
        return cableList;
    }

    public void setCableList(List<Cable> cableList) {
        this.cableList = cableList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "loyerList", isVariablePrivate = true)
    public List<Loyer> getLoyerList() {
        if (loyerList == null || loyerList.isEmpty()) {
            loyerList = SQLite.select()
                    .from(Loyer.class)
                    .where(Loyer_Table.mois_id.eq(id))
                    .queryList();
        }
        return loyerList;
    }

    public void setLoyerList(List<Loyer> loyerList) {
        this.loyerList = loyerList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "consommationEauList", isVariablePrivate = true)
    public List<ConsommationEau> getConsommationEauList() {
        if (consommationEauList == null || consommationEauList.isEmpty()) {
            consommationEauList = SQLite.select()
                    .from(ConsommationEau.class)
                    .where(ConsommationEau_Table.mois_id.eq(id))
                    .queryList();
        }
        return consommationEauList;
    }

    public void setConsommationEauList(List<ConsommationEau> consommationEauList) {
        this.consommationEauList = consommationEauList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "consommationElectriciteList", isVariablePrivate = true)
    public List<ConsommationElectricite> getConsommationElectriciteList() {
        if (consommationElectriciteList == null || consommationElectriciteList.isEmpty()) {
            consommationElectriciteList = SQLite.select()
                    .from(ConsommationElectricite.class)
                    .where(ConsommationElectricite_Table.mois_id.eq(id))
                    .queryList();
        }
        return consommationElectriciteList;
    }

    public void setConsommationElectriciteList(List<ConsommationElectricite> consommationElectriciteList) {
        this.consommationElectriciteList = consommationElectriciteList;
    }

    public Integer getMoisId() {
        return moisId;
    }

    public void setMoisId(Integer moisId) {
        this.moisId = moisId;
    }

    public ForeignKeyContainer<Annee> getAnnee() {
        return annee;
    }

    public void setAnnee(ForeignKeyContainer<Annee> annee) {
        this.annee = annee;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "chargeList", isVariablePrivate = true)
    public List<Charge> getChargeList() {
        if (chargeList == null || chargeList.isEmpty()) {
            chargeList = SQLite.select()
                    .from(Charge.class)
                    .where(Charge_Table.mois_id.eq(id))
                    .queryList();
        }
        return chargeList;
    }

    public void setChargeList(List<Charge> chargeList) {
        this.chargeList = chargeList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "depenseList", isVariablePrivate = true)
    public List<Depense> getDepenseList() {
        if (depenseList == null || depenseList.isEmpty()) {
            depenseList = SQLite.select()
                    .from(Depense.class)
                    .where(Depense_Table.mois_id.eq(id))
                    .queryList();
        }
        return depenseList;
    }

    public void setDepenseList(List<Depense> depenseList) {
        this.depenseList = depenseList;
    }

    public void assoAnnee(Annee annee1) {
        annee = new ForeignKeyContainer<>(Annee.class);
        annee.setModel(annee1);
        annee.put(Annee_Table.id, annee1.id);

    }

    @Override
    public String toString() {
        return mois;
    }
}
