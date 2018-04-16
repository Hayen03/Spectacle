//package com.ift2905.reservation.database.entities;
package hayen.spectacle.data.data;


public class CarteCredit {
    public static final String TABLE_NAME = "carte_credit";
    public static final String TABLE_NAME_REF_UTILISATEUR = "utilisateur";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOM_CARTE = "nom_carte";
    public static final String COLUMN_NOM_UTILISATEUR = "nom_utilisateur";
    public static final String COLUMN_NUMERO = "numero";
    public static final String COLUMN_DATE_EXPIRATION = "date_expiration";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_UTILISATEUR_ID = "id_utilisateur";



    public static final String CREATE_TABLE_CARTE_CREDIT =
            "CREATE TABLE IF NOT EXISTS carte_credit (id INTEGER PRIMARY KEY AUTOINCREMENT, nom_carte VARCHAR(20), nom_utilisateur VARCHAR(30), numero VARCHAR(20), " +
                   " date_expiration DATETIME DEFAULT CURRENT_TIMESTAMP, code INTEGER, utilisateur_id INTEGER, UNIQUE(nom_carte, numero), FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id))";


    private int id;
    private String nomCarte;
    private String nomUtilisateur;
    private String numero;
    private String date_expiration;
    private int code;
    private int utilisateurId;



    public CarteCredit(){}


    public CarteCredit(int utilisateurId, String nomCarte, String nomUtilisateur, String numero, String date_expiration, int code){
        this.id = id;
        this.nomCarte = nomCarte;
        this.nomUtilisateur = nomUtilisateur;
        this.numero = numero;
        this.date_expiration = date_expiration;
        this.code =  code;
        this.utilisateurId = utilisateurId;

    }
    public CarteCredit(int id, int utilisateurId, String nomCarte, String nomUtilisateur, String numero, String date_expiration, int code){
        this.id = id;
        this.nomCarte = nomCarte;
        this.nomUtilisateur = nomUtilisateur;
        this.numero = numero;
        this.date_expiration = date_expiration;
        this.code =  code;
        this.utilisateurId = utilisateurId;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomCarte() {
        return nomCarte;
    }

    public void setNomCarte(String nomCarte) {
        this.nomCarte = nomCarte;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDate_expiration() {
        return date_expiration;
    }

    public void setDate_expiration(String date_expiration) {
        this.date_expiration = date_expiration;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }


    public String toString(){
        String str = "Id: " + getId() + "; Utilisateur: " + getNomUtilisateur() + "; id utilisateur: " + getUtilisateurId() + "; nom carte: " + getNomCarte();
        str += "; numero: " + getNumero() + "; date expiration: " + getDate_expiration() + "; code: " + getCode();


        return str;

    }
}
