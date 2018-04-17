//package com.ift2905.reservation.database.entities;
package hayen.spectacle.data.data;

/**
 * Created by daristote on 18-03-15.
 */

public class Reservation {
    public static final Reservation bidon = new Reservation(-1, "0000000", "aujourd'hui", -1);

    public static final String TABLE_NAME = "reservation";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NUMERO_CONFIRMATION = "num_confirmation";
    public static final String COLUMN_DATE_RESERVATION = "date_reservation";
    public static final String COLUMN_USER_ID = "id_utilisateur";
    public static final String COLUMN_SPECTACLE_ID = "id_spectacle";

    private int id;
    private String numeroConfirmation;
    private String dateReservation;
    private int userId;
//    private int spectacleId;

    public Reservation(){}
    public Reservation(String numeroConfirmation, String dateReservation, int userId){
        this.id = 0;
        this.numeroConfirmation =  numeroConfirmation;
        this.dateReservation = dateReservation;
        this.userId = userId;
 //       this.spectacleId = spectacleId;
    }

    public Reservation(int id, String numeroConfirmation, String dateReservation, int userId){
        this.id = id;
        this.numeroConfirmation =  numeroConfirmation;
        this.dateReservation = dateReservation;
        this.userId = userId;
 //       this.spectacleId = spectacleId;
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
