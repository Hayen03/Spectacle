//package com.ift2905.reservation;
package hayen.spectacle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

//import com.ift2905.reservation.database.dao.Constant;
//import com.ift2905.reservation.database.dao.ReservationSQLHelper;
//import com.ift2905.reservation.database.entities.Reservation;

import hayen.spectacle.database.dao.Constant;
import hayen.spectacle.database.dao.ReservationSQLHelper;
import hayen.spectacle.database.entities.Reservation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class TestReservation {


    @Test
    public void getReservation() {

        Log.i("RPI", "Récupérer une Reservation");

        Context appContext = InstrumentationRegistry.getTargetContext();

        ReservationSQLHelper dbHelper = new ReservationSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbReservations = dbHelper.getReservationsCount();

        Log.i("RPI", "Reservation count: " + nbReservations);
        assert(nbReservations > 0);

        List<Reservation> reservations = dbHelper.getAllReservations();

        Log.i("RPI", "Reservation count: " + reservations.size());

        for (Reservation reservation1 : reservations) {
            Log.i("RPI", "Reservation: " + reservation1);
        }


        Reservation reservation =  dbHelper.getReservationById(reservations.size() / 2);

        Log.i("RPI", "Réservation: " + reservation);

        assertNotNull(reservation);

        Reservation reservation2 =  dbHelper.getReservationById(reservation.getId());


        assertEquals(reservation.getId(), reservation2.getId());
        assertEquals(reservation.getNumeroConfirmation(), reservation2.getNumeroConfirmation());
        assertEquals(reservation.getUserId(), reservation2.getUserId());
        assertEquals(reservation.getDateReservation(), reservation2.getDateReservation());


    }


    @Test
    public void addReservation(){

        Log.i("RPI", "Ajout d'une Reservation");

        Context appContext = InstrumentationRegistry.getTargetContext();

        ReservationSQLHelper dbHelper = new ReservationSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbReservations = dbHelper.getReservationsCount();

        List<Reservation> reservations = dbHelper.getAllReservations();

        Log.i("RPI", "Liste des Reservations avant insertion");

        for (Reservation reservation1 : reservations) {
            Log.i("RPI", "Reservation: " + reservation1);
        }


        Reservation reservation = new Reservation();
        reservation.setNumeroConfirmation("3712638172389");
        reservation.setUserId(4);
        reservation.setDateReservation("2018-03-10 13:42:00");


        Log.i("RPI", "Ajout d'un Reservation: " + reservation);

        long result = dbHelper.addReservation(reservation);

        int nbReservations2 = dbHelper.getReservationsCount();

        assertEquals(nbReservations2, nbReservations + 1);

        Log.i("RPI", "Ajout de la Reservation : " + result);

        reservations = dbHelper.getAllReservations();

        Log.i("RPI", "Liste des Reservations après insertion");

        for (Reservation reservation1 : reservations) {
            Log.i("RPI", "Reservation: " + reservation1);
        }


        database.close();

    }


    @Test
    public void updateReservation() {
        Log.i("RPI", "Mettre à jour d'une Reservation");
        Context appContext = InstrumentationRegistry.getTargetContext();

        ReservationSQLHelper dbHelper = new ReservationSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbReservations = dbHelper.getReservationsCount();

        List <Reservation> reservations = dbHelper.getAllReservations();

        assertNotNull(reservations);

        assert(reservations.size() > 0);

        String nom =  "Métal classique";

        Reservation reservation =  reservations.get(reservations.size() - 1);


        Log.i("RPI", "Reservation avant mise à jour: " + reservation);


        reservation.setDateReservation("2018-03-22 16:01:00");


        dbHelper.updateReservation(reservation);

        int nbReservations2 = dbHelper.getReservationsCount();

        Reservation reservation2 =  dbHelper.getReservationById(reservation.getId());


        Log.i("RPI", "Reservation après mise à jour: " + reservation2);

        assert(reservation.getDateReservation().equals(reservation2.getDateReservation()));

    }



    @Test
    public void deleteReservation() {

        Log.i("RPI", "Effacer une Reservation");
        Context appContext = InstrumentationRegistry.getTargetContext();

        ReservationSQLHelper dbHelper = new ReservationSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);


        int nbReservations = dbHelper.getReservationsCount();

        List <Reservation> reservations = dbHelper.getAllReservations();

        assert(reservations.size() > 0 );

        int index =  reservations.get(reservations.size() -1).getId();

        Log.i("RPI", "Liste des Reservations avant suppression");

        for (Reservation reservation1 : reservations) {
            Log.i("RPI", "Reservation: " + reservation1);
        }


        Log.i("RPI: ", "nbReservations: " + nbReservations);
        int nbReservations2 = 0;

        Reservation reservation = dbHelper.getReservationById(index);

        if(reservation != null) {

            int result = dbHelper.deleteReservation(reservation);

            nbReservations2 = dbHelper.getReservationsCount();

            assertEquals(nbReservations2, nbReservations - 1);

            Log.i("RPI", "Liste des Reservations après suppression");
            Log.i("RPI: ", "nbReservations2: " + nbReservations2);
            for (Reservation reservation1 : reservations) {
                Log.i("RPI", "Reservation: " + reservation);
            }


        }

    }


}
