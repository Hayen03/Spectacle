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

//import com.ift2905.reservation.database.dao.CarteCreditSQLHelper;
//import com.ift2905.reservation.database.dao.Constant;
//import com.ift2905.reservation.database.entities.CarteCredit;


import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.CarteCredit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class TestCarteCredit {


    @Test
    public void getCarteCredit() {

        Log.i("RPI", "Récupérer une Carte de Credit");

        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext);// (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbCarteCredits = dbHelper.getCartesCount();

        Log.i("RPI", "carte count: " + nbCarteCredits);
        assert(nbCarteCredits > 0);

        List<CarteCredit> carteCredits = dbHelper.getAllCartes();
        for (CarteCredit carteCredit1 : carteCredits) {
            Log.i("RPI", "CarteCredit: " + carteCredit1);
        }


        CarteCredit carteCredit =  dbHelper.getCarteByUserId(3);

        Log.i("RPI", "Carte credit: " + carteCredit);



        assertNotNull(carteCredit);

        CarteCredit carteCredit2 =  dbHelper.getCarteByUserId(carteCredit.getUtilisateurId());


        assertEquals(carteCredit.getId(), carteCredit2.getId());
        assertEquals(carteCredit.getNomCarte(), carteCredit2.getNomCarte());
        assertEquals(carteCredit.getNumero(), carteCredit2.getNumero());


    }


    @Test
    public void addCarteCredit(){

        Log.i("RPI", "Ajout d'un CarteCredit");

        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext);// (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbCarteCredits = dbHelper.getCartesCount();

        List<CarteCredit> carteCredits = dbHelper.getAllCartes();

        Log.i("RPI", "Liste des CarteCredits avant insertion");

        for (CarteCredit carteCredit1 : carteCredits) {
            Log.i("RPI", "CarteCredit: " + carteCredit1);
        }


        CarteCredit carteCredit = new CarteCredit();
        carteCredit.setUtilisateurId(6);
        carteCredit.setNomUtilisateur("Rosamonde Fréchette");
        carteCredit.setNumero("2618716823768812");
        carteCredit.setNomCarte("VISA");
        carteCredit.setDate_expiration("2020-05-01'");
        carteCredit.setCode(287);


        Log.i("RPI", "Ajout d'un CarteCredit: " + carteCredit);

        long result = dbHelper.addCarte(carteCredit);

        int nbCarteCredits2 = dbHelper.getCartesCount();

        assertEquals(nbCarteCredits2, nbCarteCredits + 1);

        Log.i("RPI", "Ajout de la carteCredit : " + result);

        carteCredits = dbHelper.getAllCartes();

        Log.i("RPI", "Liste des CarteCredits après insertion");

        for (CarteCredit carteCredit1 : carteCredits) {
            Log.i("RPI", "CarteCredit: " + carteCredit1);
        }



            database.close();

    }

    @Test
    public void deleteCarteCredit() {

        Log.i("RPI", "Effacer une CarteCredit");
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext);// (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);


        int nbCarteCredits = dbHelper.getCartesCount();

        List <CarteCredit> carteCredits = dbHelper.getAllCartes();

        assert(carteCredits.size() > 0 );

        int index =  carteCredits.get(carteCredits.size() -1).getUtilisateurId();

        Log.i("RPI", "Liste des CarteCredits avant suppression");

        for (CarteCredit CarteCredit1 : carteCredits) {
            Log.i("RPI", "CarteCredit: " + CarteCredit1);
        }



        Log.i("RPI: ", "nbCarteCredits: " + nbCarteCredits);
        int nbCarteCredits2 = 0;

        CarteCredit carteCredit = dbHelper.getCarteByUserId(index);

        if(carteCredit != null) {

            int result = dbHelper.deleteCarteCredit(carteCredit);

            nbCarteCredits2 = dbHelper.getCartesCount();

            assertEquals(nbCarteCredits2, nbCarteCredits - 1);

            Log.i("RPI", "Liste des CarteCredits après suppression");
            Log.i("RPI: ", "nbCarteCredits2: " + nbCarteCredits2);
            for (CarteCredit CarteCredit1 : carteCredits) {
                Log.i("RPI", "CarteCredit: " + CarteCredit1);
            }


        }

    }


    @Test
    public void updateCarteCredit() {
        Log.i("RPI", "Mettre à jour un CarteCredit");
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext);// (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbCarteCredits = dbHelper.getCartesCount();

        List <CarteCredit> carteCredits = dbHelper.getAllCartes();

        assertNotNull(carteCredits);

        assert(carteCredits.size() > 0);

        String numero =  "3826128739712873";
        String nom = "VISA";
        String date_expriration = "2022-02-01";
        int code =  534;

        CarteCredit carteCredit =  carteCredits.get(3);


        Log.i("RPI", "carteCredit avant mise à jour: " + carteCredit);


        carteCredit.setNomCarte(nom);
        carteCredit.setNumero(numero);
        carteCredit.setDate_expiration(date_expriration);
        carteCredit.setCode(code);

        dbHelper.updateCarte(carteCredit);

        int nbCarteCredits2 = dbHelper.getCartesCount();

        CarteCredit carteCredit2 =  dbHelper.getCarteByUserId(carteCredit.getUtilisateurId());


        Log.i("RPI", "CarteCredit après mise à jour: " + carteCredit);

        assert(carteCredit.getNomCarte().equals(carteCredit2.getNomCarte()));
        assert(carteCredit.getNumero().equals(carteCredit2.getNumero()));
        assert(carteCredit.getDate_expiration().equals(carteCredit2.getDate_expiration()));
        assert(carteCredit.getCode() == carteCredit2.getCode());

    }


}
