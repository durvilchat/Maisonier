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
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.ArrayList;
import java.util.List;

@ModelContainer
@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class Cite extends BaseModel {

    public static List<Cite> cites = new ArrayList<>();
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Size(max = 255)
    @Column(name = "description", length = 255)
    String description;

    @Size(max = 255)
    @Column(name = "email", length = 255)
    String email;

    @NotNull
    @Column(name = "etat")
    boolean etat;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nom_cite", length = 255)
    String nomCite;

    @Column(name = "police_cite")
    Double policeCite;
    @Column(name = "police_contact")
    Double policeContact;
    @Column(name = "police_description")
    Double policeDescription;
    @Size(max = 255)
    @Column(name = "siege", length = 255)
    String siege;
    @Size(max = 255)
    @Column(name = "tels", length = 255)
    String tels;
    //@OneToMany(mappedBy = "cite")
    List<Cite> batimentList;
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "bailleur_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Bailleur> bailleur;

    public Cite() {
    }

    public Cite(Integer id) {
        this.id = id;
    }

    public Cite(Integer id, boolean etat, String nomCite) {
        this.id = id;
        this.etat = etat;
        this.nomCite = nomCite;
    }

    public static List<Cite> getInitData(int size) {
        List<Cite> Cite = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Cite.add(createRandomPerson());
        }
        return Cite;
    }

    public static Cite createRandomPerson() {
        Cite Cite = null;
        try {
            Cite = cites.get(0);
            cites.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return Cite;

    }

    public static List<Cite> findAll() {
        return SQLite.select().from(Cite.class).queryList();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public String getNomCite() {
        return nomCite;
    }

    public void setNomCite(String nomCite) {
        this.nomCite = nomCite;
    }

    public Double getPoliceCite() {
        return policeCite;
    }

    public void setPoliceCite(Double policeCite) {
        this.policeCite = policeCite;
    }

    public Double getPoliceContact() {
        return policeContact;
    }

    public void setPoliceContact(Double policeContact) {
        this.policeContact = policeContact;
    }

    public Double getPoliceDescription() {
        return policeDescription;
    }

    public void setPoliceDescription(Double policeDescription) {
        this.policeDescription = policeDescription;
    }

    public String getSiege() {
        return siege;
    }

    public void setSiege(String siege) {
        this.siege = siege;
    }

    public String getTels() {
        return tels;
    }
/*

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "batimentList", isVariablePrivate = true)
    public List<Cite> getBatimentList() {
        if (batimentList == null || batimentList.isEmpty()) {
            batimentList = SQLite.select()
                    .from(Cite.class)
                    .where(Batiment_Table.cite_id.eq(id))
                    .queryList();
        }
        return batimentList;
    }
*/

    public void setTels(String tels) {
        this.tels = tels;
    }

    public ForeignKeyContainer<Bailleur> getBailleur() {
        return bailleur;
    }

    public void setBailleur(ForeignKeyContainer<Bailleur> bailleur) {
        this.bailleur = bailleur;
    }

    public void setBatimentList(List<Cite> batimentList) {
        this.batimentList = batimentList;
    }

    public void assoBailleur(Bailleur bailleur1) {
        bailleur = new ForeignKeyContainer<>(Bailleur.class);
        bailleur.setModel(bailleur1);
        bailleur.put(Bailleur_Table.id, bailleur1.id);

    }

}
