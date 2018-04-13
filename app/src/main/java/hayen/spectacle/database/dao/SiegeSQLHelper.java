//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import com.ift2905.reservation.database.entities.Siege;
import hayen.spectacle.database.data.Siege;


import java.util.ArrayList;
import java.util.List;



public class SiegeSQLHelper extends SQLiteOpenHelper {

    //private static final String TABLE_NAME = "siege";

//    public static final String CREATE_TABLE_SIEGE=
//            "CREATE TABLE IF NOT EXISTS " + Siege.TABLE_NAME +
//                    " (" + Siege.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    Siege.COLUMN_RANGEE + " VARCHAR(3) NOT NULL, " +
//                    Siege.COLUMN_NUMERO + " INTEGER NOT NULL, " +
//                    Siege.COLUMN_RESERVE + " TINYINT DEFAULT 0, " +
//                    Siege.COLUMN_SECTION_ID + " INTEGER, FOREIGN KEY (" + Siege.COLUMN_SECTION_ID + ") REFERENCES " +
//                    Siege.TABLE_NAME_REF_SECTION + "(" + Siege.COLUMN_ID + ") )";


    private static volatile SiegeSQLHelper siegeSQLHelper;

    private SQLiteDatabase database;

    private SiegeSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    private SiegeSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public static SiegeSQLHelper getInstance(Context context){

        if(siegeSQLHelper == null){
            synchronized (SiegeSQLHelper.class){
                if(siegeSQLHelper == null){
                    siegeSQLHelper = new SiegeSQLHelper(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
                }
            }
        }
        return siegeSQLHelper;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public Siege getSiegeById(int id) {


        database = this.getReadableDatabase();

        Siege siege = null;

        Cursor cursor = database.query(Siege.TABLE_NAME,
                new String[]{   Siege.COLUMN_ID, Siege.COLUMN_RANGEE, Siege.COLUMN_COLUMN,
                            Siege.COLUMN_SECTION_ID },
                Siege.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            siege = new Siege(
                    cursor.getInt(cursor.getColumnIndex(Siege.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Siege.COLUMN_RANGEE)),
                    cursor.getInt(cursor.getColumnIndex(Siege.COLUMN_COLUMN)),
                    cursor.getInt(cursor.getColumnIndex(Siege.COLUMN_SECTION_ID)));

            cursor.close();
        }

        close();

        return siege;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Siege> getAllSieges() {

        database = this.getReadableDatabase();

        List<Siege> sieges = null;

        String selectQuery = "SELECT  * FROM " + Siege.TABLE_NAME + " ORDER BY " +
                Siege.COLUMN_RANGEE + ", " + Siege.COLUMN_COLUMN + " ASC";

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            sieges = new ArrayList<>();

            do {
                Siege siege = new Siege();
                siege.setId(cursor.getInt(cursor.getColumnIndex(Siege.COLUMN_ID)));
                siege.setRangee(cursor.getString(cursor.getColumnIndex(Siege.COLUMN_RANGEE)));
                siege.setColonne(cursor.getInt(cursor.getColumnIndex(Siege.COLUMN_COLUMN)));
                siege.setSectionId(cursor.getInt(cursor.getColumnIndex(Siege.COLUMN_SECTION_ID)));

                sieges.add(siege);

            } while (cursor.moveToNext());

            cursor.close();
        }


        close();

        return sieges;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getSiegesCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Siege.TABLE_NAME;

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


    public long addSiege(Siege siege) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Siege.COLUMN_RANGEE, siege.getRangee());
        values.put(Siege.COLUMN_COLUMN, siege.getColonne());
        values.put(Siege.COLUMN_SECTION_ID, siege.getSectionId());

        long nbAffectedRows= database.insert (Siege.TABLE_NAME, null, values);

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateSiege(Siege siege) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Siege.COLUMN_RANGEE, siege.getRangee());
        values.put(Siege.COLUMN_COLUMN, siege.getColonne());
        values.put(Siege.COLUMN_SECTION_ID, siege.getSectionId());

        int nbAffectedRows= database.update (Siege.TABLE_NAME, values, Siege.COLUMN_ID + " = ?",
                new String[]{String.valueOf(siege.getId())});

        close();

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteSiege(Siege siege) {

        database = this.getWritableDatabase();
        int result = database.delete(Siege.TABLE_NAME, Siege.COLUMN_ID + " = ?",
                new String[]{String.valueOf(siege.getId())});

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

