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
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.Date;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Mouchard extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "action", length = 255)
    String action;

    @NotNull
    @Column(name = "date_action")

    Date dateAction;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "utilisateur_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<CompteUtilisateur> utilisateur;

    public Mouchard() {
    }

    public Mouchard(Integer id) {
        this.id = id;
    }

    public Mouchard(Integer id, String action, Date dateAction) {
        this.id = id;
        this.action = action;
        this.dateAction = dateAction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDateAction() {
        return dateAction;
    }

    public void setDateAction(Date dateAction) {
        this.dateAction = dateAction;
    }

    public void asso(CompteUtilisateur utilisateur1) {
        utilisateur = new ForeignKeyContainer<>(CompteUtilisateur.class);
        utilisateur.setModel(utilisateur1);
        utilisateur.put(CompteUtilisateur_Table.id, utilisateur1.id);

    }


}
