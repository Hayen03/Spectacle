//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import com.ift2905.reservation.database.entities.Genre;
import hayen.spectacle.database.data.Genre;


import java.util.ArrayList;
import java.util.List;



public class GenreSQLHelper extends SQLiteOpenHelper {



//    public static final String CREATE_TABLE_GENRE =
//            "CREATE TABLE IF NOT EXISTS "+Genre.TABLE_NAME+
//                    " (" + Genre.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    Genre.COLUMN_NAME + " VARCHAR(30) NOT NULL)";


    private static volatile GenreSQLHelper genreSQLHelper;

    private SQLiteDatabase database;

    private GenreSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    private GenreSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    //**********************************************************************************************
    //**********************************************************************************************


    public static GenreSQLHelper getInstance(Context context){

        if(genreSQLHelper == null){
            synchronized (GenreSQLHelper.class){
                if(genreSQLHelper == null){
                    genreSQLHelper = new GenreSQLHelper(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
                }
            }
        }
        return genreSQLHelper;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public Genre getGenreById(int id) {

        Genre genre = null;

        // get a readable database
        database= this.getReadableDatabase();

        Cursor cursor = database.query(Genre.TABLE_NAME,
                new String[]{ Genre.COLUMN_ID, Genre.COLUMN_NAME },
                Genre.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            genre = new Genre(
                    cursor.getInt(cursor.getColumnIndex(Genre.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Genre.COLUMN_NAME)));

            cursor.close();
        }

        close();

        return genre;


    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Genre> getAllGenres() {

        List<Genre> genres = null;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Genre.TABLE_NAME + " ORDER BY " + Genre.COLUMN_NAME + " ASC";

        database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            genres = new ArrayList<>();

            do {
                Genre genre = new Genre();
                genre.setId(cursor.getInt(cursor.getColumnIndex(Genre.COLUMN_ID)));
                genre.setNom(cursor.getString(cursor.getColumnIndex(Genre.COLUMN_NAME)));
                genres.add(genre);
            } while (cursor.moveToNext());

            cursor.close();
        }

        close();

        return genres;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getGenresCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Genre.TABLE_NAME;
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


    public long addGenre(Genre genre) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Genre.COLUMN_NAME, genre.getNom());

        long nbAffectedRows= database.insert (Genre.TABLE_NAME, null, values);


        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateGenre(Genre genre) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Genre.COLUMN_NAME, genre.getNom());

        int nbAffectedRows= database.update (Genre.TABLE_NAME, values, Genre.COLUMN_ID + " = ?",
                new String[]{String.valueOf(genre.getId())});

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteGenre(Genre genre) {

        database = this.getWritableDatabase();

        int result = database.delete(Genre.TABLE_NAME, Genre.COLUMN_ID + " = ?",
                new String[]{String.valueOf(genre.getId())});

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

