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
public class TypeCharge extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Unique
    @Column(name = "libelle", length = 255)
    String libelle;
    @Column(name = "montant")
    Double montant;
    List<Charge> chargeList;

    public TypeCharge() {
    }

    public TypeCharge(Integer id) {
        this.id = id;
    }

    public TypeCharge(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "chargeList", isVariablePrivate = true)
    public List<Charge> getChargeList() {
        if (chargeList == null || chargeList.isEmpty()) {
            chargeList = SQLite.select()
                    .from(Charge.class)
                    .where(Charge_Table.typecharge_id.eq(id))
                    .queryList();
        }
        return chargeList;
    }

    public void setChargeList(List<Charge> chargeList) {
        this.chargeList = chargeList;
    }
}
