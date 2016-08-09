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
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class TypeCaution extends BaseModel {


    public static List<TypeCaution> typeCautions = new ArrayList<>();
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Unique
    @Size(min = 1, max = 255)
    @Column(name = "libelle", length = 255)
    String libelle;

    List<Caution> cautionList;

    public TypeCaution() {
    }

    public TypeCaution(Integer id) {
        this.id = id;
    }

    public TypeCaution(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public static List<TypeCaution> getInitData(int size) {
        List<TypeCaution> typeLogements = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            typeLogements.add(createRandomPerson());
        }
        return typeLogements;
    }

    public static TypeCaution createRandomPerson() {
        TypeCaution typeLogement = null;
        try {
            typeLogement = typeCautions.get(0);
            typeCautions.remove(0);
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        return typeLogement;

    }

    public static List<TypeCaution> findAll() {
        return SQLite.select().from(TypeCaution.class).queryList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "cautionList", isVariablePrivate = true)
    public List<Caution> getCautionList() {
        if (cautionList == null || cautionList.isEmpty()) {
            cautionList = SQLite.select()
                    .from(Caution.class)
                    .where(Caution_Table.typecaution_id.eq(id))
                    .queryList();
        }
        return cautionList;
    }

    public void setCautionList(List<Caution> cautionList) {
        this.cautionList = cautionList;
    }

}
