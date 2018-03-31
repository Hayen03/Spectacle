//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import com.ift2905.reservation.database.entities.Paiement;
import hayen.spectacle.database.entities.Paiement;


import java.util.ArrayList;
import java.util.List;


public class PaiementSQLHelper extends SQLiteOpenHelper {

    //private static final String TABLE_NAME = "paiement";

    public static final String CREATE_TABLE_PAIEMENT =
            "CREATE TABLE IF NOT EXISTS paiement (id INTEGER PRIMARY KEY AUTOINCREMENT, montant FLOAT DEFAULT 0.0, reservation_id INTEGER, " +
                    " date_paiement DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (reservation_id) REFERENCES reservation(id))";

    public PaiementSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    public PaiementSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Paiement getPaiementById(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Paiement.TABLE_NAME,
                new String[]{ Paiement.COLUMN_ID, Paiement.COLUMN_MONTANT, Paiement.COLUMN_DATE_PAIEMENT, Paiement.COLUMN_RESERVATION_ID },
                Paiement.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            cursor.getString(cursor.getColumnIndex(Paiement.COLUMN_DATE_PAIEMENT));
            Paiement paiement = new Paiement(
                    cursor.getInt(cursor.getColumnIndex(Paiement.COLUMN_ID)),
                    cursor.getFloat(cursor.getColumnIndex(Paiement.COLUMN_MONTANT)),
                    cursor.getString(cursor.getColumnIndex(Paiement.COLUMN_DATE_PAIEMENT)),
                    cursor.getInt(cursor.getColumnIndex(Paiement.COLUMN_RESERVATION_ID)));


            cursor.close();

            return paiement;
        }
        return null;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public Paiement getPaiementByReservationId(int reservationId) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Paiement.TABLE_NAME,
                new String[]{ Paiement.COLUMN_ID, Paiement.COLUMN_MONTANT, Paiement.COLUMN_DATE_PAIEMENT, Paiement.COLUMN_RESERVATION_ID },
                Paiement.COLUMN_RESERVATION_ID + "=?",
                new String[]{String.valueOf(reservationId)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            cursor.getString(cursor.getColumnIndex(Paiement.COLUMN_DATE_PAIEMENT));
            Paiement paiement = new Paiement(
                    cursor.getInt(cursor.getColumnIndex(Paiement.COLUMN_ID)),
                    cursor.getFloat(cursor.getColumnIndex(Paiement.COLUMN_MONTANT)),
                    cursor.getString(cursor.getColumnIndex(Paiement.COLUMN_DATE_PAIEMENT)),
                    cursor.getInt(cursor.getColumnIndex(Paiement.COLUMN_RESERVATION_ID)));


            cursor.close();

            return paiement;
        }
        return null;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Paiement> getAllPaiements() {
        List<Paiement> paiements = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Paiement.TABLE_NAME + " ORDER BY " +
                Paiement.COLUMN_DATE_PAIEMENT + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Paiement paiement = new Paiement();
                paiement.setId(cursor.getInt(cursor.getColumnIndex(Paiement.COLUMN_ID)));
                paiement.setMontant(cursor.getFloat(cursor.getColumnIndex(Paiement.COLUMN_MONTANT)));
                paiement.setDatePaiement(cursor.getString(cursor.getColumnIndex(Paiement.COLUMN_DATE_PAIEMENT)));
                paiement.setReservation_id(cursor.getInt(cursor.getColumnIndex(Paiement.COLUMN_RESERVATION_ID)));

                paiements.add(paiement);
            } while (cursor.moveToNext());
        }


        db.close();

        return paiements;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getPaiementsCount() {
        String countQuery = "SELECT  * FROM " + Paiement.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        return count;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public long addPaiement(Paiement paiement) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Paiement.COLUMN_MONTANT, paiement.getMontant());
        values.put(Paiement.COLUMN_DATE_PAIEMENT, paiement.getDatePaiement());
        values.put(Paiement.COLUMN_RESERVATION_ID, paiement.getReservation_id());


        long nbAffectedRows = db.insert(Paiement.TABLE_NAME, null, values);


        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updatePaiement(Paiement paiement) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Paiement.COLUMN_MONTANT, paiement.getMontant());
        values.put(Paiement.COLUMN_DATE_PAIEMENT, paiement.getDatePaiement());
        values.put(Paiement.COLUMN_RESERVATION_ID, paiement.getReservation_id());


        int nbAffectedRows= db.update (Paiement.TABLE_NAME, values, Paiement.COLUMN_ID + " = ?",
                new String[]{String.valueOf(paiement.getId())});

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deletePaiement(Paiement paiement) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Paiement.TABLE_NAME, Paiement.COLUMN_ID + " = ?",
                new String[]{String.valueOf(paiement.getId())});
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

