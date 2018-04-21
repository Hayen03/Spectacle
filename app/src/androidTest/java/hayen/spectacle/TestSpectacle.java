//package com.ift2905.reservation;
package hayen.spectacle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import com.ift2905.reservation.database.dao.Constant;
//import com.ift2905.reservation.database.dao.SpectacleSQLHelper;
//import com.ift2905.reservation.database.entities.Spectacle;


import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Artiste;
import hayen.spectacle.data.data.Siege;
import hayen.spectacle.data.data.Spectacle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class TestSpectacle {


    @Test
    public void getSpectacle() {

        Log.i("RPI", "Récupérer une Spectacle");

        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION)
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        assertNotNull(database);

        int nbSpectacles = dbHelper.getSpectaclesCount();

        assert(nbSpectacles > 0);

        Log.i("RPI", "nb  Spectacles: " + nbSpectacles);

        List<Spectacle> spectacles = dbHelper.getAllSpectacles();

        Log.i("RPI", "nb  Spectacles: " + spectacles.size());

        assertEquals(nbSpectacles, spectacles.size());

        for (Spectacle spectacle : spectacles) {
            Log.i("RPI", "spectacle: " + spectacle);
        }


        Spectacle spectacle =  dbHelper.getSpectacleById(spectacles.get(spectacles.size()-1).getId());

        assertNotNull(spectacle);

        Spectacle spectacle2 =  dbHelper.getSpectacleById(spectacle.getId());


        assertEquals(spectacle.getId(), spectacle2.getId());


        Calendar cal = Calendar.getInstance();

        SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
        String time =  formatter.format(cal.getTime());
        Log.i("RPI", "date:  " + time);

       spectacles = dbHelper.getSpectaclesByDate(time);

        Log.i("RPI", "spectacle:  " + spectacles);
       Log.i("RPI", "nb  Spectacles size: " + spectacles.size());

      //  assertEquals(nbSpectacles, spectacles.size());

        for (Spectacle spectacle5 : spectacles) {
            Log.i("RPI", "spectacle: " + spectacle5);
            Log.i("RPI", "description: " + spectacle5.getDescription());
        }

        Log.i("RPI", "get all spectacles");
        spectacles =  dbHelper.getAllSpectacles();
        for (Spectacle spectacle6 : spectacles) {
            Log.i("RPI", "spectacle: " + spectacle6);
            Log.i("RPI", "description: " + spectacle6.getDescription());
        }
    }

    @Test
    public void getArtistesBySpectacle(){

        Log.i("RPI", "Récupérer les artiste d'un Spectacle");

        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper =  DatabaseHelper.getInstance(appContext);

        int spectacleId =  3;

        List<Artiste> artistes = dbHelper.getAllArtistesBySpectacleId(spectacleId);

        assertNotNull(artistes);

        assert(artistes.size() > 0);

        for (Artiste artiste : artistes) {
            Log.i("RPI", "artist: " + artiste);
        }

    }


    @Test
    public void addSpectacle(){

        Log.i("RPI", "Ajout d'une Spectacle");


        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION)
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbSpectacles = dbHelper.getSpectaclesCount();

        List<Spectacle> spectacles = dbHelper.getAllSpectacles();

        Log.i("RPI", "Liste des spectacles avant insertion");

        for (Spectacle spectacle1 : spectacles) {
            Log.i("RPI", "spectacle: " + spectacle1);
        }


        Artiste artiste = new Artiste();
        artiste.setNom("Presley");
        artiste.setPrenom("Elvis");


        long result = dbHelper.replaceArtiste(artiste);



        Log.i("RPI", "Ajout de l'artiste : " + artiste + "; result: "+ result);

