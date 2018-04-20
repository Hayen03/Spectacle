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
//import com.ift2905.reservation.database.dao.SectionSQLHelper;
//import com.ift2905.reservation.database.entities.Section;

import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Section;
import hayen.spectacle.data.data.Siege;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class TestSection {


    @Test
    public void getSection() {

        Log.i("RPI", "**********************************Récupérer une Section");

        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        assertNotNull(database);

        int nbSections = dbHelper.getSectionsCount();

        assert(nbSections > 0);

        Log.i("RPI", "nb  Sections: " + nbSections);

        List<Section> sections = dbHelper.getAllSections();

        Log.i("RPI", "nb  Sections: " + sections.size());

        assertEquals(nbSections, sections.size());

        for (Section section : sections) {
            Log.i("RPI", "section: " + section);
        }


        Section section =  dbHelper.getSectionById(sections.get(sections.size()-1).getId());

        assertNotNull(section);

        Section section2 =  dbHelper.getSectionById(section.getId());


        assertEquals(section.getId(), section2.getId());


        List<Siege> sieges = dbHelper.getFreeSiegeBySections(1, 2, 4);

        for (Siege siege: sieges) {
            Log.i("RPI", siege.toString());
        }


    }


    @Test
    public void addSection(){

        Log.i("RPI", "**********************************Ajouter une Section");


        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbSections = dbHelper.getSectionsCount();
        Log.i("RPI","Count avant insertion:  " + nbSections);

        List<Section> sections = dbHelper.getAllSections();

        Log.i("RPI", "Liste des sections avant insertion");

        for (Section section1 : sections) {
            Log.i("RPI", "section: " + section1);
        }

        Section section = new Section();
        section.setName("Passerelle");
        section.setCategorie(5);
        section.setNbSieges(100);
        section.setSalleId(1);



        Log.i("RPI", "Ajout d'une section: " + section);

        long result = dbHelper.addSection(section);

        int nbSections2 = dbHelper.getSectionsCount();
        Log.i("RPI","Count after insertion:  " + nbSections2);

        assertEquals(nbSections2, nbSections + 1);

        Log.i("RPI", "Ajout de la section : " + result);

        sections = dbHelper.getAllSections();

        assertEquals(nbSections2, sections.size());


        Log.i("RPI", "Liste des sections après insertion");

        for (Section section2 : sections) {
            Log.i("RPI", "section: " + section2);
        }


            database.close();

    }


    @Test
    public void updateSection() {
        Log.i("RPI", "**********************************Mettre à jour une Section");
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);

        int nbSections = dbHelper.getSectionsCount();

        List <Section> sections = dbHelper.getAllSections();

        assertNotNull(sections);

        assert(sections.size() > 0);

        Section section =  sections.get(0);

        Log.i("RPI", "section avant mise à jour: " + section);

        section.setCategorie(2);

        dbHelper.updateSection(section);

        int nbSections2 = dbHelper.getSectionsCount();

        Section section2=  dbHelper.getSectionById(section.getId());


        Log.i("RPI", "Artiste après mise à jour: " + section);

        assertEquals(section.getId(), section2.getId());
        assertEquals(section.getCategorie(), section2.getCategorie());
        assertEquals(section.getName(), section2.getName());
        assertEquals(section.getNbSieges(), section2.getNbSieges());
        assertEquals(section.getSalleId(), section2.getSalleId());

        assertEquals(nbSections, nbSections2);


    }

    @Test
    public void deleteSection() {

        Log.i("RPI", "**********************************Supprimer un Section");
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext); // (appContext, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        //  Log.i("RPI", "open: " + dbHelper);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        assertNotNull(database);


        int nbSections = dbHelper.getSectionsCount();

        List <Section> sections = dbHelper.getAllSections();

        assert(sections.size() > 0 );

        int index =  sections.get(sections.size() -1).getId();

        Log.i("RPI", "Liste des sections avant suppression");

        for (Section section : sections) {
            Log.i("RPI", "section: " + section);
        }



        Log.i("RPI: ", "nbSections: " + nbSections);
        int nbSections2 = 0;

        Section section = dbHelper.getSectionById(index);

        if(section != null) {

            int result = dbHelper.deleteSection(section);

            nbSections2 = dbHelper.getSectionsCount();

            assertEquals(nbSections2, nbSections - 1);

            Log.i("RPI", "Liste des sections après suppression");
            Log.i("RPI: ", "nbSections2: " + nbSections2);

            List <Section> sections2 = dbHelper.getAllSections();
            for (Section section2 : sections2) {
                Log.i("RPI", "Section: " + section2);
            }


        }

    }




}
