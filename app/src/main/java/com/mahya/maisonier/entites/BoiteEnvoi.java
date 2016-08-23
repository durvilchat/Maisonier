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
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.Date;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class BoiteEnvoi extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Column(name = "date_reception")

    Date dateReception;
    @Size(max = 255)
    @Column(name = "numero_destinataire", length = 255)
    String numeroDestinataire;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "messagesortant_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<MessageSortant> messageSortant;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "telephone_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Telephone> telephone;

    public BoiteEnvoi() {
    }

    public BoiteEnvoi(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateReception() {
        return dateReception;
    }

    public void setDateReception(Date dateReception) {
        this.dateReception = dateReception;
    }

    public String getNumeroDestinataire() {
        return numeroDestinataire;
    }

    public void setNumeroDestinataire(String numeroDestinataire) {
        this.numeroDestinataire = numeroDestinataire;
    }


    public void assoMessaSort(MessageSortant messageSortant1) {
        messageSortant = new ForeignKeyContainer<>(MessageSortant.class);
        messageSortant.setModel(messageSortant1);
        messageSortant.put(Role_Table.id, messageSortant1.id);

    }


    public void assoTelephone(Telephone telephone1) {
        telephone = new ForeignKeyContainer<>(Telephone.class);
        telephone.setModel(telephone1);
        telephone.put(Telephone_Table.id, telephone1.id);

    }


}
