//package com.ift2905.reservation.database.entities;
package hayen.spectacle.database.entities;


public class Genre {

    public static final String TABLE_NAME = "genre";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "nom";

//    public static final String CREATE_TABLE_GENRE =
//            "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
//            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//            COLUMN_NAME + " VARCHAR(30) NOT NULL)";


    private int id;
    private String nom;

    public Genre(){
        this.id = -1;
        this.nom = "";
    }

    public Genre(int id, String nom){
        this.id = id;
        this.nom = nom;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String toString(){
        String str = "Id: " + getId() + "; nom: " + getNom();

        return str;

    }
}
