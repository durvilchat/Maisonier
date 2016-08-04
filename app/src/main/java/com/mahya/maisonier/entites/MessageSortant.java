/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahya.maisonier.entites;

import android.support.annotation.Size;

import com.mahya.maisonier.dataBase.Maisonier;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = Maisonier.class, useBooleanGetterSetters = true)
public class MessageSortant extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;
    @Size(max = 255)
    @Column(name = "contenu", length = 255)
    String contenu;
    @Size(max = 255)
    @Column(name = "type_message", length = 255)
    String typeMessage;


    List<BoiteEnvoi> boiteEnvoiList;

    public MessageSortant() {
    }

    public MessageSortant(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "boiteEnvoiList", isVariablePrivate = true)
    public List<BoiteEnvoi> getBoiteEnvoiList() {
        if (boiteEnvoiList == null || boiteEnvoiList.isEmpty()) {
            boiteEnvoiList = SQLite.select()
                    .from(BoiteEnvoi.class)
                    .where(BoiteEnvoi_Table.messagesortant_id.eq(id))
                    .queryList();
        }
        return boiteEnvoiList;
    }

    public void setBoiteEnvoiList(List<BoiteEnvoi> boiteEnvoiList) {
        this.boiteEnvoiList = boiteEnvoiList;
    }

}
