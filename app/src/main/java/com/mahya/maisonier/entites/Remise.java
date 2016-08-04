/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahya.maisonier.entites;

import com.mahya.maisonier.dataBase.Maisonier;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.Date;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)

public class Remise extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "date_attribution")

    Date dateAttribution;

    @Column
    @Unique
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "loyer_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Loyer> loyer;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "parametre_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Parametre> parametre;

    public Remise() {
    }

    public Remise(Integer id) {
        this.id = id;
    }

    public Remise(Integer id, Date dateAttribution) {
        this.id = id;
        this.dateAttribution = dateAttribution;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateAttribution() {
        return dateAttribution;
    }

    public void setDateAttribution(Date dateAttribution) {
        this.dateAttribution = dateAttribution;
    }


    public void assoParamentre(Parametre parametre1) {
        parametre = new ForeignKeyContainer<>(Parametre.class);
        parametre.setModel(parametre1);
        parametre.put(Parametre_Table.id, parametre1.id);

    }

    public void assoLoyer(Loyer loyer1) {
        loyer = new ForeignKeyContainer<>(Loyer.class);
        loyer.setModel(loyer1);
        loyer.put(Parametre_Table.id, loyer1.id);

    }
}
