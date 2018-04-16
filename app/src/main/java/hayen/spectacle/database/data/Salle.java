//package com.ift2905.reservation.database.entities;
package hayen.spectacle.database.data;




public class Salle {

    public static final String TABLE_NAME = "salle";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "nom";
    public static final String COLUMN_TELEPHONE = "telephone";
    public static final String COLUMN_FAX = "fax";
    public static final String COLUMN_COURRIEL = "courriel";
    public static final String COLUMN_ADRESSE_ID = "id_adresse";
    public static final String COLUMN_DESCRIPTION = "description";


    public static final String CREATE_TABLE_SALLE =

                "CREATE TABLE IF NOT EXISTS salle (id INTEGER PRIMARY KEY AUTOINCREMENT, nom VARCHAR(30), telephone VARCHAR(20), fax VARCHAR(30), " +
                        " courriel VARCHAR(30), description TEXT, adresse_id INTEGER, FOREIGN KEY (adresse_id) REFERENCES adresse(id)) ";


    private int id;
    private String nom;
    private String telephone;
    private String fax;
    private String courriel;
    private String description;
    private int adresseId;

    public Salle(){}

    public Salle(int id, String nom, String telephone, String fax, String courriel, int adresseId, String description){
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
        this.fax = fax;
        this.courriel = courriel;
        this.adresseId = adresseId;
        this.description = description;
    }


    public Salle(String nom, String telephone, String fax, String courriel, int adresseId, String description){
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
        this.fax = fax;
        this.courriel = courriel;
        this.adresseId = adresseId;
        this.description = description;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(int adresseId) {
        this.adresseId = adresseId;
    }

    public String toString(){
        String str = "Id: " + getId() + "; nom: " + getNom() + "; telephone: " + getTelephone();
        str += "; fax: " + getFax() + "; courriel: " + getCourriel() + "; adresse id: " + getAdresseId();
        str += "; description: " + getDescription();

        return str;

    }
}
