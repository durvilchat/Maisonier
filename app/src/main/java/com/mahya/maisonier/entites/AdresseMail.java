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
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

@ModelContainer
@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class AdresseMail extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mail", length = 255)
    String mail;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password", length = 255)
    String password;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "serveurmail_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<ServeurMail> serveurMail;

    public AdresseMail() {
    }

    public AdresseMail(Integer id) {
        this.id = id;
    }

    public AdresseMail(Integer id, String mail, String password) {
        this.id = id;
        this.mail = mail;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void assoServeurMail(ServeurMail serveurMail1) {
        serveurMail = new ForeignKeyContainer<>(ServeurMail.class);
        serveurMail.setModel(serveurMail1);
        serveurMail.put(ServeurMail_Table.id, serveurMail1.id);

    }

}
