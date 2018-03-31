//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import com.ift2905.reservation.database.entities.Salle;
import hayen.spectacle.database.entities.Salle;

import java.util.ArrayList;
import java.util.List;



public class SalleSQLHelper extends SQLiteOpenHelper {

  //  private static final String TABLE_NAME = "salle";

    public static final String CREATE_TABLE_SALLE =
            "CREATE TABLE IF NOT EXISTS salle (id INTEGER PRIMARY KEY AUTOINCREMENT, nom VARCHAR(30), telephone VARCHAR(20), fax VARCHAR(30), " +
                    " courriel VARCHAR(30), adresse_id INTEGER, description TEXT, FOREIGN KEY (adresse_id) REFERENCES adresse(id)) ";


    public SalleSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }
    public SalleSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public Salle getSalleById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(   Salle.TABLE_NAME,
                new String[]{       Salle.COLUMN_ID, Salle.COLUMN_NAME, Salle.COLUMN_TELEPHONE, Salle.COLUMN_FAX,
                                    Salle.COLUMN_COURRIEL, Salle.COLUMN_DESCRIPTION, Salle.COLUMN_ADRESSE_ID},
                            Salle.COLUMN_ID + "=?",
                                    new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();


            Salle salle = new Salle(
                    cursor.getInt(cursor.getColumnIndex(Salle.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Salle.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(Salle.COLUMN_TELEPHONE)),
                    cursor.getString(cursor.getColumnIndex(Salle.COLUMN_FAX)),
                    cursor.getString(cursor.getColumnIndex(Salle.COLUMN_COURRIEL)),
                    cursor.getInt(cursor.getColumnIndex(Salle.COLUMN_ADRESSE_ID)),
                    cursor.getString(cursor.getColumnIndex(Salle.COLUMN_DESCRIPTION)));


            cursor.close();

            return salle;
        }
        return null;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Salle> getAllSalles() {
        List<Salle> salles = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Salle.TABLE_NAME + " ORDER BY " +
                Salle.COLUMN_ID +  " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Salle salle = new Salle();
                salle.setId(cursor.getInt(cursor.getColumnIndex(Salle.COLUMN_ID)));
                salle.setNom(cursor.getString(cursor.getColumnIndex(Salle.COLUMN_NAME)));
                salle.setTelephone(cursor.getString(cursor.getColumnIndex(Salle.COLUMN_TELEPHONE)));
                salle.setFax(cursor.getString(cursor.getColumnIndex(Salle.COLUMN_FAX)));
                salle.setCourriel(cursor.getString(cursor.getColumnIndex(Salle.COLUMN_COURRIEL)));
                salle.setAdresseId(cursor.getInt(cursor.getColumnIndex(Salle.COLUMN_ADRESSE_ID)));
                salle.setDescription(cursor.getString(cursor.getColumnIndex(Salle.COLUMN_DESCRIPTION)));

                salles.add(salle);

            } while (cursor.moveToNext());
        }


        db.close();

        return salles;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getSallesCount() {
        String countQuery = "SELECT  * FROM " + Salle.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        return count;
    }



    //**********************************************************************************************
    //**********************************************************************************************


    public long addSalle(Salle salle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Salle.COLUMN_NAME, salle.getNom());
        values.put(Salle.COLUMN_TELEPHONE, salle.getTelephone());
        values.put(Salle.COLUMN_FAX, salle.getFax());
        values.put(Salle.COLUMN_COURRIEL, salle.getCourriel());
        values.put(Salle.COLUMN_DESCRIPTION, salle.getDescription());
        values.put(Salle.COLUMN_ADRESSE_ID, salle.getAdresseId());

        long nbAffectedRows= db.insert(Salle.TABLE_NAME, null, values);

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateSalle(Salle salle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Salle.COLUMN_NAME, salle.getNom());
        values.put(Salle.COLUMN_TELEPHONE, salle.getTelephone());
        values.put(Salle.COLUMN_FAX, salle.getFax());
        values.put(Salle.COLUMN_COURRIEL, salle.getCourriel());
        values.put(Salle.COLUMN_DESCRIPTION, salle.getDescription());
        values.put(Salle.COLUMN_ADRESSE_ID, salle.getAdresseId());

        int nbAffectedRows= db.update (Salle.TABLE_NAME, values, Salle.COLUMN_ID + " = ?",
                new String[]{String.valueOf(salle.getId())});

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteSalle(Salle salle) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Salle.TABLE_NAME, Salle.COLUMN_ID + " = ?",
                new String[]{String.valueOf(salle.getId())});
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

