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
public class Telephone extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    Integer id;

    @NotNull
    @Column(name = "frequence")
    int frequence;
    @Size(max = 255)
    @Column(name = "marque", length = 255)
    String marque;
    @Size(max = 255)
    @Column(name = "modele", length = 255)
    String modele;
    @Size(max = 255)
    @Column(name = "numero_telephone", length = 255)
    String numeroTelephone;
    @Size(max = 255)
    @Column(name = "port", length = 255)
    String port;
    @Size(max = 255)
    @Column(name = "server_number", length = 255)
    String serverNumber;
    @Size(max = 255)
    @Column(name = "type_reseau", length = 255)
    String typeReseau;

    List<MessageEntrant> messageEntrantList;

    List<BoiteEnvoi> boiteEnvoiList;

    public Telephone() {
    }

    public Telephone(Integer id) {
        this.id = id;
    }

    public Telephone(Integer id, int frequence) {
        this.id = id;
        this.frequence = frequence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFrequence() {
        return frequence;
    }

    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServerNumber() {
        return serverNumber;
    }

    public void setServerNumber(String serverNumber) {
        this.serverNumber = serverNumber;
    }

    public String getTypeReseau() {
        return typeReseau;
    }

    public void setTypeReseau(String typeReseau) {
        this.typeReseau = typeReseau;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "messageEntrantList", isVariablePrivate = true)
    public List<MessageEntrant> getMessageEntrantList() {
        if (messageEntrantList == null || messageEntrantList.isEmpty()) {
            messageEntrantList = SQLite.select()
                    .from(MessageEntrant.class)
                    .where(MessageEntrant_Table.telephone_id.eq(id))
                    .queryList();
        }
        return messageEntrantList;
    }

    public void setMessageEntrantList(List<MessageEntrant> messageEntrantList) {
        this.messageEntrantList = messageEntrantList;
    }


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "boiteEnvoiList", isVariablePrivate = true)
    public List<BoiteEnvoi> getBoiteEnvoiList() {
        if (boiteEnvoiList == null || boiteEnvoiList.isEmpty()) {
            boiteEnvoiList = SQLite.select()
                    .from(BoiteEnvoi.class)
                    .where(BoiteEnvoi_Table.telephone_id.eq(id))
                    .queryList();
        }
        return boiteEnvoiList;
    }

    public void setBoiteEnvoiList(List<BoiteEnvoi> boiteEnvoiList) {
        this.boiteEnvoiList = boiteEnvoiList;
    }
}
