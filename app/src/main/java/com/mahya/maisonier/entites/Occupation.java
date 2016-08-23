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
import com.raizlabs.android.dbflow.annotation.OneToMany;
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
public class Occupation extends BaseModel {


    public static List<Occupation> occupations = new ArrayList<>();
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "date_entree")

    Date dateEntree;
    @Column(name = "date_sortie")

    Date dateSortie;
    @Size(max = 255)
    @Column(name = "description", length = 255)
    String description;

    @NotNull
    @Column(name = "etat")
    boolean etat;
    @Column(name = "forfait_eau")
    Boolean forfaitEau;
    @Column(name = "forfait_electricte")
    Boolean forfaitElectricte;

    @NotNull
    @Column(name = "loyer_base")
    double loyerBase;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mode_paiement", length = 255)
    String modePaiement;

    @NotNull
    @Column(name = "paie_cable")
    boolean paieCable;

    @NotNull
    @Column(name = "paie_eau")
    boolean paieEau;

    @NotNull
    @Column(name = "paie_electricite")
    boolean paieElectricite;

    List<Penalite> penaliteList;


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

    List<Depot> depotList;

    List<Cable> cableList;

    List<Loyer> loyerList;

    List<Caution> cautionList;

    List<Charge> chargeList;

    List<ContratBail> contratBailList;

    List<Compte> compteList;

    public Occupation() {
    }

    public Occupation(Integer id) {
        this.id = id;
    }

    public Occupation(Integer id, Date dateEntree, boolean etat, double loyerBase, String modePaiement, boolean paieCable, boolean paieEau, boolean paieElectricite) {
        this.id = id;
        this.dateEntree = dateEntree;
        this.etat = etat;
        this.loyerBase = loyerBase;
        this.modePaiement = modePaiement;
        this.paieCable = paieCable;
        this.paieEau = paieEau;
        this.paieElectricite = paieElectricite;
    }

    public static List<Occupation> findAll() {
        return SQLite.select().from(Occupation.class).queryList();
    }

    public static List<Occupation> getInitData(int size) {
        List<Occupation> Occupation = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Occupation.add(createRandomPerson());
        }
        return Occupation;
    }

    public static Occupation createRandomPerson() {
        Occupation Occupation = null;
        try {
            Occupation = occupations.get(0);
            occupations.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return Occupation;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(Date dateEntree) {
        this.dateEntree = dateEntree;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Boolean isForfaitEau() {
        return forfaitEau;
    }

    public double getLoyerBase() {
        return loyerBase;
    }

    public void setLoyerBase(double loyerBase) {
        this.loyerBase = loyerBase;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public Boolean isForfaitElectricte() {
        return forfaitElectricte;
    }

    public boolean isPaieCable() {
        return paieCable;
    }

    public void setPaieCable(boolean paieCable) {
        this.paieCable = paieCable;
    }

    public boolean isPaieEau() {
        return paieEau;
    }

    public void setPaieEau(boolean paieEau) {
        this.paieEau = paieEau;
    }

    public boolean isPaieElectricite() {
        return paieElectricite;
    }

    public void setPaieElectricite(boolean paieElectricite) {
        this.paieElectricite = paieElectricite;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "penaliteList", isVariablePrivate = true)
    public List<Penalite> getPenaliteList() {
        if (penaliteList == null || penaliteList.isEmpty()) {
            penaliteList = SQLite.select()
                    .from(Penalite.class)
                    .where(Penalite_Table.occupation_id.eq(id))
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
                    .where(Depot_Table.occupation_id.eq(id))
                    .queryList();
        }
        return depotList;
    }

    public void setDepotList(List<Depot> depotList) {
        this.depotList = depotList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "cableList", isVariablePrivate = true)
    public List<Cable> getCableList() {
        if (cableList == null || cableList.isEmpty()) {
            cableList = SQLite.select()
                    .from(Cable.class)
                    .where(Cable_Table.occupation_id.eq(id))
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
                    .where(Loyer_Table.occupation_id.eq(id))
                    .queryList();
        }
        return loyerList;
    }

    public void setLoyerList(List<Loyer> loyerList) {
        this.loyerList = loyerList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "cautionList", isVariablePrivate = true)
    public List<Caution> getCautionList() {
        if (cautionList == null || cautionList.isEmpty()) {
            cautionList = SQLite.select()
                    .from(Caution.class)
                    .where(Caution_Table.occupation_id.eq(id))
                    .queryList();
        }
        return cautionList;
    }

    public void setCautionList(List<Caution> cautionList) {
        this.cautionList = cautionList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "chargeList", isVariablePrivate = true)
    public List<Charge> getChargeList() {
        if (chargeList == null || chargeList.isEmpty()) {
            chargeList = SQLite.select()
                    .from(Charge.class)
                    .where(Charge_Table.occupation_id.eq(id))
                    .queryList();
        }
        return chargeList;
    }

    public void setChargeList(List<Charge> chargeList) {
        this.chargeList = chargeList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "contratBailList", isVariablePrivate = true)
    public List<ContratBail> getContratBailList() {
        if (contratBailList == null || contratBailList.isEmpty()) {
            contratBailList = SQLite.select()
                    .from(ContratBail.class)
                    .where(ContratBail_Table.occupation_id.eq(id))
                    .queryList();
        }
        return contratBailList;
    }

    public void setContratBailList(List<ContratBail> contratBailList) {
        this.contratBailList = contratBailList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "compteList", isVariablePrivate = true)
    public List<Compte> getCompteList() {
        if (compteList == null || compteList.isEmpty()) {
            compteList = SQLite.select()
                    .from(Compte.class)
                    .where(Compte_Table.occupation_id.eq(id))
                    .queryList();
        }
        return compteList;
    }

    public void setCompteList(List<Compte> compteList) {
        this.compteList = compteList;
    }

    public void assoLogement(Logement logement1) {
        logement = new ForeignKeyContainer<>(Logement.class);
        logement.setModel(logement1);
        logement.put(Logement_Table.id, logement1.id);

    }

    public void assoHabitant(Habitant habitant1) {
        habitant = new ForeignKeyContainer<>(Habitant.class);
        habitant.setModel(habitant1);
        habitant.put(Habitant_Table.id, habitant1.id);

    }

    public Boolean getForfaitEau() {
        return forfaitEau;
    }

    public void setForfaitEau(Boolean forfaitEau) {
        this.forfaitEau = forfaitEau;
    }

    public ForeignKeyContainer<Logement> getLogement() {
        return logement;
    }

    public ForeignKeyContainer<Habitant> getHabitant() {
        return habitant;
    }

    public Boolean getForfaitElectricte() {
        return forfaitElectricte;
    }

    public void setForfaitElectricte(Boolean forfaitElectricte) {
        this.forfaitElectricte = forfaitElectricte;
    }

    @Override
    public String toString() {
        return getLogement().load().getReference();
    }
}
