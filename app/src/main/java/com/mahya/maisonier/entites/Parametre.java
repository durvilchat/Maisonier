/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahya.maisonier.entites;

import android.support.annotation.Size;

import com.mahya.maisonier.dataBase.Maisonier;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)

public class Parametre extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "cablage")
    double cablage;

    @NotNull
    @Column(name = "date_enregistrement")

    Date dateEnregistrement;
    @Column(name = "date_modification")

    Date dateModification;

    @NotNull
    @Column(name = "entretient_eau")
    double entretientEau;

    @NotNull
    @Column(name = "entretient_electricite")
    double entretientElectricite;

    @NotNull
    @Column(name = "etat")
    boolean etat;

    @NotNull
    @Column(name = "prix_unitaire_eau")
    double prixUnitaireEau;

    @NotNull
    @Column(name = "prix_unitaire_electricite")
    double prixUnitaireElectricite;
    @Size(max = 255)
    @Column(name = "repertoire_photo_batiment", length = 255)
    String repertoirePhotoBatiment;
    @Size(max = 255)
    @Column(name = "repertoire_photo_habitant", length = 255)
    String repertoirePhotoHabitant;
    @Size(max = 255)
    @Column(name = "repertoire_photo_logement", length = 255)
    String repertoirePhotoLogement;
    @Size(max = 255)
    @Column(name = "repertoire_photo_personnel", length = 255)
    String repertoirePhotoPersonnel;

    @NotNull
    @Column(name = "t_v_a")
    double tVA;

    @NotNull
    @Column(name = "taux_remise")
    double tauxRemise;

    List<Cable> cableList;

    List<ConsommationEau> consommationEauList;

    List<ConsommationElectricite> consommationElectriciteList;

    List<Remise> remiseList;

    public Parametre() {
    }

    public Parametre(Integer id) {
        this.id = id;
    }

    public Parametre(Integer id, double cablage, Date dateEnregistrement, double entretientEau, double entretientElectricite, boolean etat, double prixUnitaireEau, double prixUnitaireElectricite, double tVA, double tauxRemise) {
        this.id = id;
        this.cablage = cablage;
        this.dateEnregistrement = dateEnregistrement;
        this.entretientEau = entretientEau;
        this.entretientElectricite = entretientElectricite;
        this.etat = etat;
        this.prixUnitaireEau = prixUnitaireEau;
        this.prixUnitaireElectricite = prixUnitaireElectricite;
        this.tVA = tVA;
        this.tauxRemise = tauxRemise;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCablage() {
        return cablage;
    }

    public void setCablage(double cablage) {
        this.cablage = cablage;
    }

    public Date getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(Date dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public double getEntretientEau() {
        return entretientEau;
    }

    public void setEntretientEau(double entretientEau) {
        this.entretientEau = entretientEau;
    }

    public double getEntretientElectricite() {
        return entretientElectricite;
    }

    public void setEntretientElectricite(double entretientElectricite) {
        this.entretientElectricite = entretientElectricite;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public double getPrixUnitaireEau() {
        return prixUnitaireEau;
    }

    public void setPrixUnitaireEau(double prixUnitaireEau) {
        this.prixUnitaireEau = prixUnitaireEau;
    }

    public double getPrixUnitaireElectricite() {
        return prixUnitaireElectricite;
    }

    public void setPrixUnitaireElectricite(double prixUnitaireElectricite) {
        this.prixUnitaireElectricite = prixUnitaireElectricite;
    }

    public String getRepertoirePhotoBatiment() {
        return repertoirePhotoBatiment;
    }

    public void setRepertoirePhotoBatiment(String repertoirePhotoBatiment) {
        this.repertoirePhotoBatiment = repertoirePhotoBatiment;
    }

    public String getRepertoirePhotoHabitant() {
        return repertoirePhotoHabitant;
    }

    public void setRepertoirePhotoHabitant(String repertoirePhotoHabitant) {
        this.repertoirePhotoHabitant = repertoirePhotoHabitant;
    }

    public String getRepertoirePhotoLogement() {
        return repertoirePhotoLogement;
    }

    public void setRepertoirePhotoLogement(String repertoirePhotoLogement) {
        this.repertoirePhotoLogement = repertoirePhotoLogement;
    }

    public String getRepertoirePhotoPersonnel() {
        return repertoirePhotoPersonnel;
    }

    public void setRepertoirePhotoPersonnel(String repertoirePhotoPersonnel) {
        this.repertoirePhotoPersonnel = repertoirePhotoPersonnel;
    }

    public double getTVA() {
        return tVA;
    }


    public void setTVA(double tVA) {
        this.tVA = tVA;
    }

    public double getTauxRemise() {
        return tauxRemise;
    }

    public void setTauxRemise(double tauxRemise) {
        this.tauxRemise = tauxRemise;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "cableList", isVariablePrivate = true)
    public List<Cable> getCableList() {
        if (cableList == null || cableList.isEmpty()) {
            cableList = SQLite.select()
                    .from(Cable.class)
                    .where(Cable_Table.parametre_id.eq(id))
                    .queryList();
        }
        return cableList;
    }

    public void setCableList(List<Cable> cableList) {
        this.cableList = cableList;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "consommationEauList", isVariablePrivate = true)
    public List<ConsommationEau> getConsommationEauList() {
        if (consommationEauList == null || consommationEauList.isEmpty()) {
            consommationEauList = SQLite.select()
                    .from(ConsommationEau.class)
                    .where(ConsommationEau_Table.parametre_id.eq(id))
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
                    .where(ConsommationElectricite_Table.parametre_id.eq(id))
                    .queryList();
        }
        return consommationElectriciteList;
    }

    public void setConsommationElectriciteList(List<ConsommationElectricite> consommationElectriciteList) {
        this.consommationElectriciteList = consommationElectriciteList;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "remiseList", isVariablePrivate = true)
    public List<Remise> getRemiseList() {
        if (remiseList == null || remiseList.isEmpty()) {
            remiseList = SQLite.select()
                    .from(Remise.class)
                    .where(Remise_Table.parametre_id.eq(id))
                    .queryList();
        }
        return remiseList;
    }

    public void setRemiseList(List<Remise> remiseList) {
        this.remiseList = remiseList;
    }


}
