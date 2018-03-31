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
//import com.ift2905.reservation.database.dao.PaiementSQLHelper;
//import com.ift2905.reservation.database.entities.Paiement;

import hayen.spectacle.database.dao.Constant;
import hayen.spectacle.database.dao.PaiementSQLHelper;
import hayen.spectacle.database.entities.Paiement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class TestPaiement {


    @Test
    public void getPaiement() {

        Log.i("RPI", "Récupérer un Paiement");

        Context appContext = InstrumentationRegistry.getTargetContext();

        PaiementSQLHelper dbHelper = new PaiementSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        assertNotNull(database);

        int nbPaiements = dbHelper.getPaiementsCount();

        assert(nbPaiements > 0);

        Log.i("RPI", "nb  Paiements: " + nbPaiements);

        List<Paiement> paiements = dbHelper.getAllPaiements();

        Log.i("RPI", "nb  Paiements: " + paiements.size());

        assertEquals(nbPaiements, paiements.size());

        for (Paiement paiement : paiements) {
            Log.i("RPI", "paiement: " + paiement);
        }


        Paiement paiement =  dbHelper.getPaiementById(paiements.get(paiements.size()-1).getId());

        assertNotNull(paiement);

        Paiement paiement2 =  dbHelper.getPaiementById(paiement.getId());


        assertEquals(paiement.getId(), paiement2.getId());


    }


    @Test
    public void addPaiement(){

        Log.i("RPI", "Ajout d'un Paiement");


        Context appContext = InstrumentationRegistry.getTargetContext();

        PaiementSQLHelper dbHelper = new PaiementSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbPaiements = dbHelper.getPaiementsCount();

        List<Paiement> paiements = dbHelper.getAllPaiements();

        Log.i("RPI", "Liste des paiements avant insertion");

        for (Paiement paiement1 : paiements) {
            Log.i("RPI", "paiement: " + paiement1);
        }



        Paiement paiement = new Paiement();
        paiement.setMontant(92.0f);
        paiement.setDatePaiement("2018-03-30 21:51:00");
        paiement.setReservation_id(9);


        Log.i("RPI", "Ajout d'un paiement: " + paiement);

        long result = dbHelper.addPaiement(paiement);

        int nbPaiements2 = dbHelper.getPaiementsCount();

        assertEquals(nbPaiements2, nbPaiements + 1);

        Log.i("RPI", "Ajout du paiement : " + result);

        paiements = dbHelper.getAllPaiements();

        assertEquals(nbPaiements2, paiements.size());


        Log.i("RPI", "Liste des paiements après insertion");

        for (Paiement paiement2 : paiements) {
            Log.i("RPI", "paiement: " + paiement2);
        }


            database.close();

    }


    @Test
    public void updatePaiement() {
        Log.i("RPI", "Mettre à jour d'un Paiement");
        Context appContext = InstrumentationRegistry.getTargetContext();

        PaiementSQLHelper dbHelper = new PaiementSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbPaiements = dbHelper.getPaiementsCount();

        List <Paiement> paiements = dbHelper.getAllPaiements();

        assertNotNull(paiements);

        assert(paiements.size() > 0);

        Log.i("RPI", "Liste des paiements avant mise à jour: ");
        for (Paiement paiement : paiements) {
            Log.i("RPI", "paiement: " + paiement);
        }



        Paiement paiement =  paiements.get(paiements.size()-1);
        Log.i("RPI", "Paiement avant la mise à jour: " + paiement);

        paiement.setReservation_id(7);

        dbHelper.updatePaiement(paiement);

        int nbPaiements2 = dbHelper.getPaiementsCount();

        Paiement paiement2 =  dbHelper.getPaiementById(paiement.getId());

        Log.i("RPI", "Paiement avant la mise à jour: " + paiement2);



        List <Paiement> paiements2 = dbHelper.getAllPaiements();

        Log.i("RPI", "Liste des paiements après mise à jour");


        for (Paiement paiement3 : paiements2) {
            Log.i("RPI", "paiement: " + paiement3);
        }

        Log.i("RPI", "paiement 1: " + paiement.getMontant());
        Log.i("RPI", "paiement 2: " + paiement2.getMontant());

        assertEquals(paiement.getId(), paiement2.getId());
        assertTrue(paiement.getMontant() == paiement2.getMontant());
        assertEquals(paiement.getDatePaiement(), paiement2.getDatePaiement());
        assertEquals(paiement.getReservation_id(), paiement2.getReservation_id());



        assertEquals(nbPaiements, nbPaiements2);


    }

    @Test
    public void deletePaiement() {

        Log.i("RPI", "Effacer un Paiement");
        Context appContext = InstrumentationRegistry.getTargetContext();

        PaiementSQLHelper dbHelper = new PaiementSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);


        int nbPaiements = dbHelper.getPaiementsCount();

        List <Paiement> paiements = dbHelper.getAllPaiements();

        assert(paiements.size() > 0 );

        int index =  paiements.get(paiements.size() -1).getId();

        Log.i("RPI", "Liste des paiements avant suppression");

        for (Paiement paiement : paiements) {
            Log.i("RPI", "paiement: " + paiement);
        }



        Log.i("RPI: ", "nbPaiements: " + nbPaiements);
        int nbPaiements2 = 0;

        Paiement paiement = dbHelper.getPaiementById(index);

        if(paiement != null) {

            int result = dbHelper.deletePaiement(paiement);

            nbPaiements2 = dbHelper.getPaiementsCount();

            assertEquals(nbPaiements2, nbPaiements - 1);

            Log.i("RPI", "Liste des paiements après suppression");
            Log.i("RPI: ", "nbPaiements: " + nbPaiements2);

            List <Paiement> paiements2 = dbHelper.getAllPaiements();
            for (Paiement paiement2 : paiements2) {
                Log.i("RPI", "paiement: " + paiement2);
            }


        }

    }




}
