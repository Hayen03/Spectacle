//package com.ift2905.reservation.database.entities;
package hayen.spectacle.data.data;



public class Paiement {
    public static final String TABLE_NAME = "paiement";
    public static final String TABLE_NAME_REF_UTILISATEUR = "reservation";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MONTANT = "montant";
    public static final String COLUMN_DATE_PAIEMENT = "date_paiement";

    public static final String COLUMN_RESERVATION_ID = "id_reservation";

    public static final String CREATE_TABLE_PAIEMENT =
                "CREATE TABLE IF NOT EXISTS paiement (id INTEGER PRIMARY KEY AUTOINCREMENT, montant FLOAT DEFAULT 0.0, reservation_id INTEGER, " +
                        " date_paiement DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (reservation_id) REFERENCES reservation(id))";


    private int id;
    private float montant;
    private String datePaiement;
    private int reservationId;

    public Paiement(){}

    public Paiement(float montant, String date_paeiment, int reservationId ){
        this.id = 0;
        this.montant =  montant;
        this.datePaiement =  date_paeiment;
        this.reservationId =  reservationId;
    }

    public Paiement(int id, float montant, String date_paeiment, int reservationId ){
        this.id = id;
        this.montant =  montant;
        this.datePaiement =  date_paeiment;
        this.reservationId =  reservationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(String datePaiement) {
        this.datePaiement = datePaiement;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public int getReservation_id() {
        return reservationId;
    }

    public void setReservation_id(int reservationId) {
        this.reservationId = reservationId;
    }

    public String toString(){
        String str = "id: " + getId();
        str += "; montant: " + getMontant();
        str += "; date paiement: " + getDatePaiement();
        str += "; reservationId: " + getReservation_id();
        return str;

    }
}
