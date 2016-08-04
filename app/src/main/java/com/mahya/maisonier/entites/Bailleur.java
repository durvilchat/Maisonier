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

@Table(database = Maisonier.class, useBooleanGetterSetters = true,
        uniqueColumnGroups = {@UniqueGroup(groupNumber = 1, uniqueConflict = ConflictAction.FAIL)})
public class Bailleur extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Column(name = "date_delivraison_cni")

    Date dateDelivraisonCni;
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

    @NotNull
    @Size(min = 1, max = 255)
    @Unique(unique = false, uniqueGroups = 1)
    @Column(name = "nom", length = 255)
    String nom;
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

    List<ContratBail> contratBailList;

    List<Depense> depenseList;

    List<Cite> citeList;


    public Bailleur() {
    }

    public Bailleur(Integer id) {
        this.id = id;
    }

    public Bailleur(Integer id, String genre, String nom, String tel1, String titre) {
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "contratBailList")
    public List<ContratBail> getContratBailList() {
        if (contratBailList == null || contratBailList.isEmpty()) {
            contratBailList = SQLite.select()
                    .from(ContratBail.class).where(ContratBail_Table.bailleur_id.eq(id))
                    .queryList();
        }
        return contratBailList;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "depenseList")
    public List<Depense> getDepenseList() {
        if (depenseList == null || depenseList.isEmpty()) {
            depenseList = SQLite.select()
                    .from(Depense.class).where(Depense_Table.bailleur_id.eq(id))
                    .queryList();
        }

        return depenseList;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "citeList")
    public List<Cite> getCiteList() {
        if (citeList == null || citeList.isEmpty()) {
            citeList = SQLite.select()
                    .from(Cite.class).where(Cite_Table.bailleur_id.eq(id))
                    .queryList();
        }

        return citeList;
    }


}
