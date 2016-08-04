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
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.annotation.UniqueGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true, uniqueColumnGroups = {@UniqueGroup(groupNumber = 1, uniqueConflict = ConflictAction.FAIL)})
public class Habitant extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Column(name = "date_delivraison_cni")

    Date dateDelivraisonCni;
    @Column(name = "date_naissance")

    Date dateNaissance;
    @Size(max = 255)
    @Column(name = "email1", length = 255)
    String email1;
    @Size(max = 255)
    @Column(name = "email2", length = 255)
    String email2;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "genre", length = 255)
    String genre;
    @Size(max = 255)
    @Column(name = "lieu_delivraison_cni", length = 255)
    String lieuDelivraisonCni;
    @Size(max = 255)
    @Column(name = "lieu_naissance", length = 255)
    String lieuNaissance;

    @NotNull
    @Size(min = 1, max = 255)
    @Unique(unique = false, uniqueGroups = 1)
    @Column(name = "nom", length = 255)
    String nom;
    @Size(max = 255)
    @Column(name = "nom_de_la_mere", length = 255)
    String nomDeLaMere;
    @Size(max = 255)
    @Column(name = "nom_du_pere", length = 255)
    String nomDuPere;
    @Size(max = 255)
    @Column(name = "numero_c_n_i", length = 255)
    String numeroCNI;
    @Size(max = 255)
    @Column(name = "photo", length = 255)
    String photo;
    @Size(max = 255)
    @Unique(unique = false, uniqueGroups = 1)
    @Column(name = "prenom", length = 255)
    String prenom;
    @Size(max = 255)
    @Column(name = "profession", length = 255)
    String profession;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tel1", length = 255)
    String tel1;
    @Size(max = 255)
    @Column(name = "tel2", length = 255)
    String tel2;
    @Size(max = 255)
    @Column(name = "tel3", length = 255)
    String tel3;
    @Size(max = 255)
    @Column(name = "tel4", length = 255)
    String tel4;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "titre", length = 255)
    String titre;

    List<Occupation> occupationList;

    List<Facture> factureList;

    List<ConsommationEau> consommationEauList;

    List<ConsommationElectricite> consommationElectriciteList;

    public Habitant() {
    }

    public Habitant(Integer id) {
        this.id = id;
    }

    public Habitant(Integer id, String genre, String nom, String tel1, String titre) {
        this.id = id;
        this.genre = genre;
        this.nom = nom;
        this.tel1 = tel1;
        this.titre = titre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateDelivraisonCni() {
        return dateDelivraisonCni;
    }

    public void setDateDelivraisonCni(Date dateDelivraisonCni) {
        this.dateDelivraisonCni = dateDelivraisonCni;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLieuDelivraisonCni() {
        return lieuDelivraisonCni;
    }

    public void setLieuDelivraisonCni(String lieuDelivraisonCni) {
        this.lieuDelivraisonCni = lieuDelivraisonCni;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomDeLaMere() {
        return nomDeLaMere;
    }

    public void setNomDeLaMere(String nomDeLaMere) {
        this.nomDeLaMere = nomDeLaMere;
    }

    public String getNomDuPere() {
        return nomDuPere;
    }

    public void setNomDuPere(String nomDuPere) {
        this.nomDuPere = nomDuPere;
    }

    public String getNumeroCNI() {
        return numeroCNI;
    }

    public void setNumeroCNI(String numeroCNI) {
        this.numeroCNI = numeroCNI;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getTel4() {
        return tel4;
    }

    public void setTel4(String tel4) {
        this.tel4 = tel4;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "occupationList", isVariablePrivate = true)
    public List<Occupation> getOccupationList() {
        if (occupationList == null || occupationList.isEmpty()) {
            occupationList = SQLite.select()
                    .from(Occupation.class)
                    .where(Occupation_Table.habitant_id.eq(id))
                    .queryList();
        }
        return occupationList;
    }

    public void setOccupationList(List<Occupation> occupationList) {

        this.occupationList = occupationList;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "factureList", isVariablePrivate = true)
    public List<Facture> getFactureList() {
        if (factureList == null || factureList.isEmpty()) {
            factureList = SQLite.select()
                    .from(Facture.class)
                    .where(Facture_Table.habitant_id.eq(id))
                    .queryList();
        }
        return factureList;
    }

    public void setFactureList(List<Facture> factureList) {
        this.factureList = factureList;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "consommationEauList", isVariablePrivate = true)
    public List<ConsommationEau> getConsommationEauList() {
        if (consommationEauList == null || consommationEauList.isEmpty()) {
            consommationEauList = SQLite.select()
                    .from(ConsommationEau.class)
                    .where(ConsommationEau_Table.habitant_id.eq(id))
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
                    .where(ConsommationElectricite_Table.habitant_id.eq(id))
                    .queryList();
        }
        return consommationElectriciteList;
    }

    public void setConsommationElectriciteList(List<ConsommationElectricite> consommationElectriciteList) {
        this.consommationElectriciteList = consommationElectriciteList;
    }

}
