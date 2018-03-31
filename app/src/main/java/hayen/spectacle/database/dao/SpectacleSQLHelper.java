//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

//import com.ift2905.reservation.database.entities.Spectacle;
import hayen.spectacle.database.entities.Spectacle;


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


    public SpectacleSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    public SpectacleSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Spectacle getSpectacleById(int id) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Spectacle.TABLE_NAME,
                new String[]{   Spectacle.COLUMN_ID, Spectacle.COLUMN_TITRE, Spectacle.COLUMN_DATE_SPECTACLE,
                        Spectacle.COLUMN_GENRE_ID, Spectacle.COLUMN_SALLE_ID },
                Spectacle.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Spectacle spectacle = new Spectacle(
                    cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_TITRE)),
                    cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_DATE_SPECTACLE)),
                    cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_GENRE_ID)),
                    cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_SALLE_ID)));

            cursor.close();

            return spectacle;
        }

        return null;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Spectacle> getAllSpectacles() {
        List<Spectacle> spectacles = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Spectacle.TABLE_NAME + " ORDER BY " +
                Spectacle.COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Spectacle spectacle = new Spectacle();
                spectacle.setId(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_ID)));
                spectacle.setTitre(cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_TITRE)));
                spectacle.setDate(cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_DATE_SPECTACLE)));
                spectacle.setGenreId(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_GENRE_ID)));
                spectacle.setSalleId(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_SALLE_ID)));


                spectacles.add(spectacle);
            } while (cursor.moveToNext());
        }


        db.close();

        return spectacles;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getSpectaclesCount() {
        String countQuery = "SELECT  * FROM " + Spectacle.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        return count;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public long addSpectacle(Spectacle spectacle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Spectacle.COLUMN_TITRE, spectacle.getTitre());
        values.put(Spectacle.COLUMN_DATE_SPECTACLE, spectacle.getDate());
        values.put(Spectacle.COLUMN_GENRE_ID, spectacle.getGenreId());
        values.put(Spectacle.COLUMN_SALLE_ID, spectacle.getSalleId());


        long nbAffectedRows= db.insert(Spectacle.TABLE_NAME, null, values);

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateSpectacle(Spectacle spectacle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Spectacle.COLUMN_TITRE, spectacle.getTitre());
        values.put(Spectacle.COLUMN_DATE_SPECTACLE, spectacle.getDate());
        values.put(Spectacle.COLUMN_GENRE_ID, spectacle.getGenreId());
        values.put(Spectacle.COLUMN_SALLE_ID, spectacle.getSalleId());


        int nbAffectedRows= db.update (Spectacle.TABLE_NAME, values, Spectacle.COLUMN_ID + " = ?",
                new String[]{String.valueOf(spectacle.getId())});

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int  deleteSpectacle(Spectacle spectacle) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Spectacle.TABLE_NAME, Spectacle.COLUMN_ID + " = ?",
                new String[]{String.valueOf(spectacle.getId())});
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

