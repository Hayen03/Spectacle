//package com.ift2905.reservation;
package hayen.spectacle;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

//import com.ift2905.reservation.database.dao.AdresseSQLHelper;
//import com.ift2905.reservation.database.dao.Constant;
//import com.ift2905.reservation.database.entities.Adresse;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import hayen.spectacle.data.dao.AdresseSQLHelper;
import hayen.spectacle.data.data.Adresse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class TestAdresse {


    @Test
    public void getAdresse() {

        Log.i("RPI", "Récupérer une Adresse");

        Context appContext = InstrumentationRegistry.getTargetContext();

        AdresseSQLHelper adresseSQLHelper =  AdresseSQLHelper.getInstance(appContext) ;//(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = adresseSQLHelper.getWritableDatabase();

        assertNotNull(database);

        int nbAdresses = adresseSQLHelper.getAdressesCount();

        assert(nbAdresses > 0);

        List<Adresse> adresses = adresseSQLHelper.getAllAdresses();

        Adresse adresse =  adresseSQLHelper.getAdresseById(adresses.get(adresses.size() - 1).getId());

        assertNotNull(adresse);

        Adresse adresse2 =  adresseSQLHelper.getAdresseById(adresse.getId());


        assertEquals(adresse.getId(), adresse2.getId());
        assertEquals(adresse.getNumero(), adresse2.getNumero());
        assertEquals(adresse.getRue(), adresse2.getRue());
        assertEquals(adresse.getVille(), adresse2.getVille());
        assertEquals(adresse.getProvince(), adresse2.getProvince());
        assertEquals(adresse.getCodePostal(), adresse2.getCodePostal());


    }


    @Test
    public void addAdresse(){

        Log.i("RPI", "Ajout d'une Adresse");

        Context appContext = InstrumentationRegistry.getTargetContext();

        AdresseSQLHelper adresseSQLHelper =  AdresseSQLHelper.getInstance(appContext) ;//(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = adresseSQLHelper.getWritableDatabase();

        assertNotNull(database);

        int nbAdresses = adresseSQLHelper.getAdressesCount();

        List<Adresse> adresses = adresseSQLHelper.getAllAdresses();

        Log.i("RPI", "Liste des adresses avant insertion");

        for (Adresse adresse1 : adresses) {
            Log.i("RPI", "Adresse: " + adresse1);
        }

        Adresse adresse = new Adresse();
        adresse.setNumero(2519);
        adresse.setRue("Pearl Street");
        adresse.setVille("Streetsville");
        adresse.setProvince("ON");
        adresse.setCodePostal("L5M 1X2");

        Log.i("RPI", "Ajout d'une adresse: " + adresse);

        long result = adresseSQLHelper.addAdresse(adresse);

        int nbAdresses2 = adresseSQLHelper.getAdressesCount();

        assertEquals(nbAdresses2, nbAdresses + 1);

        Log.i("RPI", "Ajout de l'Adresse : " + result);

        adresses = adresseSQLHelper.getAllAdresses();

        Log.i("RPI", "Liste des adresses après insertion");

        for (Adresse adresse1 : adresses) {
            Log.i("RPI", "Adresse: " + adresse1);
        }



            database.close();

    }

    @Test
    public void deleteAdresse() {

        Log.i("RPI", "Effacer une Adresse");
        Context appContext = InstrumentationRegistry.getTargetContext();

        AdresseSQLHelper adresseSQLHelper =  AdresseSQLHelper.getInstance(appContext) ;//(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = adresseSQLHelper.getWritableDatabase();

        assertNotNull(database);


        int nbAdresses = adresseSQLHelper.getAdressesCount();

        List <Adresse> adresses = adresseSQLHelper.getAllAdresses();

        assert(adresses.size() > 0 );

        int index =  adresses.get(adresses.size() -1).getId();

        Log.i("RPI", "Liste des adresses avant suppression");

        for (Adresse adresse1 : adresses) {
            Log.i("RPI", "Adresse: " + adresse1);
        }



        Log.i("RPI: ", "nbAdresses: " + nbAdresses);
        int nbAdresses2 = 0;

        Adresse adresse = adresseSQLHelper.getAdresseById(index);

        if(adresse != null) {

            int result = adresseSQLHelper.deleteAdresse(adresse);

            nbAdresses2 = adresseSQLHelper.getAdressesCount();

            assertEquals(nbAdresses2, nbAdresses - 1);

            Log.i("RPI: ", "nbAdresses après suppression: " + nbAdresses2);

            Log.i("RPI", "Liste des adresses après suppression");
            adresses = adresseSQLHelper.getAllAdresses();

            for (Adresse adresse1 : adresses) {
                Log.i("RPI", "Adresse: " + adresse1);
            }


        }

    }


    @Test
    public void updateAdresse() {
        Log.i("RPI", "Mettre à jour d'une Adresse");
        Context appContext = InstrumentationRegistry.getTargetContext();

        AdresseSQLHelper adresseSQLHelper =  AdresseSQLHelper.getInstance(appContext) ;//(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = adresseSQLHelper.getWritableDatabase();

        assertNotNull(database);

        int nbAdresses = adresseSQLHelper.getAdressesCount();

        List <Adresse> adresses = adresseSQLHelper.getAllAdresses();

        assertNotNull(adresses);

        assert(adresses.size() > 0);

        Adresse adresse =  adresses.get(0);
        Log.i("RPI", "Adresse avant mise à jour: " + adresse);

        adresse.setNumero(2762);
        adresse.setRue("James Street");
        adresse.setVille("Aldergrove");
        adresse.setProvince("BC");
        adresse.setCodePostal("V5W 3P7");




        adresseSQLHelper.updateAdresse(adresse);

        int nbAdresses2 = adresseSQLHelper.getAdressesCount();

        Adresse adresse2 =  adresseSQLHelper.getAdresseById(adresse.getId());


        Log.i("RPI", "Adresse après mise à jour: " + adresse);

        assertEquals(adresse.getNumero(), adresse2.getNumero());
        assertEquals(adresse.getRue(), adresse2.getRue());
        assertEquals(adresse.getVille(), adresse2.getVille());
        assertEquals(adresse.getProvince(), adresse2.getProvince());
        assertEquals(adresse.getCodePostal(), adresse2.getCodePostal());

        assertEquals(nbAdresses2, nbAdresses);


        Log.i("RPI", "Liste des adresses après mise à jour");
        for (Adresse adresse3 : adresses) {
            Log.i("RPI", "Adresse: " + adresse3);
        }

    }


}
