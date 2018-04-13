//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import com.ift2905.reservation.database.entities.Paiement;
import hayen.spectacle.database.data.Paiement;


import java.util.ArrayList;
import java.util.List;


public class PaiementSQLHelper extends SQLiteOpenHelper {

    //private static final String TABLE_NAME = "paiement";

//    public static final String CREATE_TABLE_PAIEMENT =
//            "CREATE TABLE IF NOT EXISTS paiement (id INTEGER PRIMARY KEY AUTOINCREMENT, montant FLOAT DEFAULT 0.0, reservation_id INTEGER, " +
//                    " date_paiement DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (reservation_id) REFERENCES reservation(id))";


    private static volatile PaiementSQLHelper paiementSQLHelper;

    private SQLiteDatabase database;

    public PaiementSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    private PaiementSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    public static PaiementSQLHelper getInstance(Context context){

        if(paiementSQLHelper == null){
            synchronized (PaiementSQLHelper.class){
                if(paiementSQLHelper == null){
                    paiementSQLHelper = new PaiementSQLHelper(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
                }
            }
        }
        return paiementSQLHelper;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Paiement getPaiementById(int id) {

        Paiement paiement = null;

       database = this.getReadableDatabase();

        Cursor cursor = database.query(Paiement.TABLE_NAME,
                new String[]{ Paiement.COLUMN_ID, Paiement.COLUMN_MONTANT, Paiement.COLUMN_DATE_PAIEMENT, Paiement.COLUMN_RESERVATION_ID },
                Paiement.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            cursor.getString(cursor.getColumnIndex(Paiement.COLUMN_DATE_PAIEMENT));
            paiement = new Paiement(
                    cursor.getInt(cursor.getColumnIndex(Paiement.COLUMN_ID)),
                    cursor.getFloat(cursor.getColumnIndex(Paiement.COLUMN_MONTANT)),
                    cursor.getString(cursor.getColumnIndex(Paiement.COLUMN_DATE_PAIEMENT)),
                    cursor.getInt(cursor.getColumnIndex(Paiement.COLUMN_RESERVATION_ID)));


            cursor.close();
        }

        close();

        return paiement;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public Paiement getPaiementByReservationId(int reservationId) {

        Paiement paiement = null;

        // get a readable database
        database = this.getReadableDatabase();

        Cursor cursor = database.query(Paiement.TABLE_NAME,
                new String[]{ Paiement.COLUMN_ID, Paiement.COLUMN_MONTANT, Paiement.COLUMN_DATE_PAIEMENT, Paiement.COLUMN_RESERVATION_ID },
                Paiement.COLUMN_RESERVATION_ID + "=?",
                new String[]{String.valueOf(reservationId)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            cursor.getString(cursor.getColumnIndex(Paiement.COLUMN_DATE_PAIEMENT));
            paiement = new Paiement(
                    cursor.getInt(cursor.getColumnIndex(Paiement.COLUMN_ID)),
                    cursor.getFloat(cursor.getColumnIndex(Paiement.COLUMN_MONTANT)),
                    cursor.getString(cursor.getColumnIndex(Paiement.COLUMN_DATE_PAIEMENT)),
                    cursor.getInt(cursor.getColumnIndex(Paiement.COLUMN_RESERVATION_ID)));

            cursor.close();
        }

        close();

        return paiement;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Paiement> getAllPaiements() {


        List<Paiement> paiements = null;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Paiement.TABLE_NAME + " ORDER BY " +
                Paiement.COLUMN_DATE_PAIEMENT + " DESC";

        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            paiements = new ArrayList<>();

            do {
                Paiement paiement = new Paiement();
                paiement.setId(cursor.getInt(cursor.getColumnIndex(Paiement.COLUMN_ID)));
                paiement.setMontant(cursor.getFloat(cursor.getColumnIndex(Paiement.COLUMN_MONTANT)));
                paiement.setDatePaiement(cursor.getString(cursor.getColumnIndex(Paiement.COLUMN_DATE_PAIEMENT)));
                paiement.setReservation_id(cursor.getInt(cursor.getColumnIndex(Paiement.COLUMN_RESERVATION_ID)));

                paiements.add(paiement);
            } while (cursor.moveToNext());

            cursor.close();
        }

        close();

        return paiements;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getPaiementsCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Paiement.TABLE_NAME;

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


    public long addPaiement(Paiement paiement) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Paiement.COLUMN_MONTANT, paiement.getMontant());
        values.put(Paiement.COLUMN_DATE_PAIEMENT, paiement.getDatePaiement());
        values.put(Paiement.COLUMN_RESERVATION_ID, paiement.getReservation_id());

        long nbAffectedRows = database.insert(Paiement.TABLE_NAME, null, values);

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updatePaiement(Paiement paiement) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Paiement.COLUMN_MONTANT, paiement.getMontant());
        values.put(Paiement.COLUMN_DATE_PAIEMENT, paiement.getDatePaiement());
        values.put(Paiement.COLUMN_RESERVATION_ID, paiement.getReservation_id());


        int nbAffectedRows= database.update (Paiement.TABLE_NAME, values, Paiement.COLUMN_ID + " = ?",
                new String[]{String.valueOf(paiement.getId())});

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deletePaiement(Paiement paiement) {
        database= this.getWritableDatabase();
        int result = database.delete(Paiement.TABLE_NAME, Paiement.COLUMN_ID + " = ?",
                new String[]{String.valueOf(paiement.getId())});

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

