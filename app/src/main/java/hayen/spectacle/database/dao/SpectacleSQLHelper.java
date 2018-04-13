//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

//import com.ift2905.reservation.database.entities.Spectacle;
import hayen.spectacle.database.data.Artiste;
import hayen.spectacle.database.data.Spectacle;


public class SpectacleSQLHelper extends SQLiteOpenHelper {

    //  private static final String TABLE_NAME = "spectacle";

//    public static final String CREATE_TABLE_SPECTACLE =
//            "CREATE TABLE IF NOT EXISTS " + Spectacle.TABLE_NAME +
//                    " (" + Spectacle.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    Spectacle.COLUMN_TITRE + " VARCHAR(40), " +
//                    Spectacle.COLUMN_DATE_SPECTACLE + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
//                    Spectacle.COLUMN_GENRE_ID + " INTEGER, " +
//                    " FOREIGN KEY (" + Spectacle.COLUMN_GENRE_ID + ") REFERENCES " +
//                    Spectacle.TABLE_NAME_REF_GENRE + "(" + Spectacle.COLUMN_ID + ") )";


    private static volatile SpectacleSQLHelper spectacleSQLHelper;

    private SQLiteDatabase database;


    private SpectacleSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    private SpectacleSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public static SpectacleSQLHelper getInstance(Context context){

        if(spectacleSQLHelper == null){
            synchronized (SpectacleSQLHelper.class){
                if(spectacleSQLHelper == null){
                    spectacleSQLHelper = new SpectacleSQLHelper(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
                }
            }
        }
        return spectacleSQLHelper;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Spectacle getSpectacleById(int id) {


        database = this.getReadableDatabase();

        Spectacle spectacle = null;

        Cursor cursor = database.query(Spectacle.TABLE_NAME,
                new String[]{   Spectacle.COLUMN_ID, Spectacle.COLUMN_TITRE, Spectacle.COLUMN_DATE_SPECTACLE,
                        Spectacle.COLUMN_GENRE_ID, Spectacle.COLUMN_SALLE_ID },
                Spectacle.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            spectacle = new Spectacle(
                    cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_TITRE)),
                    cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_DATE_SPECTACLE)),
                    cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_GENRE_ID)),
                    cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_SALLE_ID)));

            cursor.close();
        }

        return spectacle;

    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Spectacle> getAllSpectacles() {

        database = this.getReadableDatabase();

        List<Spectacle> spectacles = null;

        String selectQuery = "SELECT  * FROM " + Spectacle.TABLE_NAME + " ORDER BY " +
                Spectacle.COLUMN_ID + " ASC";

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            spectacles = new ArrayList<>();

            do {

                Spectacle spectacle = new Spectacle();
                spectacle.setId(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_ID)));
                spectacle.setTitre(cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_TITRE)));
                spectacle.setDate(cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_DATE_SPECTACLE)));
                spectacle.setGenreId(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_GENRE_ID)));
                spectacle.setSalleId(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_SALLE_ID)));

                spectacles.add(spectacle);

            } while (cursor.moveToNext());

            cursor.close();
        }


        close();

        return spectacles;
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

    public int getSpectaclesCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Spectacle.TABLE_NAME;

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


    public long addSpectacle(Spectacle spectacle) {


        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Spectacle.COLUMN_TITRE, spectacle.getTitre());
        values.put(Spectacle.COLUMN_DATE_SPECTACLE, spectacle.getDate());
        values.put(Spectacle.COLUMN_GENRE_ID, spectacle.getGenreId());
        values.put(Spectacle.COLUMN_SALLE_ID, spectacle.getSalleId());


        long nbAffectedRows= database.insert(Spectacle.TABLE_NAME, null, values);

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateSpectacle(Spectacle spectacle) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Spectacle.COLUMN_TITRE, spectacle.getTitre());
        values.put(Spectacle.COLUMN_DATE_SPECTACLE, spectacle.getDate());
        values.put(Spectacle.COLUMN_GENRE_ID, spectacle.getGenreId());
        values.put(Spectacle.COLUMN_SALLE_ID, spectacle.getSalleId());


        int nbAffectedRows= database.update (Spectacle.TABLE_NAME, values, Spectacle.COLUMN_ID + " = ?",
                new String[]{String.valueOf(spectacle.getId())});

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int  deleteSpectacle(Spectacle spectacle) {

        database = this.getWritableDatabase();

        int result = database.delete(Spectacle.TABLE_NAME, Spectacle.COLUMN_ID + " = ?",
                new String[]{String.valueOf(spectacle.getId())});

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

