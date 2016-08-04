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

import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Poste extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Size(max = 254)
    @Column(name = "description", length = 254)
    String description;

    @NotNull
    @Column(name = "etat")
    boolean etat;

    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "libelle", length = 254)
    String libelle;

    List<Personnel> personnelList;

    public Poste() {
    }

    public Poste(Integer id) {
        this.id = id;
    }

    public Poste(Integer id, boolean etat, String libelle) {
        this.id = id;
        this.etat = etat;
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

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "personnelList", isVariablePrivate = true)
    public List<Personnel> getPersonnelList() {
        if (personnelList == null || personnelList.isEmpty()) {
            personnelList = SQLite.select()
                    .from(Personnel.class)
                    .where(Personnel_Table.poste_id.eq(id))
                    .queryList();
        }
        return personnelList;
    }

    public void setPersonnelList(List<Personnel> personnelList) {
        this.personnelList = personnelList;
    }
}
