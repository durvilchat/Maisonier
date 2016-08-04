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
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Privilege extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Size(max = 254)
    @Column(name = "description", length = 254)
    String description;
    @Size(max = 254)
    @Unique
    @Column(name = "nomprivilege", length = 254)
    String nomprivilege;

    List<PersonnelPrivilege> personnelPrivilegeList;

    List<Conflit> conflitList;

    List<Conflit> conflitList1;

    List<Privilege> privilegeList;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "hierarchie_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Privilege> hierarchie;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "privilege")
    List<RolePrivilege> rolePrivilegeList;

    public Privilege() {
    }

    public Privilege(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNomprivilege() {
        return nomprivilege;
    }

    public void setNomprivilege(String nomprivilege) {
        this.nomprivilege = nomprivilege;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "personnelPrivilegeList", isVariablePrivate = true)
    public List<PersonnelPrivilege> getPersonnelPrivilegeList() {
        if (personnelPrivilegeList == null || personnelPrivilegeList.isEmpty()) {
            personnelPrivilegeList = SQLite.select()
                    .from(PersonnelPrivilege.class)
                    .where(PersonnelPrivilege_Table.privilege_id.eq(id))
                    .queryList();
        }
        return personnelPrivilegeList;
    }

    public void setPersonnelPrivilegeList(List<PersonnelPrivilege> personnelPrivilegeList) {
        this.personnelPrivilegeList = personnelPrivilegeList;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "conflitList", isVariablePrivate = true)
    public List<Conflit> getConflitList() {
        if (conflitList == null || conflitList.isEmpty()) {
            conflitList = SQLite.select()
                    .from(Conflit.class)
                    .where(Conflit_Table.privilege1_id.eq(id))
                    .queryList();
        }
        return conflitList;
    }

    public void setConflitList(List<Conflit> conflitList) {
        this.conflitList = conflitList;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "conflitList1", isVariablePrivate = true)
    public List<Conflit> getConflitList1() {
        if (conflitList1 == null || conflitList1.isEmpty()) {
            conflitList1 = SQLite.select()
                    .from(Conflit.class)
                    .where(Conflit_Table.privilege1_id.eq(id))
                    .queryList();
        }
        return conflitList1;
    }

    public void setConflitList1(List<Conflit> conflitList1) {
        this.conflitList1 = conflitList1;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "privilegeList", isVariablePrivate = true)
    public List<Privilege> getPrivilegeList() {
        if (privilegeList == null || privilegeList.isEmpty()) {
            privilegeList = SQLite.select()
                    .from(Privilege.class)
                    .where(Privilege_Table.hierarchie_id.eq(id))
                    .queryList();
        }
        return privilegeList;
    }

    public void setPrivilegeList(List<Privilege> privilegeList) {
        this.privilegeList = privilegeList;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "rolePrivilegeList", isVariablePrivate = true)
    public List<RolePrivilege> getRolePrivilegeList() {
        if (rolePrivilegeList == null || rolePrivilegeList.isEmpty()) {
            rolePrivilegeList = SQLite.select()
                    .from(RolePrivilege.class)
                    .where(RolePrivilege_Table.privilege_id.eq(id))
                    .queryList();
        }
        return rolePrivilegeList;
    }

    public void setRolePrivilegeList(List<RolePrivilege> rolePrivilegeList) {
        this.rolePrivilegeList = rolePrivilegeList;
    }
}
