//package com.ift2905.reservation.database.dao;
package hayen.spectacle.data.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import com.ift2905.reservation.database.entities.Artiste;
import hayen.spectacle.data.data.Artiste;

import java.util.ArrayList;
import java.util.List;


public class ArtisteSQLHelper extends SQLiteOpenHelper{


    // private static final String TABLE_NAME = "artiste";
//    public static final String CREATE_TABLE_ARTISTE =
//            "CREATE TABLE IF NOT EXISTS " + Artiste.TABLE_NAME +
//                    " (" + Artiste.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    Artiste.COLUMN_FIRSTNAME + " VARCHAR(30) NOT NULL, " +
//                    Artiste.COLUMN_LASTNAME + " VARCHAR(30))";


    private static volatile ArtisteSQLHelper artisteSQLHelper;

    private SQLiteDatabase database;

    private ArtisteSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    private ArtisteSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static ArtisteSQLHelper getInstance(Context context){

        if(artisteSQLHelper == null){
            synchronized (ArtisteSQLHelper.class){
                if(artisteSQLHelper == null){
                    artisteSQLHelper = new ArtisteSQLHelper(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
                }
            }
        }
        return artisteSQLHelper;
    }



    //**********************************************************************************************
    //**********************************************************************************************

    public Artiste getArtisteById(int id) {

        database = this.getReadableDatabase();

        Artiste artiste = null;
        Cursor cursor = database.query(Artiste.TABLE_NAME,
                new String[]{ Artiste.COLUMN_ID, Artiste.COLUMN_FIRSTNAME, Artiste.COLUMN_LASTNAME },
                Artiste.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            artiste = new Artiste(
                    cursor.getInt(cursor.getColumnIndex(Artiste.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_FIRSTNAME)),
                    cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_LASTNAME)));

            cursor.close();
        }

        close();

        return artiste;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Artiste getArtisteByName(Artiste artiste) {

        database = this.getReadableDatabase();

        Artiste artiste2 = null;
        Cursor cursor = database.query(Artiste.TABLE_NAME,
                new String[]{ Artiste.COLUMN_ID, Artiste.COLUMN_FIRSTNAME, Artiste.COLUMN_LASTNAME },
                Artiste.COLUMN_FIRSTNAME + "=? and " + Artiste.COLUMN_LASTNAME + "=?",
                new String[]{artiste.getPrenom(), artiste.getNom()}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            artiste2 = new Artiste(
                    cursor.getInt(cursor.getColumnIndex(Artiste.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_FIRSTNAME)),
                    cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_LASTNAME)));

            cursor.close();

        }

        close();

        return artiste2;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Artiste> getAllArtistes() {
        List<Artiste> artistes = null;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Artiste.TABLE_NAME + " ORDER BY " + Artiste.COLUMN_ID;
               // Artiste.COLUMN_LASTNAME + ", " + Artiste.COLUMN_FIRSTNAME + " ASC";

        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            artistes = new ArrayList<>();

            do {
                Artiste artiste = new Artiste();
                artiste.setId(cursor.getInt(cursor.getColumnIndex(Artiste.COLUMN_ID)));
                artiste.setPrenom(cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_FIRSTNAME)));
                artiste.setNom(cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_LASTNAME)));

                artistes.add(artiste);
            } while (cursor.moveToNext());

            cursor.close();
        }

        close();

        return artistes;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Artiste> getAllArtistesBySpectacleId(int id) {
        List<Artiste> artistes = null;

        // Select All Query
        String selectQuery = "SELECT  * FROM artiste " +
                " inner join spectacle_artiste as SA on SA.id_artiste=artiste.id " +
                " inner join spectacle on spectacle.id=SA.id_spectacle " +
                "   where id_spectacle=" + id;


        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            artistes = new ArrayList<>();

            do {
                Artiste artiste = new Artiste();
                artiste.setId(cursor.getInt(cursor.getColumnIndex(Artiste.COLUMN_ID)));
                artiste.setPrenom(cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_FIRSTNAME)));
                artiste.setNom(cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_LASTNAME)));

                artistes.add(artiste);
            } while (cursor.moveToNext());

            cursor.close();
        }

        close();

        return artistes;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getArtistesCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Artiste.TABLE_NAME;

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


    public int updateArtiste(Artiste artiste) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Artiste.COLUMN_FIRSTNAME, artiste.getPrenom());
        values.put(Artiste.COLUMN_LASTNAME, artiste.getNom());

        int nbAffectedRows= database.update (Artiste.TABLE_NAME, values, Artiste.COLUMN_ID + " = ?",
                new String[]{String.valueOf(artiste.getId())});

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public long addArtiste(Artiste artiste) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Artiste.COLUMN_FIRSTNAME, artiste.getPrenom());
        values.put(Artiste.COLUMN_LASTNAME, artiste.getNom());

        long nbAffectedRows= database.insert(Artiste.TABLE_NAME, null, values);

        close();

        return nbAffectedRows;
    }



    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteArtiste(Artiste artiste) {

        database= this.getWritableDatabase();
        int result = database.delete(Artiste.TABLE_NAME, Artiste.COLUMN_ID + " = ?",
                new String[]{String.valueOf(artiste.getId())});

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
