//package com.ift2905.reservation.database.dao;
package hayen.spectacle.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import com.ift2905.reservation.database.entities.Salle;
//import com.ift2905.reservation.database.entities.Section;

import hayen.spectacle.data.data.Section;
import hayen.spectacle.data.data.SpectacleSection;


import java.util.ArrayList;
import java.util.List;


public class SectionSQLHelper extends SQLiteOpenHelper {

  //  private static final String TABLE_NAME = "section";

//    public static final String CREATE_TABLE_SECTION =
//            "CREATE TABLE IF NOT EXISTS " + Section.TABLE_NAME +
//                    " (" + Section.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    Section.COLUMN_NAME + " VARCHAR(30), " +
//                    Section.COLUMN_CATEGORIE + " INTEGER, " +
//                    Section.COLUMN_NB_SIEGES + " INTEGER DEFAULT 0, " +
//                    Section.COLUMN_SALLE_ID + " INTEGER, " +
//                    " FOREIGN KEY (" + Section.COLUMN_SALLE_ID + ") REFERENCES " + Salle.TABLE_NAME + "(" + Salle.COLUMN_ID + "))";

    private static volatile SectionSQLHelper sectionSQLHelper;

    private SQLiteDatabase database;

    private SectionSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    private SectionSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public static SectionSQLHelper getInstance(Context context){

        if(sectionSQLHelper == null){
            synchronized (SectionSQLHelper.class){
                if(sectionSQLHelper == null){
                    sectionSQLHelper = new SectionSQLHelper(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
                }
            }
        }
        return sectionSQLHelper;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public Section getSectionById(int id) {


        database = this.getReadableDatabase();

        Section section = null;

        Cursor cursor = database.query(Section.TABLE_NAME,
                new String[]{   Section.COLUMN_ID, Section.COLUMN_NAME, Section.COLUMN_CATEGORIE,
                                Section.COLUMN_NB_SIEGES, Section.COLUMN_SALLE_ID },
                        Section.COLUMN_ID + "=?",
                        new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            section = new Section(
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Section.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_CATEGORIE)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_NB_SIEGES)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_SALLE_ID)));

