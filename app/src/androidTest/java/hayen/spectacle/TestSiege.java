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
//import com.ift2905.reservation.database.dao.SiegeSQLHelper;
//import com.ift2905.reservation.database.entities.Siege;

import hayen.spectacle.database.dao.SiegeSQLHelper;
import hayen.spectacle.database.data.Siege;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class TestSiege {


    @Test
    public void getSiege() {

        Log.i("RPI", "Récupérer une Siege");

        Context appContext = InstrumentationRegistry.getTargetContext();

        SiegeSQLHelper dbHelper = SiegeSQLHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION)
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        assertNotNull(database);

        int nbSieges = dbHelper.getSiegesCount();

        assert(nbSieges > 0);

        Log.i("RPI", "nb  Sieges: " + nbSieges);

        List<Siege> sieges = dbHelper.getAllSieges();

        Log.i("RPI", "nb  Sieges: " + sieges.size());

        assertEquals(nbSieges, sieges.size());

        for (Siege siege : sieges) {
            Log.i("RPI", "siege: " + siege);
        }


        Siege siege =  dbHelper.getSiegeById(sieges.get(sieges.size()-1).getId());

        assertNotNull(siege);

        Siege siege2 =  dbHelper.getSiegeById(siege.getId());


        assertEquals(siege.getId(), siege2.getId());


    }


    @Test
    public void addSiege(){

        Log.i("RPI", "Ajout d'une Siege");


        Context appContext = InstrumentationRegistry.getTargetContext();

        SiegeSQLHelper dbHelper = SiegeSQLHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION)
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbSieges = dbHelper.getSiegesCount();

        List<Siege> sieges = dbHelper.getAllSieges();

        Log.i("RPI", "Liste des sieges avant insertion");

        for (Siege siege1 : sieges) {
            Log.i("RPI", "siege: " + siege1);
        }



        Siege siege = new Siege();
        siege.setRangee("BG");
        siege.setColonne(2);
        siege.setSectionId(4);


        Log.i("RPI", "Ajout d'une siege: " + siege);

        long result = dbHelper.addSiege(siege);

        int nbSieges2 = dbHelper.getSiegesCount();

        assertEquals(nbSieges2, nbSieges + 1);

        Log.i("RPI", "Ajout de la siege : " + result);

        sieges = dbHelper.getAllSieges();

        assertEquals(nbSieges2, sieges.size());


        Log.i("RPI", "Liste des sieges après insertion");

        for (Siege siege2 : sieges) {
            Log.i("RPI", "siege: " + siege2);
        }


            database.close();

    }


    @Test
    public void updateSiege() {
        Log.i("RPI", "Mettre à jour un Siege");
        Context appContext = InstrumentationRegistry.getTargetContext();

        SiegeSQLHelper dbHelper = SiegeSQLHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION)
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbSieges = dbHelper.getSiegesCount();

        List <Siege> sieges = dbHelper.getAllSieges();

        assertNotNull(sieges);

        assert(sieges.size() > 0);

        Siege siege =  sieges.get(0);

        Log.i("RPI", "siege avant mise à jour: " + siege);

        siege.setRangee("BG");

        dbHelper.updateSiege(siege);

        int nbSieges2 = dbHelper.getSiegesCount();

        Siege siege2=  dbHelper.getSiegeById(siege.getId());


        Log.i("RPI", "Artiste après mise à jour: " + siege);

        assertEquals(siege.getId(), siege2.getId());
        assertEquals(siege.getRangee(), siege2.getRangee());
        assertEquals(siege.getColonne(), siege2.getColonne());
        assertEquals(siege.getSectionId(), siege2.getSectionId());


        assertEquals(nbSieges, nbSieges2);


    }

    @Test
    public void deleteSiege() {

        Log.i("RPI", "Effacer un Siege");
        Context appContext = InstrumentationRegistry.getTargetContext();

        SiegeSQLHelper dbHelper = SiegeSQLHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION)
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);


        int nbSieges = dbHelper.getSiegesCount();

        List <Siege> sieges = dbHelper.getAllSieges();

        assert(sieges.size() > 0 );

        int index =  sieges.get(sieges.size() -1).getId();

        Log.i("RPI", "Liste des sieges avant suppression");

        for (Siege siege : sieges) {
            Log.i("RPI", "siege: " + siege);
        }



        Log.i("RPI: ", "nbSieges: " + nbSieges);
        int nbSieges2 = 0;

        Siege siege = dbHelper.getSiegeById(index);

        if(siege != null) {

            int result = dbHelper.deleteSiege(siege);

            nbSieges2 = dbHelper.getSiegesCount();

            assertEquals(nbSieges2, nbSieges - 1);

            Log.i("RPI", "Liste des sieges après suppression");
            Log.i("RPI: ", "nbSieges: " + nbSieges2);

            List <Siege> sieges2 = dbHelper.getAllSieges();
            for (Siege siege2 : sieges2) {
                Log.i("RPI", "siege: " + siege2);
            }


        }

    }




}
