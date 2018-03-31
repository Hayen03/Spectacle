//package com.ift2905.reservation;
package hayen.spectacle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

//import com.ift2905.reservation.database.dao.AdresseSQLHelper;
//import com.ift2905.reservation.database.dao.ArtisteSQLHelper;
//import com.ift2905.reservation.database.dao.Constant;
//import com.ift2905.reservation.database.dao.UtilisateurSQLHelper;
//import com.ift2905.reservation.database.entities.Adresse;
//import com.ift2905.reservation.database.entities.Artiste;
//import com.ift2905.reservation.database.entities.Utilisateur;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import hayen.spectacle.database.dao.AdresseSQLHelper;
import hayen.spectacle.database.dao.Constant;
import hayen.spectacle.database.dao.UtilisateurSQLHelper;
import hayen.spectacle.database.entities.Adresse;
import hayen.spectacle.database.entities.Utilisateur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class TestUtiisateur {


    @Test
    public void getUtilisateur() {

        Log.i("RPI", "Récupérer un utilisateur");

        Context appContext = InstrumentationRegistry.getTargetContext();

        UtilisateurSQLHelper dbHelper = new UtilisateurSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbUtilisateurs = dbHelper.getUtilisateursCount();

        assert(nbUtilisateurs > 0);

        Log.i("RPI", "nb  utilisateurs: " + nbUtilisateurs);

        List<Utilisateur> utilisateurs = dbHelper.getAllUtilisateurs();

        Log.i("RPI", "nb  utilisateurs: " + utilisateurs.size());

        Log.i("RPI", "Liste des utilisateurs après insertion");

        for (Utilisateur utilisateur : utilisateurs) {
            Log.i("RPI", "utilisateur: " + utilisateur);
        }


        Utilisateur utilisateur =  dbHelper.getUtilisateurById(utilisateurs.get(utilisateurs.size()-1).getId());

        assertNotNull(utilisateur);

        Utilisateur utilisateur2 =  dbHelper.getUtilisateurByName(utilisateur);


        assertEquals(utilisateur.getId(), utilisateur2.getId());


        Utilisateur utilisateur3 =  dbHelper.getUtilisateurByLogin(utilisateur);


        assertEquals(utilisateur.getId(), utilisateur3.getId());


        Utilisateur utilisateur4 =  dbHelper.validateLogin(utilisateur);


        assertEquals(utilisateur.getId(), utilisateur4.getId());



    }


    @Test
    public void addUtilisateur(){

        Log.i("RPI", "Ajout d'un utilisateur");


        Context appContext = InstrumentationRegistry.getTargetContext();

        UtilisateurSQLHelper dbHelper = new UtilisateurSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbUtilisateurs = dbHelper.getUtilisateursCount();

        List<Utilisateur> utilisateurs = dbHelper.getAllUtilisateurs();

        Log.i("RPI", "Liste des utilisateurs avant insertion");

        for (Utilisateur utilisateur1 : utilisateurs) {
            Log.i("RPI", "Utilisateur: " + utilisateur1);
        }


        Adresse adresse =  new Adresse();

        adresse.setNumero(2955);
        adresse.setRue("Saskatchewan Dr");
        adresse.setVille("Québec");
        adresse.setProvince("QC");
        adresse.setCodePostal("S4P 3Y2");

        AdresseSQLHelper adresseSQLHelper =  new AdresseSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

       SQLiteDatabase database2 = dbHelper.getWritableDatabase();

        assertNotNull(database2);

        long id =  adresseSQLHelper.addAdresse(adresse);

        assert(id > 0);



        Utilisateur utilisateur = new Utilisateur("Alaine", "Brasseur", "Ficheres", "eeY2Xeed1", "AlaineBrasseur@rhyta.com", "418-688-8470", (int)id);


        Log.i("RPI", "Ajout d'un utilisateur: " + utilisateur);

        long result = dbHelper.addUtilisateur(utilisateur);

        int nbUtilisateurs2 = dbHelper.getUtilisateursCount();

        assertEquals(nbUtilisateurs2, nbUtilisateurs + 1);

        Log.i("RPI", "Ajout de l'utilisateur : " + result);

        utilisateurs = dbHelper.getAllUtilisateurs();

        Log.i("RPI", "Liste des utilisateurs après insertion");

        for (Utilisateur utilisateur2 : utilisateurs) {
            Log.i("RPI", "utilisateur: " + utilisateur2);
        }


            database.close();
            database2.close();
    }



    @Test
    public void deleteUtilisateur() {

        Log.i("RPI", "Effacer un utilisateur");
        Context appContext = InstrumentationRegistry.getTargetContext();

        UtilisateurSQLHelper dbHelper = new UtilisateurSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);


        int nbUtilisateurs = dbHelper.getUtilisateursCount();

        List <Utilisateur> utilisateurs = dbHelper.getAllUtilisateurs();

        assert(utilisateurs.size() > 0 );

        int index =  utilisateurs.get(utilisateurs.size() -1).getId();

        Log.i("RPI", "Liste des utilisateurs avant suppression");

        for (Utilisateur utilisateur : utilisateurs) {
            Log.i("RPI", "Utilisateur: " + utilisateur);
        }



        Log.i("RPI: ", "nbUtilisateurs: " + nbUtilisateurs);
        int nbUtilisateurs2= 0;

        Utilisateur utilisateur = dbHelper.getUtilisateurById(index);

        if(utilisateur != null) {

            int result = dbHelper.deleteUtilisateur(utilisateur);

            nbUtilisateurs2 = dbHelper.getUtilisateursCount();

            assertEquals(nbUtilisateurs2, nbUtilisateurs - 1);

            Log.i("RPI", "Liste des utilisateurs après suppression");
            Log.i("RPI: ", "nbUtilisateurs2: " + nbUtilisateurs2);

            List <Utilisateur> utilisateurs2 = dbHelper.getAllUtilisateurs();
            for (Utilisateur utilisateur2 : utilisateurs2) {
                Log.i("RPI", "Utilisateur: " + utilisateur2);
            }


        }

    }


    @Test
    public void updateUtilisateur() {
        Log.i("RPI", "Mettre à jour un utilisateur");
        Context appContext = InstrumentationRegistry.getTargetContext();

        UtilisateurSQLHelper dbHelper = new UtilisateurSQLHelper(appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbUtilisateurs = dbHelper.getUtilisateursCount();

        List <Utilisateur> utilisateurs = dbHelper.getAllUtilisateurs();

        assertNotNull(utilisateurs);

        assert(utilisateurs.size() > 0);

        String prenom =  "Hugues";
        String nom = "Mailhot";

        Utilisateur utilisateur =  utilisateurs.get(0);


        Log.i("RPI", "utilisateur avant mise à jour: " + utilisateur);


        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);


        dbHelper.updateUtilisateur(utilisateur);

        int nbUtilisateur2 = dbHelper.getUtilisateursCount();

        Utilisateur utilisateur2=  dbHelper.getUtilisateurById(utilisateur.getId());


        Log.i("RPI", "Artiste après mise à jour: " + utilisateur);

        assert(utilisateur.getNom().equals(utilisateur2.getNom()) && utilisateur.getPrenom().equals(utilisateur2.getPrenom()));

        
        assertEquals(nbUtilisateurs, nbUtilisateur2);


    }


}