            cursor.close();
        }

        return section;

    }


    //**********************************************************************************************
    //**********************************************************************************************

    public Section getSectionBySalleId(int idSalle) {

        database = this.getReadableDatabase();

        Section section = null;

        Cursor cursor = database.query(Section.TABLE_NAME,
                new String[]{   Section.COLUMN_ID, Section.COLUMN_NAME, Section.COLUMN_CATEGORIE,
                        Section.COLUMN_NB_SIEGES, Section.COLUMN_SALLE_ID },
                Section.COLUMN_SALLE_ID + "=?",
                new String[]{String.valueOf(idSalle)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            section = new Section(
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Section.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_CATEGORIE)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_NB_SIEGES)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_SALLE_ID)));

            cursor.close();
        }

        close();

        return section;

    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Section> getSectionsBySalleId(int salleId) {

        database = this.getReadableDatabase();

        List<Section> sections = null;

        String selectQuery = "SELECT  * FROM " + Section.TABLE_NAME +
                " where "+ Section.COLUMN_SALLE_ID +"=" + salleId;


        Cursor cursor = database.rawQuery(selectQuery, null);

        Log.i("RPI", "Section count: " + cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {

            sections = new ArrayList<>();

            do {


                Section section = new Section();
                section.setId(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_ID)));
                section.setName(cursor.getString(cursor.getColumnIndex(Section.COLUMN_NAME)));
                section.setCategorie(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_CATEGORIE)));
                section.setNbSieges(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_NB_SIEGES)));
                section.setSalleId(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_SALLE_ID)));

                sections.add(section);
            } while (cursor.moveToNext());

            cursor.close();
        }


        close();

        return sections;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public List<SpectacleSection> getSectionsBySpectacleId(int spectacleId) {

        database = this.getReadableDatabase();

        List<SpectacleSection> spectacleSections = null;

        String selectQuery = "SELECT  * FROM " + SpectacleSection.TABLE_NAME +
                " where "+ SpectacleSection.COLUMN_SPECTACLE_ID +"=" + spectacleId;


        Cursor cursor = database.rawQuery(selectQuery, null);

        Log.i("RPI", "Section count: " + cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {

            spectacleSections = new ArrayList<>();

            do {


                SpectacleSection spectacleSection = new SpectacleSection();
                spectacleSection.setSpectacleId(cursor.getInt(cursor.getColumnIndex(SpectacleSection.COLUMN_SPECTACLE_ID)));
                spectacleSection.setSectionid(cursor.getInt(cursor.getColumnIndex(SpectacleSection.COLUMN_SECTION_ID)));
                spectacleSection.setPrice(cursor.getFloat(cursor.getColumnIndex(SpectacleSection.COLUMN_PRICE)));


                spectacleSections.add(spectacleSection);
            } while (cursor.moveToNext());

            cursor.close();
        }


        close();

        return spectacleSections;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public List<Integer> getFreePlacesBySections(int spectacleId) {

        database = this.getReadableDatabase();

        List<Integer> freeSieges = null;

       // String selectQuery = "SELECT  * FROM " + SpectacleSection.TABLE_NAME +
        //        " where "+ SpectacleSection.COLUMN_SPECTACLE_ID +"=" + spectacleId;
        String selectQuery =
            "select count(*) as nb_libres" +

                " from siege " +
                " inner join spectacle_siege as SPSI on SPSI.id_siege=siege.id " +
                " inner join section on section.id=siege.id_section " +
                " where SPSI.id_spectacle=1 and SPSI.reserve=0 " +
                    " group by section.id " +
                    " order by section.id asc "
        ;

                ;
        Cursor cursor = database.rawQuery(selectQuery, null);

        Log.i("RPI", "Section count: " + cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {

            freeSieges= new ArrayList<>();

            do {


                int nbSieges = cursor.getInt(cursor.getColumnIndex("nb_libres"));
                freeSieges.add(nbSieges);
                Log.i("RPI", "nbSieges: " + nbSieges);
            } while (cursor.moveToNext());

            cursor.close();
        }


        close();

        return freeSieges;
    }




    //**********************************************************************************************
    //**********************************************************************************************

    public int getSectionsCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Section.TABLE_NAME;

        Cursor cursor = database.rawQuery(countQuery, null);

        int count = 0;

        if(cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        close();

        return count;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public long addSection(Section section) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Section.COLUMN_NAME, section.getName());
        values.put(Section.COLUMN_CATEGORIE, section.getCategorie());
        values.put(Section.COLUMN_NB_SIEGES, section.getNbSieges());
        values.put(Section.COLUMN_SALLE_ID, section.getSalleId());

        long nbAffectedRows= database.insert (Section.TABLE_NAME, null, values);

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateSection(Section section) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Section.COLUMN_NAME, section.getName());
        values.put(Section.COLUMN_CATEGORIE, section.getCategorie());
        values.put(Section.COLUMN_NB_SIEGES, section.getNbSieges());
        values.put(Section.COLUMN_SALLE_ID, section.getSalleId());

        int nbAffectedRows= database.update (Section.TABLE_NAME, values, Section.COLUMN_ID + " = ?",
                new String[]{String.valueOf(section.getId())});

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteSection(Section section) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Section.TABLE_NAME, Section.COLUMN_ID + " = ?",
                new String[]{String.valueOf(section.getId())});


        close();

        return result;
    }

    //*******************************************************************************************************
    //*******************************************************************************************************

    @Override
    public synchronized void close() {

        try {
            if(database.isOpen()) {
                Log.i ("RPI", "Closing database" );
                database.close();
                Log.i ("RPI", "Database closed ?: " + (!database.isOpen()));
                database = null;
            }
        }catch(SQLException ex){
            Log.i ("RPI", "Error trying to close database: " + ex.getMessage());
            ex.printStackTrace();
        }
        finally{
            super.close();
        }

    }

    //*******************************************************************************************************
    //*******************************************************************************************************

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //**********************************************************************************************
    //**********************************************************************************************


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    //**********************************************************************************************
    //**********************************************************************************************


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
