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
public class PersonnelPrivilege extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "date_attrib")

    Date dateAttrib;
    @Column(name = "date_retrait")

    Date dateRetrait;
    @Size(max = 254)
    @Column(name = "description", length = 254)
    String description;

    @NotNull
    @Column(name = "etat")
    boolean etat;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "personnel_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Personnel> personnel;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "privilege_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Privilege> privilege;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "role_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Role> role;

    public PersonnelPrivilege() {
    }

    public PersonnelPrivilege(Integer id) {
        this.id = id;
    }

    public PersonnelPrivilege(Integer id, Date dateAttrib, boolean etat) {
        this.id = id;
        this.dateAttrib = dateAttrib;
        this.etat = etat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateAttrib() {
        return dateAttrib;
    }

    public void setDateAttrib(Date dateAttrib) {
        this.dateAttrib = dateAttrib;
    }

    public Date getDateRetrait() {
        return dateRetrait;
    }

    public void setDateRetrait(Date dateRetrait) {
        this.dateRetrait = dateRetrait;
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


    public void assoPersonnel(Personnel personnel1) {
        personnel = new ForeignKeyContainer<>(Personnel.class);
        personnel.setModel(personnel1);
        personnel.put(Personnel_Table.id, personnel1.id);

    }

    public void assoPriv(Privilege privilege1) {
        privilege = new ForeignKeyContainer<>(Privilege.class);
        privilege.setModel(privilege1);
        privilege.put(Privilege_Table.id, privilege1.id);

    }

    public void assoRole(Role role1) {
        role = new ForeignKeyContainer<>(Role.class);
        role.setModel(role1);
        role.put(Role_Table.id, role1.id);

    }

}
