//package com.ift2905.reservation.database.entities;
package hayen.spectacle.database.entities;

import android.util.Log;



public class Section {


    public static final String TABLE_NAME = "section";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "nom";
    public static final String COLUMN_CATEGORIE = "categorie";
    public static final String COLUMN_NB_SIEGES = "nb_sieges";
    public static final String COLUMN_SALLE_ID = "id_salle";



//    public static final String CREATE_TABLE_SECTION =
//            "CREATE TABLE IF NOT EXISTS "+TABLE_NAME +
//                    " ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_NAME + " VARCHAR(30), " +
//                    COLUMN_CATEGORIE + " INTEGER, " +
//                    COLUMN_NB_SIEGES + " INTEGER DEFAULT 0, " +
//                    COLUMN_SALLE_ID + " INTEGER, " +
//                    " FOREIGN KEY ("+COLUMN_SALLE_ID+") REFERENCES "+TABLE_NAME_REF_SALLE+"("+COLUMN_ID+"))";


    private int id;
    private String name;
    private int nbSieges;
    private int categorie;
    private int salleId;

    public Section(){}

    public Section(String name, int categorie, int nbSieges, int salleId ){
        this.id = 0;
        this.name =  name;
        this.categorie =  categorie;
        this.nbSieges =  nbSieges;
        this.salleId =  salleId;

    }

    public Section(int id, String name, int categorie, int nbSieges, int salleId ){
        this.id = id;
        this.name =  name;
        this.categorie =  categorie;
        this.nbSieges =  nbSieges;
        this.salleId =  salleId;

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategorie() {
        return this.categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public int getNbSieges() {
        return this.nbSieges;
    }

    public void setNbSieges(int nbSieges) {
        this.nbSieges = nbSieges;
    }

    public int getSalleId() {
        return this.salleId;
    }

    public void setSalleId(int salleId) {
        this.salleId = salleId;
    }

    public String toString(){
        String str = "id: " + this.getId();
        str += "; nom: " + this.getName();
        str += "; categorie: " + this.getCategorie();
        str += "; nbSieges: " + this.getNbSieges();
        str += "; salleId: " + this.getSalleId();

        return str;

    }

}
