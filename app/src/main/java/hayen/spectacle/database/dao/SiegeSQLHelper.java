//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import com.ift2905.reservation.database.entities.Siege;
import hayen.spectacle.database.entities.Siege;


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


    public SiegeSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    public SiegeSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Siege getSiegeById(int id) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Siege.TABLE_NAME,
                new String[]{   Siege.COLUMN_ID, Siege.COLUMN_RANGEE, Siege.COLUMN_COLUMN,
                            Siege.COLUMN_SECTION_ID },
                Siege.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Siege siege = new Siege(
                    cursor.getInt(cursor.getColumnIndex(Siege.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Siege.COLUMN_RANGEE)),
                    cursor.getInt(cursor.getColumnIndex(Siege.COLUMN_COLUMN)),
                    cursor.getInt(cursor.getColumnIndex(Siege.COLUMN_SECTION_ID)));

            cursor.close();

            return siege;
        }
        return null;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Siege> getAllSieges() {
        List<Siege> sieges = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Siege.TABLE_NAME + " ORDER BY " +
                Siege.COLUMN_RANGEE + ", " + Siege.COLUMN_COLUMN + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Siege siege = new Siege();
                siege.setId(cursor.getInt(cursor.getColumnIndex(Siege.COLUMN_ID)));
                siege.setRangee(cursor.getString(cursor.getColumnIndex(Siege.COLUMN_RANGEE)));
                siege.setColonne(cursor.getInt(cursor.getColumnIndex(Siege.COLUMN_COLUMN)));
                siege.setSectionId(cursor.getInt(cursor.getColumnIndex(Siege.COLUMN_SECTION_ID)));

                sieges.add(siege);
            } while (cursor.moveToNext());
        }


        db.close();

        return sieges;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getSiegesCount() {
        String countQuery = "SELECT  * FROM " + Siege.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        return count;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public long addSiege(Siege siege) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Siege.COLUMN_RANGEE, siege.getRangee());
        values.put(Siege.COLUMN_COLUMN, siege.getColonne());
        values.put(Siege.COLUMN_SECTION_ID, siege.getSectionId());

        long nbAffectedRows= db.insert (Siege.TABLE_NAME, null, values);

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateSiege(Siege siege) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Siege.COLUMN_RANGEE, siege.getRangee());
        values.put(Siege.COLUMN_COLUMN, siege.getColonne());
        values.put(Siege.COLUMN_SECTION_ID, siege.getSectionId());

        int nbAffectedRows= db.update (Siege.TABLE_NAME, values, Siege.COLUMN_ID + " = ?",
                new String[]{String.valueOf(siege.getId())});

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteSiege(Siege siege) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Siege.TABLE_NAME, Siege.COLUMN_ID + " = ?",
                new String[]{String.valueOf(siege.getId())});
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

