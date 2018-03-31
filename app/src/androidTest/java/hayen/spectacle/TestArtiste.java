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

//import com.ift2905.reservation.database.dao.ArtisteSQLHelper;
//import com.ift2905.reservation.database.dao.Constant;
//import com.ift2905.reservation.database.entities.Artiste;

import hayen.spectacle.database.dao.ArtisteSQLHelper;
import hayen.spectacle.database.dao.Constant;
import hayen.spectacle.database.entities.Artiste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



@RunWith(AndroidJUnit4.class)
public class TestArtiste {


    @Test
    public void getArtiste() {

        Log.i("RPI", "Récupérer un artiste");

        Context appContext = InstrumentationRegistry.getTargetContext();

        ArtisteSQLHelper dbHelper = new ArtisteSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbArtistes = dbHelper.getArtistesCount();

        assert(nbArtistes > 0);

        List<Artiste> artistes = dbHelper.getAllArtistes();

        Artiste artiste =  dbHelper.getArtisteByName(artistes.get(artistes.size() - 1));

        assertNotNull(artiste);

        Artiste artiste2 =  dbHelper.getArtisteById(artiste.getId());


        assertEquals(artiste.getId(), artiste2.getId());
        assertEquals(artiste.getPrenom(), artiste2.getPrenom());
        assertEquals(artiste.getNom(), artiste2.getNom());


    }


    @Test
    public void addArtiste(){

        Log.i("RPI", "Ajout d'un artiste");

        Context appContext = InstrumentationRegistry.getTargetContext();

        ArtisteSQLHelper dbHelper = new ArtisteSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbArtistes = dbHelper.getArtistesCount();

        List<Artiste> artistes = dbHelper.getAllArtistes();

        Log.i("RPI", "Liste des artistes avant insertion");

        for (Artiste artiste1 : artistes) {
            Log.i("RPI", "Artiste: " + artiste1);
        }


        Artiste artiste = new Artiste("Alfred", "Brendel");


        Log.i("RPI", "Ajout d'un artiste: " + artiste);

        long result = dbHelper.addArtiste(artiste);

        int nbArtistes2 = dbHelper.getArtistesCount();

        assertEquals(nbArtistes2, nbArtistes + 1);

        Log.i("RPI", "Ajout de l'artiste : " + result);

        artistes = dbHelper.getAllArtistes();

        Log.i("RPI", "Liste des artistes après insertion");

        for (Artiste artiste1 : artistes) {
            Log.i("RPI", "Artiste: " + artiste1);
        }



            database.close();

    }

    @Test
    public void deleteArtiste() {

        Log.i("RPI", "Effacer un artiste");
        Context appContext = InstrumentationRegistry.getTargetContext();

        ArtisteSQLHelper dbHelper = new ArtisteSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);


        int nbArtistes = dbHelper.getArtistesCount();

        List <Artiste> artistes = dbHelper.getAllArtistes();

        assert(artistes.size() > 0 );

        int index =  artistes.get(artistes.size() -1).getId();

        Log.i("RPI", "Liste des artistes avant suppression");

        for (Artiste artiste1 : artistes) {
            Log.i("RPI", "Artiste: " + artiste1);
        }



        Log.i("RPI: ", "nbArtistes: " + nbArtistes);
        int nbArtistes2 = 0;

        Artiste artiste = dbHelper.getArtisteById(index);

        if(artiste != null) {

            int result = dbHelper.deleteArtiste(artiste);

            nbArtistes2 = dbHelper.getArtistesCount();

            assertEquals(nbArtistes2, nbArtistes - 1);

            Log.i("RPI", "Liste des artistes après suppression");
            Log.i("RPI: ", "nbArtistes2: " + nbArtistes2);
            for (Artiste artiste1 : artistes) {
                Log.i("RPI", "Artiste: " + artiste1);
            }


        }

    }


    @Test
    public void updateArtiste() {
        Log.i("RPI", "Mettre à jour un artiste");
        Context appContext = InstrumentationRegistry.getTargetContext();

        ArtisteSQLHelper dbHelper = new ArtisteSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbArtistes = dbHelper.getArtistesCount();

        List <Artiste> artistes = dbHelper.getAllArtistes();

        assertNotNull(artistes);

        assert(artistes.size() > 0);

        String prenom =  "Miles";
        String nom = "Davis";

        Artiste artiste =  artistes.get(0);


        Log.i("RPI", "Artiste avant mise à jour: " + artiste);


        artiste.setNom(nom);
        artiste.setPrenom(prenom);


        dbHelper.updateArtiste(artiste);

        int nbArtistes2 = dbHelper.getArtistesCount();

        Artiste artiste2 =  dbHelper.getArtisteById(artiste.getId());


        Log.i("RPI", "Artiste après mise à jour: " + artiste);

        assert(artiste.getNom().equals(artiste2.getNom()) && artiste.getPrenom().equals(artiste2.getPrenom()));


        assertEquals(nbArtistes2, nbArtistes);


    }


}
