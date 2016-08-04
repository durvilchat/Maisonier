/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahya.maisonier.entites;


import android.support.annotation.Size;

import com.mahya.maisonier.dataBase.Maisonier;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Role extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "etat")
    boolean etat;
    @Size(max = 254)
    @Column(name = "nom", length = 254)
    String nom;

    List<PersonnelRole> personnelRoleList;

    List<PersonnelPrivilege> personnelPrivilegeList;

    List<RolePrivilege> rolePrivilegeList;

    public Role() {
    }

    public Role(Integer id) {
        this.id = id;
    }

    public Role(Integer id, boolean etat) {
        this.id = id;
        this.etat = etat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "personnelRoleList", isVariablePrivate = true)
    public List<PersonnelRole> getPersonnelRoleList() {
        if (personnelRoleList == null || personnelRoleList.isEmpty()) {
            personnelRoleList = SQLite.select()
                    .from(PersonnelRole.class)
                    .where(PersonnelRole_Table.role_id.eq(id))
                    .queryList();
        }
        return personnelRoleList;
    }

    public void setPersonnelRoleList(List<PersonnelRole> personnelRoleList) {
        this.personnelRoleList = personnelRoleList;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "personnelPrivilegeList", isVariablePrivate = true)
    public List<PersonnelPrivilege> getPersonnelPrivilegeList() {
        if (personnelPrivilegeList == null || personnelPrivilegeList.isEmpty()) {
            personnelPrivilegeList = SQLite.select()
                    .from(PersonnelPrivilege.class)
                    .where(PersonnelPrivilege_Table.role_id.eq(id))
                    .queryList();
        }

        return personnelPrivilegeList;
    }

    public void setPersonnelPrivilegeList(List<PersonnelPrivilege> personnelPrivilegeList) {
        this.personnelPrivilegeList = personnelPrivilegeList;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "rolePrivilegeList", isVariablePrivate = true)
    public List<RolePrivilege> getRolePrivilegeList() {
        if (rolePrivilegeList == null || rolePrivilegeList.isEmpty()) {
            rolePrivilegeList = SQLite.select()
                    .from(RolePrivilege.class)
                    .where(RolePrivilege_Table.role_id.eq(id))
                    .queryList();
        }
        return rolePrivilegeList;
    }

    public void setRolePrivilegeList(List<RolePrivilege> rolePrivilegeList) {
        this.rolePrivilegeList = rolePrivilegeList;
    }


}
