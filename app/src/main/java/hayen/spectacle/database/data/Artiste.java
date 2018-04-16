//package com.ift2905.reservation.database.entities;
package hayen.spectacle.database.data;



public class Artiste {



    public static final String TABLE_NAME = "artiste";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRSTNAME = "prenom";
    public static final String COLUMN_LASTNAME = "nom";

    public static final String CREATE_TABLE_ARTISTE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FIRSTNAME + " VARCHAR(30) NOT NULL, " +
            COLUMN_LASTNAME + " VARCHAR(30))";


    private int id;
    private String prenom;
    private String nom;

    public Artiste(){
        this.id = 0;
        this.prenom = "";
        this.nom = "";

    }

    public Artiste(String prenom, String nom){
        this.id = 0;
        this.prenom = prenom;
        this.nom = nom;

    }


    public Artiste(int id, String prenom, String nom){
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String toString(){

        return "Id: " + getId() + "; Prénom: " + getPrenom() + "; Nom: " + getNom();
    }
}
