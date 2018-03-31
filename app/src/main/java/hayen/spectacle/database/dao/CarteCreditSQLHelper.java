//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import com.ift2905.reservation.database.entities.CarteCredit;
import hayen.spectacle.database.entities.CarteCredit;

import java.util.ArrayList;
import java.util.List;


public class CarteCreditSQLHelper extends SQLiteOpenHelper {

//    private static final String TABLE_NAME = "carte_credit";

//    public static final String CREATE_TABLE_CARTE_CREDIT =
//            "CREATE TABLE IF NOT EXISTS carte_credit (id INTEGER PRIMARY KEY AUTOINCREMENT, nom_carte VARCHAR(20), " +
//                    " nom_utilisateur VARCHAR(30), numero VARCHAR(20), " +
//                    " date_expiration DATETIME DEFAULT CURRENT_TIMESTAMP, code INTEGER, utilisateur_id INTEGER, "+
//                    " UNIQUE(nom_carte, numero), FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id))";


    public CarteCreditSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    public CarteCreditSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public CarteCredit getCarteByUserId(int userId) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CarteCredit.TABLE_NAME,
                new String[]{   CarteCredit.COLUMN_ID, CarteCredit.COLUMN_UTILISATEUR_ID, CarteCredit.COLUMN_NOM_CARTE, CarteCredit.COLUMN_NOM_UTILISATEUR,
                                CarteCredit.COLUMN_NUMERO, CarteCredit.COLUMN_DATE_EXPIRATION, CarteCredit.COLUMN_CODE },
                        CarteCredit.COLUMN_UTILISATEUR_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, null, null);

     //   Log.i("RPI", "curseur: " + cursor.getCount());

        String[] names =  cursor.getColumnNames();

    //    for (int i = 0; i < names.length ; i++) {
     //       Log.i("RPI", "name : " + names[i]);
    //    }


        if (cursor != null) {
            cursor.moveToFirst();

        //    Log.i("RPI", "name: "+ cursor.getColumnName(5));
         //   Log.i("RPI", "column name: "+ cursor.getColumnIndex(CarteCredit.COLUMN_UTILISATEUR_ID));


            CarteCredit carteCredit = new CarteCredit(
                    cursor.getInt(cursor.getColumnIndex(CarteCredit.COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(CarteCredit.COLUMN_UTILISATEUR_ID)),
                    cursor.getString(cursor.getColumnIndex(CarteCredit.COLUMN_NOM_CARTE)),
                    cursor.getString(cursor.getColumnIndex(CarteCredit.COLUMN_NOM_UTILISATEUR)),
                    cursor.getString(cursor.getColumnIndex(CarteCredit.COLUMN_NUMERO)),
                    cursor.getString(cursor.getColumnIndex(CarteCredit.COLUMN_DATE_EXPIRATION)),
                    cursor.getInt(cursor.getColumnIndex(CarteCredit.COLUMN_CODE))
                    );

            cursor.close();

            return carteCredit;

        }
        return null;
    }



    //**********************************************************************************************
    //**********************************************************************************************

    public List<CarteCredit> getAllCartes() {
        List<CarteCredit> cartes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + CarteCredit.TABLE_NAME + " ORDER BY " +
                CarteCredit.COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CarteCredit carte = new CarteCredit();
                carte.setId(cursor.getInt(cursor.getColumnIndex(CarteCredit.COLUMN_ID)));
                carte.setNomCarte(cursor.getString(cursor.getColumnIndex(CarteCredit.COLUMN_NOM_CARTE)));
                carte.setNomUtilisateur(cursor.getString(cursor.getColumnIndex(CarteCredit.COLUMN_NOM_UTILISATEUR)));
                carte.setNumero(cursor.getString(cursor.getColumnIndex(CarteCredit.COLUMN_NUMERO)));
                carte.setDate_expiration(cursor.getString(cursor.getColumnIndex(CarteCredit.COLUMN_DATE_EXPIRATION)));
                carte.setCode(cursor.getInt(cursor.getColumnIndex(CarteCredit.COLUMN_CODE)));
                carte.setUtilisateurId(cursor.getInt(cursor.getColumnIndex(CarteCredit.COLUMN_UTILISATEUR_ID)));

                cartes.add(carte);
            } while (cursor.moveToNext());
        }


        db.close();

        return cartes;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getCartesCount() {
        String countQuery = "SELECT  * FROM " + CarteCredit.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        return count;
    }


    //**********************************************************************************************
    //**********************************************************************************************


    public long addCarte(CarteCredit carte) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CarteCredit.COLUMN_NOM_CARTE, carte.getNomCarte());
        values.put(CarteCredit.COLUMN_NOM_UTILISATEUR, carte.getNomUtilisateur());
        values.put(CarteCredit.COLUMN_NUMERO, carte.getNumero());
        values.put(CarteCredit.COLUMN_DATE_EXPIRATION, carte.getDate_expiration());
        values.put(CarteCredit.COLUMN_CODE, carte.getCode());
        values.put(CarteCredit.COLUMN_UTILISATEUR_ID, carte.getUtilisateurId());

        long nbAffectedRows= db.insert (CarteCredit.TABLE_NAME, null, values);

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateCarte(CarteCredit carte) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CarteCredit.COLUMN_NOM_CARTE, carte.getNomCarte());
        values.put(CarteCredit.COLUMN_NOM_UTILISATEUR, carte.getNomUtilisateur());
        values.put(CarteCredit.COLUMN_NUMERO, carte.getNumero());
        values.put(CarteCredit.COLUMN_DATE_EXPIRATION, carte.getDate_expiration());
        values.put(CarteCredit.COLUMN_CODE, carte.getCode());
        values.put(CarteCredit.COLUMN_UTILISATEUR_ID, carte.getUtilisateurId());

        int nbAffectedRows= db.update (CarteCredit.TABLE_NAME, values, CarteCredit.COLUMN_ID + " = ?",
                new String[]{String.valueOf(carte.getId())});

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteCarteCredit(CarteCredit carte) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(CarteCredit.TABLE_NAME, CarteCredit.COLUMN_ID + " = ?",
                new String[]{String.valueOf(carte.getId())});
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

