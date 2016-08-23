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
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.Date;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class PersonnelRole extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "date_attrib")

    Date dateAttrib;
    @Column(name = "date_retrait")

    Date dateRetrait;

    @NotNull
    @Column(name = "etat")
    boolean etat;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "personnel_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Personnel> personnel;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "role_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Role> role;

    public PersonnelRole() {
    }

    public PersonnelRole(Integer id) {
        this.id = id;
    }

    public PersonnelRole(Integer id, Date dateAttrib, boolean etat) {
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

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public void assoRole(Role role1) {
        role = new ForeignKeyContainer<>(Role.class);
        role.setModel(role1);
        role.put(Role_Table.id, role1.id);

    }

    public void assoPersonnel(Personnel personnel1) {
        personnel = new ForeignKeyContainer<>(Personnel.class);
        personnel.setModel(personnel1);
        personnel.put(Personnel_Table.id, personnel1.id);

    }

}
