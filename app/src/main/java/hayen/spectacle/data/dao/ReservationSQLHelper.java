//package com.ift2905.reservation.database.dao;
package hayen.spectacle.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;


//import com.ift2905.reservation.database.entities.Reservation;
import hayen.spectacle.data.data.Reservation;



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

    private static volatile ReservationSQLHelper reservationSQLHelper;

    private SQLiteDatabase database;


    private ReservationSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    private ReservationSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public static ReservationSQLHelper getInstance(Context context){

        if(reservationSQLHelper == null){
            synchronized (ReservationSQLHelper.class){
                if(reservationSQLHelper == null){
                    reservationSQLHelper = new ReservationSQLHelper(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
                }
            }
        }
        return reservationSQLHelper;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public Reservation getReservationById(int id) {

        database = this.getReadableDatabase();

        Reservation reservation = null;

        Cursor cursor = database.query(Reservation.TABLE_NAME,
                new String[]{ Reservation.COLUMN_ID, Reservation.COLUMN_NUMERO_CONFIRMATION, Reservation.COLUMN_DATE_RESERVATION,
                Reservation.COLUMN_USER_ID},
                Reservation.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            reservation = new Reservation(
                    cursor.getInt(cursor.getColumnIndex(Reservation.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Reservation.COLUMN_NUMERO_CONFIRMATION)),
                    cursor.getString(cursor.getColumnIndex(Reservation.COLUMN_DATE_RESERVATION)),
                    cursor.getInt(cursor.getColumnIndex(Reservation.COLUMN_USER_ID)));

            cursor.close();
        }

        close();

        return reservation;

    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Reservation> getAllReservations() {

        List<Reservation> reservations = null;

        String selectQuery = "SELECT  * FROM " + Reservation.TABLE_NAME + " ORDER BY " +
                Reservation.COLUMN_DATE_RESERVATION  + " DESC";

        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            reservations = new ArrayList<>();
            do {
                Reservation reservation = new Reservation();
                reservation.setId(cursor.getInt(cursor.getColumnIndex(Reservation.COLUMN_ID)));
                reservation.setNumeroConfirmation(cursor.getString(cursor.getColumnIndex(Reservation.COLUMN_NUMERO_CONFIRMATION)));
                reservation.setDateReservation(cursor.getString(cursor.getColumnIndex(Reservation.COLUMN_DATE_RESERVATION)));
                reservation.setUserId(cursor.getInt(cursor.getColumnIndex(Reservation.COLUMN_USER_ID)));

                reservations.add(reservation);

            } while (cursor.moveToNext());

            cursor.close();
        }

        cursor.close();

        close();

        return reservations;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getReservationsCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Reservation.TABLE_NAME;
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


    public long addReservation(Reservation Reservation) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Reservation.COLUMN_NUMERO_CONFIRMATION, Reservation.getNumeroConfirmation());
        values.put(Reservation.COLUMN_DATE_RESERVATION, Reservation.getDateReservation());
        values.put(Reservation.COLUMN_USER_ID, Reservation.getUserId());

        long nbAffectedRows= database.insert(Reservation.TABLE_NAME, null, values);

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateReservation(Reservation Reservation) {
        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Reservation.COLUMN_NUMERO_CONFIRMATION, Reservation.getNumeroConfirmation());
        values.put(Reservation.COLUMN_DATE_RESERVATION, Reservation.getDateReservation());
        values.put(Reservation.COLUMN_USER_ID, Reservation.getUserId());

        int nbAffectedRows= database.update (Reservation.TABLE_NAME, values, Reservation.COLUMN_ID + " = ?",
                new String[]{String.valueOf(Reservation.getId())});

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteReservation(Reservation reservation) {
        //TODO: delete entry from reservation_siege table before

        database = this.getWritableDatabase();
        int result = database.delete(Reservation.TABLE_NAME, Reservation.COLUMN_ID + " = ?",
                new String[]{String.valueOf(reservation.getId())});

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
