/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahya.maisonier.entites;

import android.support.annotation.Size;

import com.mahya.maisonier.dataBase.Maisonier;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class ServeurMail extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Size(max = 255)
    @Column(name = "hote", length = 255)
    String hote;
    @Unique
    @Size(max = 255)
    @Column(name = "nom", length = 255)
    String nom;

    List<AdresseMail> adresseMailList;

    public ServeurMail() {
    }

    public ServeurMail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHote() {
        return hote;
    }

    public void setHote(String hote) {
        this.hote = hote;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "adresseMailList", isVariablePrivate = true)
    public List<AdresseMail> getAdresseMailList() {
        if (adresseMailList == null || adresseMailList.isEmpty()) {
            adresseMailList = SQLite.select()
                    .from(AdresseMail.class)
                    .where(AdresseMail_Table.serveurmail_id.eq(id))
                    .queryList();
        }
        return adresseMailList;
    }

    public void setAdresseMailList(List<AdresseMail> adresseMailList) {
        this.adresseMailList = adresseMailList;
    }
}
