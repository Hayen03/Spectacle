//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import com.ift2905.reservation.database.entities.Adresse;
import hayen.spectacle.database.data.Adresse;

import java.util.ArrayList;
import java.util.List;


public class AdresseSQLHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "adresse";

//    public static final String CREATE_TABLE_ADRESSE =
//            "CREATE TABLE IF NOT EXISTS adresse " +
//                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    " numero INTEGER NOT NULL, rue VARCHAR(60) NOT NULL, " +
//                    " ville VARCHAR(60) NOT NULL, province VARCHAR(2) NOT NULL, " +
//                    " code_postal VARCHAR(10) NOT NULL, longitude LONG DEFAULT 0, latitude LONG DEFAULT 0)";

    private static volatile AdresseSQLHelper adresseSqlHelper;
    private SQLiteDatabase database;

    private AdresseSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    private AdresseSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public static AdresseSQLHelper getInstance(Context context){

        if(adresseSqlHelper == null){
            synchronized (AdresseSQLHelper.class){
                if(adresseSqlHelper == null){
                    adresseSqlHelper = new AdresseSQLHelper(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
                }
            }
        }
        return adresseSqlHelper;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public Adresse getAdresseById(int id) {


        Adresse adresse = null;
        database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME,
                new String[]{   Adresse.COLUMN_ID, Adresse.COLUMN_NUMERO, Adresse.COLUMN_RUE,
                                Adresse.COLUMN_VILLE, Adresse.COLUMN_PROVINCE, Adresse.COLUMN_CODE_POSTAL,
                                Adresse.COLUMN_LONGITUDE, Adresse.COLUMN_LATITUDE },
                Adresse.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            adresse = new Adresse(
                    cursor.getInt(cursor.getColumnIndex(Adresse.COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(Adresse.COLUMN_NUMERO)),
                    cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_RUE)),
                    cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_VILLE)),
                    cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_PROVINCE)),
                    cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_CODE_POSTAL)),
                    cursor.getLong(cursor.getColumnIndex(Adresse.COLUMN_LONGITUDE)),
                    cursor.getLong(cursor.getColumnIndex(Adresse.COLUMN_LATITUDE)));

             cursor.close();

        }

        close();

        return adresse;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public List<Adresse> getAllAdresses() {
        List<Adresse> adresses = null;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Adresse.TABLE_NAME + " ORDER BY " +
                Adresse.COLUMN_ID + " ASC";

        database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            adresses = new ArrayList<>();

            do {
                Adresse adresse = new Adresse();
                adresse.setId(cursor.getInt(cursor.getColumnIndex(Adresse.COLUMN_ID)));
                adresse.setNumero(cursor.getInt(cursor.getColumnIndex(Adresse.COLUMN_NUMERO)));
                adresse.setRue(cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_RUE)));
                adresse.setVille(cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_VILLE)));
                adresse.setProvince(cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_PROVINCE)));
                adresse.setCodePostal(cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_CODE_POSTAL)));
                adresse.setLongitude(cursor.getLong(cursor.getColumnIndex(Adresse.COLUMN_LONGITUDE)));
                adresse.setLatitude(cursor.getLong(cursor.getColumnIndex(Adresse.COLUMN_LATITUDE)));

                adresses.add(adresse);
            } while (cursor.moveToNext());

            cursor.close();
        }


        close();

        return adresses;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getAdressesCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Adresse.TABLE_NAME;

        Cursor cursor = database.rawQuery(countQuery, null);

        int count = 0;
        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        close();
        return count;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public long addAdresse(Adresse adresse) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Adresse.COLUMN_NUMERO, adresse.getNumero());
        values.put(Adresse.COLUMN_RUE, adresse.getRue());
        values.put(Adresse.COLUMN_VILLE, adresse.getVille());
        values.put(Adresse.COLUMN_PROVINCE, adresse.getProvince());
        values.put(Adresse.COLUMN_CODE_POSTAL, adresse.getCodePostal());
        if(adresse.getLongitude() > 0 && adresse.getLatitude() > 0) {
            values.put(Adresse.COLUMN_LONGITUDE, adresse.getLongitude());
            values.put(Adresse.COLUMN_LATITUDE, adresse.getLatitude());
        }


        long nbAffectedRows = database.insert(Adresse.TABLE_NAME, null, values);

        close();
        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateAdresse(Adresse adresse) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Adresse.COLUMN_NUMERO, adresse.getNumero());
        values.put(Adresse.COLUMN_RUE, adresse.getRue());
        values.put(Adresse.COLUMN_VILLE, adresse.getVille());
        values.put(Adresse.COLUMN_PROVINCE, adresse.getProvince());
        values.put(Adresse.COLUMN_CODE_POSTAL, adresse.getCodePostal());

        if(adresse.getLongitude() > 0 && adresse.getLatitude() > 0) {
            values.put(Adresse.COLUMN_LONGITUDE, adresse.getLongitude());
            values.put(Adresse.COLUMN_LATITUDE, adresse.getLatitude());
        }


        int nbAffectedRows= database.update (Adresse.TABLE_NAME, values, Adresse.COLUMN_ID + " = ?",
                new String[]{String.valueOf(adresse.getId())});

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteAdresse(Adresse adresse) {

        database= this.getWritableDatabase();

        int result = database.delete(Adresse.TABLE_NAME, Adresse.COLUMN_ID + " = ?",
                new String[]{String.valueOf(adresse.getId())});
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

