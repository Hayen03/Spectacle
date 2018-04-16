//package com.ift2905.reservation.database.entities;
package hayen.spectacle.database.data;



public class Utilisateur {

    public static final String TABLE_NAME = "utilisateur";
    public static final String TABLE_NAME_REF_ADRESSE = "adresse";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRENOM = "prenom";
    public static final String COLUMN_NOM = "nom";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_COURRIEL = "courriel";
    public static final String COLUMN_MOT_PASSE = "mot_passe";
    public static final String COLUMN_TELEPHONE = "telephone";
    public static final String COLUMN_ADRESSE_ID = "id_adresse";



    public static final String CREATE_TABLE_UTILISATEUR =

            "CREATE TABLE IF NOT EXISTS "+TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PRENOM + " VARCHAR(30) NOT NULL, " +
                    COLUMN_NOM + " VARCHAR(30) NOT NULL, " +
                    COLUMN_LOGIN + " VARCHAR(30) UNIQUE NOT NULL, " +
                    COLUMN_MOT_PASSE + " VARCHAR(80) NOT NULL, " +
                    COLUMN_COURRIEL  + " VARCHAR(40) UNIQUE NOT NULL, " +
                    COLUMN_ADRESSE_ID + " INTEGER DEFAULT NULL, " +
                    COLUMN_TELEPHONE + " VARCHAR(10) NOT NULL, " +
                    "FOREIGN KEY ("+COLUMN_ADRESSE_ID+") REFERENCES "+TABLE_NAME_REF_ADRESSE+"("+COLUMN_ID+"))";

    private int id;
    private String prenom;
    private String nom;
    private String login;
    private String motPasse;
    private String courriel;
    private String telephone;
    private int adresseId;


    public Utilisateur(){}

    public Utilisateur(String prenom, String nom, String login, String motPasse, String courriel, String telephone, int adresseId){
        this.id = 0;
        this.prenom = prenom;
        this.nom =  nom;
        this.login = login;
        this.motPasse = motPasse;
        this.courriel = courriel;
        this.telephone =  telephone;
        this.adresseId =  adresseId;
    }

    public Utilisateur(int id, String prenom, String nom, String login, String motPasse, String courriel, String telephone, int adresseId){
        this.id = id;
        this.prenom = prenom;
        this.nom =  nom;
        this.login = login;
        this.motPasse = motPasse;
        this.courriel = courriel;
        this.telephone =  telephone;
        this.adresseId =  adresseId;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotPasse() {
        return motPasse;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(int adresseId) {
        this.adresseId = adresseId;
    }

    public String toString(){

        String str =  "Id: " + getId() + "; Prénom: " + getPrenom() + "; Nom: " + getNom();
        str += "; Login: " + getLogin() + "; mot de passe: " + getMotPasse() + "; courriel: " + getCourriel();
        str += "; Téléphone: " + getTelephone() + "; adresse id: " + getAdresseId();

        return str;

    }
}
