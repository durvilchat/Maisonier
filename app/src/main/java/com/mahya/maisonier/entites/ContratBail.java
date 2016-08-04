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
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.Date;
import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class ContratBail extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "date_etablissement")

    Date dateEtablissement;


    List<ContratRubrique> contratRubriqueList;


    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "bailleur_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Bailleur> bailleur;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "occupation_id",
                    columnType = Integer.class,
                    foreignKeyColumnName = "id")},
            saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    ForeignKeyContainer<Occupation> occupation;

    public ContratBail() {
    }

    public ContratBail(Integer id) {
        this.id = id;
    }

    public ContratBail(Integer id, Date dateEtablissement) {
        this.id = id;
        this.dateEtablissement = dateEtablissement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateEtablissement() {
        return dateEtablissement;
    }

    public void setDateEtablissement(Date dateEtablissement) {
        this.dateEtablissement = dateEtablissement;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "contratRubriqueList", isVariablePrivate = true)
    public List<ContratRubrique> getContratRubriqueList() {
        if (contratRubriqueList == null || contratRubriqueList.isEmpty()) {
            contratRubriqueList = SQLite.select()
                    .from(ContratRubrique.class)
                    .where(ContratRubrique_Table.contratbail_id.eq(id))
                    .queryList();
        }

        return contratRubriqueList;
    }

    public void setContratRubriqueList(List<ContratRubrique> contratRubriqueList) {
        this.contratRubriqueList = contratRubriqueList;
    }

    public void assoBailleur(Bailleur bailleur1) {
        bailleur = new ForeignKeyContainer<>(Bailleur.class);
        bailleur.setModel(bailleur1);
        bailleur.put(Bailleur_Table.id, bailleur1.id);

    }

    public void assoOccupation(Occupation occupation1) {
        occupation = new ForeignKeyContainer<>(Occupation.class);
        occupation.setModel(occupation1);
        occupation.put(Occupation_Table.id, occupation1.id);

    }
}
