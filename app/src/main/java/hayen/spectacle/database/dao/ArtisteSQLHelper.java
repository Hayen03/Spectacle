//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import com.ift2905.reservation.database.entities.Artiste;
import hayen.spectacle.database.entities.Artiste;

import java.util.ArrayList;
import java.util.List;


public class ArtisteSQLHelper extends SQLiteOpenHelper{


    // private static final String TABLE_NAME = "artiste";
//    public static final String CREATE_TABLE_ARTISTE =
//            "CREATE TABLE IF NOT EXISTS " + Artiste.TABLE_NAME +
//                    " (" + Artiste.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    Artiste.COLUMN_FIRSTNAME + " VARCHAR(30) NOT NULL, " +
//                    Artiste.COLUMN_LASTNAME + " VARCHAR(30))";

    public ArtisteSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    public ArtisteSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Artiste getArtisteById(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Artiste.TABLE_NAME,
                new String[]{ Artiste.COLUMN_ID, Artiste.COLUMN_FIRSTNAME, Artiste.COLUMN_LASTNAME },
                Artiste.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            // prepare note object
            Artiste artiste = new Artiste(
                    cursor.getInt(cursor.getColumnIndex(Artiste.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_FIRSTNAME)),
                    cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_LASTNAME)));

            // close the db connection
            cursor.close();

            return artiste;
        }
        return null;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Artiste getArtisteByName(Artiste artiste) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Artiste.TABLE_NAME,
                new String[]{ Artiste.COLUMN_ID, Artiste.COLUMN_FIRSTNAME, Artiste.COLUMN_LASTNAME },
                Artiste.COLUMN_FIRSTNAME + "=? and " + Artiste.COLUMN_LASTNAME + "=?",
                new String[]{artiste.getPrenom(), artiste.getNom()}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            // prepare note object
            Artiste artiste2 = new Artiste(
                    cursor.getInt(cursor.getColumnIndex(Artiste.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_FIRSTNAME)),
                    cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_LASTNAME)));

            // close the db connection
            cursor.close();

            return artiste2;
        }
        return null;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Artiste> getAllArtistes() {
        List<Artiste> artistes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Artiste.TABLE_NAME + " ORDER BY " + Artiste.COLUMN_ID;
               // Artiste.COLUMN_LASTNAME + ", " + Artiste.COLUMN_FIRSTNAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Artiste artiste = new Artiste();
                artiste.setId(cursor.getInt(cursor.getColumnIndex(Artiste.COLUMN_ID)));
                artiste.setPrenom(cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_FIRSTNAME)));
                artiste.setNom(cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_LASTNAME)));

                artistes.add(artiste);
            } while (cursor.moveToNext());
        }


        db.close();

        return artistes;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getArtistesCount() {
        String countQuery = "SELECT  * FROM " + Artiste.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        return count;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateArtiste(Artiste artiste) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Artiste.COLUMN_FIRSTNAME, artiste.getPrenom());
        values.put(Artiste.COLUMN_LASTNAME, artiste.getNom());

        int nbAffectedRows= db.update (Artiste.TABLE_NAME, values, Artiste.COLUMN_ID + " = ?",
                new String[]{String.valueOf(artiste.getId())});

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public long addArtiste(Artiste artiste) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Artiste.COLUMN_FIRSTNAME, artiste.getPrenom());
        values.put(Artiste.COLUMN_LASTNAME, artiste.getNom());

        long nbAffectedRows= db.insert(Artiste.TABLE_NAME, null, values);

        return nbAffectedRows;
    }



    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteArtiste(Artiste artiste) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Artiste.TABLE_NAME, Artiste.COLUMN_ID + " = ?",
                new String[]{String.valueOf(artiste.getId())});
        db.close();

        return result;
    }
    //**********************************************************************************************
    //**********************************************************************************************

    @Override
    public synchronized void close() {
        super.close();
    }

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
