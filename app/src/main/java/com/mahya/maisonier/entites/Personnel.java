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
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.Date;
import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Personnel extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Column(name = "date_creation")

    Date dateCreation;
    @Size(max = 254)
    @Unique
    @Column(name = "matricule", length = 254)
    String matricule;
    @Size(max = 254)
    @Column(name = "nom", length = 254)
    String nom;
    @Size(max = 255)
    @Column(name = "photo", length = 255)
    String photo;
    @Size(max = 254)
    @Column(name = "prenom", length = 254)
    String prenom;

    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "theme", length = 254)
    String theme;


    List<PersonnelRole> personnelRoleList;


    List<PersonnelPrivilege> personnelPrivilegeList;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "utilisateur_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<CompteUtilisateur> compteUtilisateur;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "poste_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Poste> poste;

    public Personnel() {
    }

    public Personnel(Integer id) {
        this.id = id;
    }

    public Personnel(Integer id, String theme) {
        this.id = id;
        this.theme = theme;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "personnelRoleList", isVariablePrivate = true)
    public List<PersonnelRole> getPersonnelRoleList() {
        if (personnelRoleList == null || personnelRoleList.isEmpty()) {
            personnelRoleList = SQLite.select()
                    .from(PersonnelRole.class)
                    .where(PersonnelRole_Table.personnel_id.eq(id))
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
                    .where(PersonnelPrivilege_Table.personnel_id.eq(id))
                    .queryList();
        }
        return personnelPrivilegeList;
    }

    public void setPersonnelPrivilegeList(List<PersonnelPrivilege> personnelPrivilegeList) {
        this.personnelPrivilegeList = personnelPrivilegeList;
    }

    public void assoCompte(CompteUtilisateur utilisateur1) {
        compteUtilisateur = new ForeignKeyContainer<>(CompteUtilisateur.class);
        compteUtilisateur.setModel(utilisateur1);
        compteUtilisateur.put(CompteUtilisateur_Table.id, utilisateur1.id);

    }

    public void assoPoste(Poste poste1) {
        poste = new ForeignKeyContainer<>(Poste.class);
        poste.setModel(poste1);
        poste.put(CompteUtilisateur_Table.id, poste1.id);

    }
}
