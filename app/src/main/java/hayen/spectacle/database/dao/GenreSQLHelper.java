//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import com.ift2905.reservation.database.entities.Genre;
import hayen.spectacle.database.entities.Genre;


import java.util.ArrayList;
import java.util.List;



public class GenreSQLHelper extends SQLiteOpenHelper {



//    public static final String CREATE_TABLE_GENRE =
//            "CREATE TABLE IF NOT EXISTS "+Genre.TABLE_NAME+
//                    " (" + Genre.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    Genre.COLUMN_NAME + " VARCHAR(30) NOT NULL)";


    public GenreSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    public GenreSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Genre getGenreById(int id) {

        // get a readable database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Genre.TABLE_NAME,
                new String[]{ Genre.COLUMN_ID, Genre.COLUMN_NAME },
                Genre.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            // prepare object
            Genre genre = new Genre(
                    cursor.getInt(cursor.getColumnIndex(Genre.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Genre.COLUMN_NAME)));

            cursor.close();

            return genre;
        }
        return null;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Genre.TABLE_NAME + " ORDER BY " + Genre.COLUMN_NAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Genre genre = new Genre();
                genre.setId(cursor.getInt(cursor.getColumnIndex(Genre.COLUMN_ID)));
                genre.setNom(cursor.getString(cursor.getColumnIndex(Genre.COLUMN_NAME)));
                genres.add(genre);
            } while (cursor.moveToNext());
        }

        db.close();

        return genres;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getGenresCount() {
        String countQuery = "SELECT  * FROM " + Genre.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public long addGenre(Genre genre) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Genre.COLUMN_NAME, genre.getNom());

        long nbAffectedRows= db.insert (Genre.TABLE_NAME, null, values);

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateGenre(Genre genre) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Genre.COLUMN_NAME, genre.getNom());

        int nbAffectedRows= db.update (Genre.TABLE_NAME, values, Genre.COLUMN_ID + " = ?",
                new String[]{String.valueOf(genre.getId())});

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteGenre(Genre genre) {
        SQLiteDatabase db = this.getWritableDatabase();
       int result = db.delete(Genre.TABLE_NAME, Genre.COLUMN_ID + " = ?",
                new String[]{String.valueOf(genre.getId())});
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

