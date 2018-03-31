//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;


//import com.ift2905.reservation.database.entities.Reservation;
import hayen.spectacle.database.entities.Reservation;



public class ReservationSQLHelper extends SQLiteOpenHelper {

    //private static final String TABLE_NAME = "reservation";

//    public static final String CREATE_TABLE_RESERVATION =
//            "CREATE TABLE IF NOT EXISTS " + Reservation.TABLE_NAME +
//                    " ("+ Reservation.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    Reservation.COLUMN_NUMERO_CONFIRMATION + "VARCHAR(20) UNIQUE NOT NULL, " +
//                    Reservation.COLUMN_DATE_RESERVATION +" DATETIME DEFAULT CURRENT_TIMESTAMP, " +
//                    Reservation.COLUMN_USER_ID + " INTEGER NOT NULL, spectacle_id INTEGER NOT NULL, " +
//                    " FOREIGN KEY (" + Reservation.COLUMN_USER_ID+ ") REFERENCES utilisateur(id), " +
//                    " FOREIGN KEY (" + Reservation.COLUMN_SPECTACLE_ID + ") REFERENCES spectacle(id))";


    public ReservationSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }


    public ReservationSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Reservation getReservationById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Reservation.TABLE_NAME,
                new String[]{ Reservation.COLUMN_ID, Reservation.COLUMN_NUMERO_CONFIRMATION, Reservation.COLUMN_DATE_RESERVATION,
                Reservation.COLUMN_USER_ID},
                Reservation.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Reservation reservation = new Reservation(
                    cursor.getInt(cursor.getColumnIndex(Reservation.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Reservation.COLUMN_NUMERO_CONFIRMATION)),
                    cursor.getString(cursor.getColumnIndex(Reservation.COLUMN_DATE_RESERVATION)),
                    cursor.getInt(cursor.getColumnIndex(Reservation.COLUMN_USER_ID)));

            cursor.close();

            return reservation;
        }
        return null;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Reservation.TABLE_NAME + " ORDER BY " +
                Reservation.COLUMN_DATE_RESERVATION  + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Reservation reservation = new Reservation();
                reservation.setId(cursor.getInt(cursor.getColumnIndex(Reservation.COLUMN_ID)));
                reservation.setNumeroConfirmation(cursor.getString(cursor.getColumnIndex(Reservation.COLUMN_NUMERO_CONFIRMATION)));
                reservation.setDateReservation(cursor.getString(cursor.getColumnIndex(Reservation.COLUMN_DATE_RESERVATION)));
                reservation.setUserId(cursor.getInt(cursor.getColumnIndex(Reservation.COLUMN_USER_ID)));

                reservations.add(reservation);
            } while (cursor.moveToNext());
        }


        db.close();

        return reservations;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getReservationsCount() {
        String countQuery = "SELECT  * FROM " + Reservation.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        return count;
    }


    //**********************************************************************************************
    //**********************************************************************************************


    public long addReservation(Reservation Reservation) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Reservation.COLUMN_NUMERO_CONFIRMATION, Reservation.getNumeroConfirmation());
        values.put(Reservation.COLUMN_DATE_RESERVATION, Reservation.getDateReservation());
        values.put(Reservation.COLUMN_USER_ID, Reservation.getUserId());

        long nbAffectedRows= db.insert(Reservation.TABLE_NAME, null, values);

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateReservation(Reservation Reservation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Reservation.COLUMN_NUMERO_CONFIRMATION, Reservation.getNumeroConfirmation());
        values.put(Reservation.COLUMN_DATE_RESERVATION, Reservation.getDateReservation());
        values.put(Reservation.COLUMN_USER_ID, Reservation.getUserId());

        int nbAffectedRows= db.update (Reservation.TABLE_NAME, values, Reservation.COLUMN_ID + " = ?",
                new String[]{String.valueOf(Reservation.getId())});

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteReservation(Reservation reservation) {
        //TODO: delete entry from reservation_siege table before

        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Reservation.TABLE_NAME, Reservation.COLUMN_ID + " = ?",
                new String[]{String.valueOf(reservation.getId())});
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
