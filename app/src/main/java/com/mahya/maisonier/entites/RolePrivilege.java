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
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class RolePrivilege extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "privilege_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Privilege> privilege;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "role_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Role> role;


    public RolePrivilege() {
    }

    public RolePrivilege(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void asso(Privilege privilege1) {
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
