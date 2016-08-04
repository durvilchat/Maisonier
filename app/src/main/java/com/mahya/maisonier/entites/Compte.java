/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahya.maisonier.entites;

import com.mahya.maisonier.dataBase.Maisonier;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.annotation.UniqueGroup;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.Date;

@Table(database = Maisonier.class, useBooleanGetterSetters = true, uniqueColumnGroups = {@UniqueGroup(groupNumber = 1, uniqueConflict = ConflictAction.FAIL)})
public class Compte extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Column(name = "dateoperaion")

    Date dateoperaion;

    @NotNull
    @Column(name = "solde")
    double solde;

    @Column
    @Unique(unique = false, uniqueGroups = 1)
    @ForeignKey(references = {@ForeignKeyReference(columnName = "occupation_id",
            columnType = Integer.class,
            foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Occupation> occupation;

    @Column
    @Unique(unique = false, uniqueGroups = 1)
    @ForeignKey(references = {@ForeignKeyReference(columnName = "typecompte_id",
            columnType = Integer.class,
            foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<TypeCompte> typeCompte;

    public Compte() {
    }

    public Compte(Integer id) {
        this.id = id;
    }

    public Compte(Integer id, double solde) {
        this.id = id;
        this.solde = solde;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateoperaion() {
        return dateoperaion;
    }

    public void setDateoperaion(Date dateoperaion) {
        this.dateoperaion = dateoperaion;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public void assoOccupation(Occupation occupation1) {
        occupation = new ForeignKeyContainer<>(Occupation.class);
        occupation.setModel(occupation1);
        occupation.put(Occupation_Table.id, occupation1.id);

    }

}
