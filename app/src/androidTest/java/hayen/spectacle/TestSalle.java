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

//import com.ift2905.reservation.database.dao.AdresseSQLHelper;
//import com.ift2905.reservation.database.dao.SalleSQLHelper;
//import com.ift2905.reservation.database.dao.Constant;
//import com.ift2905.reservation.database.entities.Adresse;
//import com.ift2905.reservation.database.entities.Salle;


import hayen.spectacle.database.dao.AdresseSQLHelper;
import hayen.spectacle.database.dao.Constant;
import hayen.spectacle.database.dao.SalleSQLHelper;
import hayen.spectacle.database.entities.Adresse;
import hayen.spectacle.database.entities.Salle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



@RunWith(AndroidJUnit4.class)
public class TestSalle {


    @Test
    public void getSalle() {

        Log.i("RPI", "Récupérer une Salle");

        Context appContext = InstrumentationRegistry.getTargetContext();

        SalleSQLHelper dbHelper = new SalleSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbSalles = dbHelper.getSallesCount();

        assert(nbSalles > 0);

        Log.i("RPI", "nb  Salles: " + nbSalles);

        List<Salle> salles = dbHelper.getAllSalles();

        Log.i("RPI", "nb  salles: " + salles.size());

        assertEquals(nbSalles, salles.size());

        for (Salle salle : salles) {
            Log.i("RPI", "salle: " + salle);
        }


        Salle salle =  dbHelper.getSalleById(salles.get(salles.size()-1).getId());

        assertNotNull(salle);

        Salle salle2 =  dbHelper.getSalleById(salle.getId());


        assertEquals(salle.getId(), salle2.getId());


    }


    @Test
    public void addSalle(){

        Log.i("RPI", "Ajout d'une salle");


        Context appContext = InstrumentationRegistry.getTargetContext();

        SalleSQLHelper dbHelper = new SalleSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbSalles = dbHelper.getSallesCount();

        List<Salle> salles = dbHelper.getAllSalles();

        Log.i("RPI", "Liste des salles avant insertion");

        for (Salle salle1 : salles) {
            Log.i("RPI", "salle: " + salle1);
        }


        Adresse adresse =  new Adresse();

        adresse.setNumero(2756);
        adresse.setRue("Saint-Honoré");
        adresse.setVille("Montréal");
        adresse.setProvince("QC");
        adresse.setCodePostal("H4I 2X9");

        AdresseSQLHelper adresseSQLHelper =  new AdresseSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

       SQLiteDatabase database2 = dbHelper.getWritableDatabase();

        assertNotNull(database2);

        long adresseId =  adresseSQLHelper.addAdresse(adresse);

        assert(adresseId > 0);

        Salle salle = new Salle();
        salle.setNom("Théâtre national de la Science");
        salle.setTelephone("514-222-8888");
        salle.setFax("514-222-9999");
        salle.setCourriel("infos@tns.ca");
       salle.setAdresseId((int)adresseId);
       salle.setDescription("Nouveau théâtre fondé en 2018");



        Log.i("RPI", "Ajout d'un salle: " + salle);

        long result = dbHelper.addSalle(salle);

        int nbSalles2 = dbHelper.getSallesCount();

        assertEquals(nbSalles2, nbSalles + 1);

        Log.i("RPI", "Ajout de la salle : " + result);

        salles = dbHelper.getAllSalles();

        Log.i("RPI", "Liste des salles après insertion");

        for (Salle salle2 : salles) {
            Log.i("RPI", "salle: " + salle2);
        }


            database.close();
            database2.close();
    }


    @Test
    public void updateSalle() {
        Log.i("RPI", "Mettre à jour un salle");
        Context appContext = InstrumentationRegistry.getTargetContext();

        SalleSQLHelper dbHelper = new SalleSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbSalles = dbHelper.getSallesCount();

        List <Salle> salles = dbHelper.getAllSalles();

        assertNotNull(salles);

        assert(salles.size() > 0);

        Salle salle =  salles.get(0);

        Log.i("RPI", "salle avant mise à jour: " + salle);

        salle.setFax("null");

        dbHelper.updateSalle(salle);

        int nbSalles2 = dbHelper.getSallesCount();

        Salle salle2=  dbHelper.getSalleById(salle.getId());


        Log.i("RPI", "Artiste après mise à jour: " + salle);

        assertEquals(salle.getNom(), salle2.getNom());
        assertEquals(salle.getTelephone(), salle2.getTelephone());
        assertEquals(salle.getFax(), salle2.getFax());
        assertEquals(salle.getCourriel(), salle2.getCourriel());
        assertEquals(salle.getAdresseId(), salle2.getAdresseId());
        assertEquals(salle.getDescription(), salle2.getDescription());

        assertEquals(nbSalles, nbSalles2);


    }

    @Test
    public void deleteSalle() {

        Log.i("RPI", "Effacer un salle");
        Context appContext = InstrumentationRegistry.getTargetContext();

        SalleSQLHelper dbHelper = new SalleSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);


        int nbSalles = dbHelper.getSallesCount();

        List <Salle> salles = dbHelper.getAllSalles();

        assert(salles.size() > 0 );

        int index =  salles.get(salles.size() -1).getId();

        Log.i("RPI", "Liste des salles avant suppression");

        for (Salle salle : salles) {
            Log.i("RPI", "Salle: " + salle);
        }



        Log.i("RPI: ", "nbSalles: " + nbSalles);
        int nbSalles2= 0;

        Salle salle = dbHelper.getSalleById(index);

        if(salle != null) {

            int result = dbHelper.deleteSalle(salle);

            nbSalles2 = dbHelper.getSallesCount();

            assertEquals(nbSalles2, nbSalles - 1);

            Log.i("RPI", "Liste des salles après suppression");
            Log.i("RPI: ", "nbSalles2: " + nbSalles2);

            List <Salle> salles2 = dbHelper.getAllSalles();
            for (Salle salle2 : salles2) {
                Log.i("RPI", "Salle: " + salle2);
            }


        }

    }




}
