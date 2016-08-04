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
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class TypeCompte extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Size(max = 255)
    @Column(name = "description", length = 255)
    String description;

    @NotNull
    @Unique
    @Size(min = 1, max = 255)
    @Column(name = "libelle", length = 255)
    String libelle;

    List<Compte> compteList;

    public TypeCompte() {
    }

    public TypeCompte(Integer id) {
        this.id = id;
    }

    public TypeCompte(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "compteList", isVariablePrivate = true)
    public List<Compte> getCompteList() {
        if (compteList == null || compteList.isEmpty()) {
            compteList = SQLite.select()
                    .from(Compte.class)
                    .where(Compte_Table.typecompte_id.eq(id))
                    .queryList();
        }
        return compteList;
    }

    public void setCompteList(List<Compte> compteList) {
        this.compteList = compteList;
    }
}