//
//        Spectacle spectacle = new Spectacle();
//        spectacle.setTitre("Martine Saint-Clair - Le grand retour ");
//        spectacle.setDate("2018-06-10 20:00:00");
//        spectacle.setGenreId(6);
//        spectacle.setSalleId(1);
//        spectacle.setDuree(115);


        if(result > 0) {

            Spectacle spectacle = new Spectacle();
            spectacle.setTitre("Elvis Presley - He's Back! ");
            spectacle.setDate("2018-07-12 20:00");
            spectacle.setGenreId(4);
            spectacle.setSalleId(1);
            spectacle.setDuree(90);
            long result2 = dbHelper.replaceSpectacle(spectacle);
            Log.i("RPI", "Ajout d'un spectacle: " + spectacle + "; result: " + result2);

            if(result2 > 0) {

                artiste.setId((int)result);
                spectacle.setId((int) result2);
                List<Artiste> artistes = new ArrayList<>();
                artistes.add(artiste);

                result = dbHelper.addSpectacleArtistes(spectacle, artistes);
                Log.i("RPI", "Ajout de l'artiste et spectacle dans SA table ; result: " + result);


            }
        }
        int nbSpectacles2 = dbHelper.getSpectaclesCount();

        assertEquals(nbSpectacles2, nbSpectacles + 1);



        spectacles = dbHelper.getAllSpectacles();

        assertEquals(nbSpectacles2, spectacles.size());


        Log.i("RPI", "Liste des spectacles après insertion");

        for (Spectacle spectacle2 : spectacles) {
            Log.i("RPI", "spectacle: " + spectacle2);
        }


            database.close();

    }


    @Test
    public void updateSpectacle() {
        Log.i("RPI", "Mettre à jour un Spectacle");
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION)
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbSpectacles = dbHelper.getSpectaclesCount();

        List <Spectacle> spectacles = dbHelper.getAllSpectacles();

        assertNotNull(spectacles);

        assert(spectacles.size() > 0);

        Log.i("RPI", "Liste des spectacles avant mise à jour: ");
        for (Spectacle spectacle : spectacles) {
            Log.i("RPI", "spectacle: " + spectacle);
        }



        Spectacle spectacle =  spectacles.get(spectacles.size()-1); //on update le dernier spectacle

        Log.i("RPI", "Spectacle avant la mise à jour: " + spectacle);


        spectacle.setTitre("Frank Sinatra - Beyond the Grave Tour");
        spectacle.setDate("2018-08-23 19:30:00");
        spectacle.setDuree(103);
        spectacle.setGenreId(5);
        spectacle.setSalleId(1);

        dbHelper.updateSpectacle(spectacle);



        int nbSpectacles2 = dbHelper.getSpectaclesCount();

        Spectacle spectacle2 =  dbHelper.getSpectacleById(spectacle.getId());

        Log.i("RPI", "Spectacle avant la mise à jour: " + spectacle2);



        List <Spectacle> spectacles2 = dbHelper.getAllSpectacles();

        Log.i("RPI", "Liste des spectacles après mise à jour");


        for (Spectacle spectacle3 : spectacles2) {
            Log.i("RPI", "spectacle: " + spectacle3);
        }

        assertEquals(spectacle.getId(), spectacle2.getId());
        assertEquals(spectacle.getDate(), spectacle2.getDate());
        assertEquals(spectacle.getTitre(), spectacle2.getTitre());
        assertEquals(spectacle.getGenreId(), spectacle2.getGenreId());
        assertEquals(spectacle.getSalleId(), spectacle2.getSalleId());


        assertEquals(nbSpectacles, nbSpectacles2);


    }

    @Test
    public void deleteSpectacle() {

        Log.i("RPI", "Effacer un Spectacle");
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION)
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);


        int nbSpectacles = dbHelper.getSpectaclesCount();

        List <Spectacle> spectacles = dbHelper.getAllSpectacles();

        assert(spectacles.size() > 0 );

        int index =  spectacles.get(spectacles.size() -1).getId();

        Log.i("RPI", "Liste des spectacles avant suppression");

        for (Spectacle spectacle : spectacles) {
            Log.i("RPI", "spectacle: " + spectacle);
        }



        Log.i("RPI: ", "nbSpectacles: " + nbSpectacles);
        int nbSpectacles2 = 0;

        index = 11;
        Spectacle spectacle = dbHelper.getSpectacleById(index);

        if(spectacle != null) {

            int result = dbHelper.deleteSpectacle(spectacle);

            nbSpectacles2 = dbHelper.getSpectaclesCount();

            assertEquals(nbSpectacles2, nbSpectacles - 1);

            Log.i("RPI", "Liste des spectacles après suppression");
            Log.i("RPI: ", "nbSpectacles: " + nbSpectacles2);

            List <Spectacle> spectacles2 = dbHelper.getAllSpectacles();
            for (Spectacle spectacle2 : spectacles2) {
                Log.i("RPI", "spectacle: " + spectacle2);
            }


        }

    }
    @Test
    public void getSpectacleSection() {

        Log.i("RPI", "Récupérer une Spectacle");

        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION)
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        assertNotNull(database);



        List<Siege> sieges = dbHelper.getFreeSiegeBySections(1, 2, 4);

        for (Siege siege: sieges) {
            Log.i("RPI", siege.toString());
      //      dbHelper.updateSpectacleSiege(1, siege.getId(), 1);
        }







    }




}
