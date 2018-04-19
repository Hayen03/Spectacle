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


//import com.ift2905.reservation.database.dao.GenreSQLHelper;
//import com.ift2905.reservation.database.dao.Constant;
//import com.ift2905.reservation.database.entities.Genre;

import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Genre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class TestGenre {


    @Test
    public void getGenre() {

        Log.i("RPI", "Récupérer un Genre");

        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbGenres = dbHelper.getGenresCount();

        Log.i("RPI", "genre count: " + nbGenres);
        assert(nbGenres > 0);

        List<Genre> genres = dbHelper.getAllGenres();
        for (Genre genre1 : genres) {
            Log.i("RPI", "Genre: " + genre1);
        }


        Genre genre =  dbHelper.getGenreById(genres.size() / 2);

        Log.i("RPI", "Genre: " + genre);



        assertNotNull(genre);

        Genre genre2 =  dbHelper.getGenreById(genre.getId());


        assertEquals(genre.getId(), genre2.getId());
        assertEquals(genre.getNom(), genre2.getNom());

    }


    @Test
    public void addGenre(){

        Log.i("RPI", "Ajout d'un Genre");

        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbGenres = dbHelper.getGenresCount();

        List<Genre> genres = dbHelper.getAllGenres();

        Log.i("RPI", "Liste des Genres avant insertion");

        for (Genre genre1 : genres) {
            Log.i("RPI", "Genre: " + genre1);
        }


        Genre genre = new Genre();
        genre.setNom("Chant grégorien");


        Log.i("RPI", "Ajout d'un Genre: " + genre);

        long result = dbHelper.addGenre(genre);

        int nbGenres2 = dbHelper.getGenresCount();

        assertEquals(nbGenres2, nbGenres + 1);

        Log.i("RPI", "Ajout du Genre : " + result);

        genres = dbHelper.getAllGenres();

        Log.i("RPI", "Liste des Genres après insertion");

        for (Genre genre1 : genres) {
            Log.i("RPI", "Genre: " + genre1);
        }



        database.close();

    }

    @Test
    public void deleteGenre() {

        Log.i("RPI", "Effacer une Genre");
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);


        int nbGenres = dbHelper.getGenresCount();

        List <Genre> genres = dbHelper.getAllGenres();

        assert(genres.size() > 0 );

        int index =  genres.get(genres.size() -1).getId();

        Log.i("RPI", "Liste des Genres avant suppression");

        for (Genre genre1 : genres) {
            Log.i("RPI", "Genre: " + genre1);
        }


        Log.i("RPI: ", "nbGenres: " + nbGenres);
        int nbGenres2 = 0;

        Genre genre = dbHelper.getGenreById(index);

        if(genre != null) {

            int result = dbHelper.deleteGenre(genre);

            nbGenres2 = dbHelper.getGenresCount();

            assertEquals(nbGenres2, nbGenres - 1);

            Log.i("RPI", "Liste des Genres après suppression");
            Log.i("RPI: ", "nbGenres2: " + nbGenres2);
            for (Genre genre1 : genres) {
                Log.i("RPI", "Genre: " + genre1);
            }


        }

    }


    @Test
    public void updateGenre() {
        Log.i("RPI", "Mettre à jour un Genre");
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbGenres = dbHelper.getGenresCount();

        List <Genre> genres = dbHelper.getAllGenres();

        assertNotNull(genres);

        assert(genres.size() > 0);

        String nom =  "Métal classique";

        Genre genre =  genres.get(genres.size() - 1);


        Log.i("RPI", "Genre avant mise à jour: " + genre);


        genre.setNom(nom);


        dbHelper.updateGenre(genre);

        int nbGenres2 = dbHelper.getGenresCount();

        Genre genre2 =  dbHelper.getGenreById(genre.getId());


        Log.i("RPI", "Genre après mise à jour: " + genre);

        assert(genre.getNom().equals(genre2.getNom()));

    }


}
