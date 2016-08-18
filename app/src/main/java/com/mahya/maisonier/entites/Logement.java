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
public class Logement extends BaseModel {
    public static List<Logement> logements = new ArrayList<>();

    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;


    @Column(name = "datecreation")
    Date datecreation;
    @Size(max = 255)
    @Column(name = "description", length = 255)
    String description;

    @NotNull
    @Column(name = "etat")
    boolean etat;

    @NotNull
    @Column(name = "prix_max")
    double prixMax;

    @NotNull
    @Column(name = "prix_min")
    double prixMin;
    @Size(max = 255)
    @Column(name = "reference", length = 255)
    String reference;

    List<Occupation> occupationList;

    List<PhotoLogement> photoLogementList;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "batiment_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Batiment> batiment;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "typelogement_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<TypeLogement> typeLogement;

    List<ConsommationEau> consommationEauList;

    List<ConsommationElectricite> consommationElectriciteList;

    List<CaracteristiqueLogement> caracteristiqueLogementList;

    public Logement() {
    }

    public Logement(Integer id) {
        this.id = id;
    }

    public Logement(Integer id, Date datecreation, boolean etat, double prixMax, double prixMin) {
        this.id = id;
        this.datecreation = datecreation;
        this.etat = etat;
        this.prixMax = prixMax;
        this.prixMin = prixMin;
    }

    public static List<Logement> findAll() {
        return SQLite.select().from(Logement.class).queryList();
    }

    public static List<Logement> getInitData(int size) {
        List<Logement> Logement = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Logement.add(createRandomPerson());
        }
        return Logement;
    }

    public static Logement createRandomPerson() {
        Logement Logement = null;
        try {
            Logement = logements.get(0);
            logements.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return Logement;

    }

    public static List<Logement> getLogements() {
        return logements;
    }

    public ForeignKeyContainer<Batiment> getBatiment() {
        return batiment;
    }

    public ForeignKeyContainer<TypeLogement> getTypeLogement() {
        return typeLogement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
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

    public double getPrixMax() {
        return prixMax;
    }

    public void setPrixMax(double prixMax) {
        this.prixMax = prixMax;
    }

    public double getPrixMin() {
        return prixMin;
    }

    public void setPrixMin(double prixMin) {
        this.prixMin = prixMin;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "occupationList", isVariablePrivate = true)
    public List<Occupation> getOccupationList() {
        if (occupationList == null || occupationList.isEmpty()) {
            occupationList = SQLite.select()
                    .from(Occupation.class)
                    .where(Occupation_Table.logement_id.eq(id))
                    .queryList();
        }
        return occupationList;
    }

    public void setOccupationList(List<Occupation> occupationList) {
        this.occupationList = occupationList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "photoLogementList", isVariablePrivate = true)
    public List<PhotoLogement> getPhotoLogementList() {
        if (photoLogementList == null || photoLogementList.isEmpty()) {
            photoLogementList = SQLite.select()
                    .from(PhotoLogement.class)
                    .where(PhotoLogement_Table.logement_id.eq(id))
                    .queryList();
        }
        return photoLogementList;
    }

    public void setPhotoLogementList(List<PhotoLogement> photoLogementList) {

        this.photoLogementList = photoLogementList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "consommationEauList", isVariablePrivate = true)
    public List<ConsommationEau> getConsommationEauList() {
        if (consommationEauList == null || consommationEauList.isEmpty()) {
            consommationEauList = SQLite.select()
                    .from(ConsommationEau.class)
                    .where(ConsommationEau_Table.logement_id.eq(id))
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
                    .where(ConsommationElectricite_Table.logement_id.eq(id))
                    .queryList();
        }
        return consommationElectriciteList;
    }

    public void setConsommationElectriciteList(List<ConsommationElectricite> consommationElectriciteList) {
        this.consommationElectriciteList = consommationElectriciteList;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "caracteristiqueLogementList", isVariablePrivate = true)
    public List<CaracteristiqueLogement> getCaracteristiqueLogementList() {
        if (caracteristiqueLogementList == null || caracteristiqueLogementList.isEmpty()) {
            caracteristiqueLogementList = SQLite.select()
                    .from(CaracteristiqueLogement.class)
                    .where(CaracteristiqueLogement_Table.logement_id.eq(id))
                    .queryList();
        }
        return caracteristiqueLogementList;
    }

    public void setCaracteristiqueLogementList(List<CaracteristiqueLogement> caracteristiqueLogementList) {
        this.caracteristiqueLogementList = caracteristiqueLogementList;
    }

    public void assoBatiment(Batiment batiment1) {
        batiment = new ForeignKeyContainer<>(Batiment.class);
        batiment.setModel(batiment1);
        batiment.put(Batiment_Table.id, batiment1.id);

    }

    public void assoTypeLogement(TypeLogement typeLogement1) {
        typeLogement = new ForeignKeyContainer<>(TypeLogement.class);
        typeLogement.setModel(typeLogement1);
        typeLogement.put(TypeLogement_Table.id, typeLogement1.id);

    }

    @Override
    public String toString() {
        return reference;
    }
}
