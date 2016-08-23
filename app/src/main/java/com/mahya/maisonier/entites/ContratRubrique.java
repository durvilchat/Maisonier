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
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.ArrayList;
import java.util.List;

@ModelContainer
@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class ContratRubrique extends BaseModel {


    public static List<ContratRubrique> contratRubriques = new ArrayList<>();
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Size(max = 255)
    @Column(name = "valeur", length = 255)
    String valeur;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "contratbail_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<ContratBail> contratBail;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "rubrique_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.NO_ACTION)
    ForeignKeyContainer<Rubrique> rubrique;

    public ContratRubrique() {
    }

    public ContratRubrique(Integer id) {
        this.id = id;
    }

    public static List<ContratRubrique> getInitData(int size) {
        List<ContratRubrique> ContratRubrique = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ContratRubrique.add(createRandomPerson());
        }
        return ContratRubrique;
    }

    public static ContratRubrique createRandomPerson() {
        ContratRubrique ContratRubrique = null;
        try {
            ContratRubrique = contratRubriques.get(0);
            contratRubriques.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return ContratRubrique;

    }

    public static List<ContratRubrique> findAll() {
        return SQLite.select().from(ContratRubrique.class).queryList();
    }

    public static List<ContratRubrique> getContratRubriques() {
        return contratRubriques;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public void assocContratBail(ContratBail contratBail1) {
        contratBail = new ForeignKeyContainer<>(ContratBail.class);
        contratBail.setModel(contratBail1);
        contratBail.put(ContratRubrique_Table.id, contratBail1.id);

    }

    public void assocRubrique(Rubrique rubrique1) {
        rubrique = new ForeignKeyContainer<>(Rubrique.class);
        rubrique.setModel(rubrique1);
        rubrique.put(Rubrique_Table.id, rubrique1.id);

    }

    public ForeignKeyContainer<Rubrique> getRubrique() {
        return rubrique;
    }

    public ForeignKeyContainer<ContratBail> getContratBail() {
        return contratBail;
    }
}
