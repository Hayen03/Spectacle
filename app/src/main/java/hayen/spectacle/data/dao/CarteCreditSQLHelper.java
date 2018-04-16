//package com.ift2905.reservation.database.dao;
package hayen.spectacle.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import com.ift2905.reservation.database.entities.CarteCredit;
import hayen.spectacle.data.data.CarteCredit;

import java.util.ArrayList;
import java.util.List;


public class CarteCreditSQLHelper extends SQLiteOpenHelper {

//    private static final String TABLE_NAME = "carte_credit";

//    public static final String CREATE_TABLE_CARTE_CREDIT =
//            "CREATE TABLE IF NOT EXISTS carte_credit (id INTEGER PRIMARY KEY AUTOINCREMENT, nom_carte VARCHAR(20), " +
//                    " nom_utilisateur VARCHAR(30), numero VARCHAR(20), " +
//                    " date_expiration DATETIME DEFAULT CURRENT_TIMESTAMP, code INTEGER, utilisateur_id INTEGER, "+
//                    " UNIQUE(nom_carte, numero), FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id))";


    private static volatile CarteCreditSQLHelper carteCreditSQLHelper;

    private SQLiteDatabase database;

    private CarteCreditSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    private CarteCreditSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public static CarteCreditSQLHelper getInstance(Context context){

        if(carteCreditSQLHelper == null){
            synchronized (CarteCreditSQLHelper.class){
                if(carteCreditSQLHelper == null){
                    carteCreditSQLHelper = new CarteCreditSQLHelper(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
                }
            }
        }
        return carteCreditSQLHelper;
    }



    //**********************************************************************************************
    //**********************************************************************************************

    public CarteCredit getCarteByUserId(int userId) {

        database = this.getReadableDatabase();

        CarteCredit carteCredit = null;

        Cursor cursor = database.query(CarteCredit.TABLE_NAME,
                new String[]{   CarteCredit.COLUMN_ID, CarteCredit.COLUMN_UTILISATEUR_ID, CarteCredit.COLUMN_NOM_CARTE, CarteCredit.COLUMN_NOM_UTILISATEUR,
                                CarteCredit.COLUMN_NUMERO, CarteCredit.COLUMN_DATE_EXPIRATION, CarteCredit.COLUMN_CODE },
                        CarteCredit.COLUMN_UTILISATEUR_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, null, null);


        String[] names =  cursor.getColumnNames();

        if (cursor != null && cursor.moveToFirst()) {

            carteCredit = new CarteCredit(
                    cursor.getInt(cursor.getColumnIndex(CarteCredit.COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(CarteCredit.COLUMN_UTILISATEUR_ID)),
                    cursor.getString(cursor.getColumnIndex(CarteCredit.COLUMN_NOM_CARTE)),
                    cursor.getString(cursor.getColumnIndex(CarteCredit.COLUMN_NOM_UTILISATEUR)),
                    cursor.getString(cursor.getColumnIndex(CarteCredit.COLUMN_NUMERO)),
                    cursor.getString(cursor.getColumnIndex(CarteCredit.COLUMN_DATE_EXPIRATION)),
                    cursor.getInt(cursor.getColumnIndex(CarteCredit.COLUMN_CODE))
                    );

            cursor.close();
        }

        close();

        return carteCredit;
    }



    //**********************************************************************************************
    //**********************************************************************************************

    public List<CarteCredit> getAllCartes() {

        database = this.getReadableDatabase();

        List<CarteCredit> cartes = null;

        String selectQuery = "SELECT  * FROM " + CarteCredit.TABLE_NAME + " ORDER BY " +
                CarteCredit.COLUMN_ID + " ASC";



        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            cartes =  new ArrayList<>();

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

            cursor.close();
        }

        close();

        return cartes;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getCartesCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + CarteCredit.TABLE_NAME;

        Cursor cursor = database.rawQuery(countQuery, null);

        int count = 0;
        if(cursor != null){
            count = cursor.getCount();
            cursor.close();
        }

        close();

        return count;
    }


    //**********************************************************************************************
    //**********************************************************************************************


    public long addCarte(CarteCredit carte) {
        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CarteCredit.COLUMN_NOM_CARTE, carte.getNomCarte());
        values.put(CarteCredit.COLUMN_NOM_UTILISATEUR, carte.getNomUtilisateur());
        values.put(CarteCredit.COLUMN_NUMERO, carte.getNumero());
        values.put(CarteCredit.COLUMN_DATE_EXPIRATION, carte.getDate_expiration());
        values.put(CarteCredit.COLUMN_CODE, carte.getCode());
        values.put(CarteCredit.COLUMN_UTILISATEUR_ID, carte.getUtilisateurId());

        long nbAffectedRows= database.insert (CarteCredit.TABLE_NAME, null, values);

        close();
        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateCarte(CarteCredit carte) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CarteCredit.COLUMN_NOM_CARTE, carte.getNomCarte());
        values.put(CarteCredit.COLUMN_NOM_UTILISATEUR, carte.getNomUtilisateur());
        values.put(CarteCredit.COLUMN_NUMERO, carte.getNumero());
        values.put(CarteCredit.COLUMN_DATE_EXPIRATION, carte.getDate_expiration());
        values.put(CarteCredit.COLUMN_CODE, carte.getCode());
        values.put(CarteCredit.COLUMN_UTILISATEUR_ID, carte.getUtilisateurId());

        int nbAffectedRows= database.update (CarteCredit.TABLE_NAME, values, CarteCredit.COLUMN_ID + " = ?",
                new String[]{String.valueOf(carte.getId())});

        close();
        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteCarteCredit(CarteCredit carte) {

        database = this.getWritableDatabase();
        int result = database.delete(CarteCredit.TABLE_NAME, CarteCredit.COLUMN_ID + " = ?",
                new String[]{String.valueOf(carte.getId())});
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

