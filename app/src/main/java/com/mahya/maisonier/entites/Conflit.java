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
public class Conflit extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "privilege1_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Privilege> privilege1;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "privilege2_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Privilege> privilege2;

    public Conflit() {
    }

    public Conflit(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void assoPrivilege1(Privilege privilege) {
        privilege1 = new ForeignKeyContainer<>(Privilege.class);
        privilege1.setModel(privilege);
        privilege1.put(Privilege_Table.id, privilege.id);

    }

    public void assoPrivilege2(Privilege privilege) {
        privilege2 = new ForeignKeyContainer<>(Privilege.class);
        privilege2.setModel(privilege);
        privilege2.put(Privilege_Table.id, privilege.id);

    }
}
