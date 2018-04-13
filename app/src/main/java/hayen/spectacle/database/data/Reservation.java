//package com.ift2905.reservation.database.entities;
package hayen.spectacle.database.entities;

/**
 * Created by daristote on 18-03-15.
 */

public class Reservation {

    public static final String TABLE_NAME = "reservation";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NUMERO_CONFIRMATION = "num_confirmation";
    public static final String COLUMN_DATE_RESERVATION = "date_reservation";
    public static final String COLUMN_USER_ID = "id_utilisateur";
    public static final String COLUMN_SPECTACLE_ID = "id_spectacle";

//    public static final String CREATE_TABLE_RESERVATION =
//            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
//                    " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_NUMERO_CONFIRMATION  + "VARCHAR(20) UNIQUE NOT NULL, " +
//                    COLUMN_DATE_RESERVATION +" DATETIME DEFAULT CURRENT_TIMESTAMP, " +
//                    COLUMN_USER_ID + " INTEGER NOT NULL, spectacle_id INTEGER NOT NULL, " +
//                    " FOREIGN KEY (" +COLUMN_USER_ID+ ") REFERENCES utilisateur(id), " +
//                    " FOREIGN KEY (" +COLUMN_SPECTACLE_ID + ") REFERENCES spectacle(id))";



    private int id;
    private String numeroConfirmation;
    private String dateReservation;
    private int userId;

    public Reservation(){}
    public Reservation(String numeroConfirmation, String dateReservation, int userId){
        this.id = id;
        this.numeroConfirmation =  numeroConfirmation;
        this.dateReservation = dateReservation;
        this.userId = userId;

    }

    public Reservation(int id, String numeroConfirmation, String dateReservation, int userId){
        this.id = id;
        this.numeroConfirmation =  numeroConfirmation;
        this.dateReservation = dateReservation;
        this.userId = userId;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroConfirmation() {
        return numeroConfirmation;
    }

    public void setNumeroConfirmation(String numeroConfirmation) {
        this.numeroConfirmation = numeroConfirmation;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String toString(){
        String str = "id: " + getId();
        str += "; numero de confirmation: " + getNumeroConfirmation();
        str += "; date de réservation: " + getDateReservation();
                str += "; user id: " + getUserId();

        return str;

    }
}
