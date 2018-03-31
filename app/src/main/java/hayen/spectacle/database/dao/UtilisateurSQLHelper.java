//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


//import com.ift2905.reservation.database.entities.Utilisateur;

import hayen.spectacle.database.entities.Utilisateur;


import java.util.ArrayList;
import java.util.List;



public class UtilisateurSQLHelper  extends SQLiteOpenHelper {

    //  private static final String TABLE_NAME = "utilisateur";

    public static final String CREATE_TABLE_UTILISATEUR =
            "CREATE TABLE IF NOT EXISTS " + Utilisateur.TABLE_NAME +
            " (" + Utilisateur.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Utilisateur.COLUMN_PRENOM + " VARCHAR(30) NOT NULL, " +
            Utilisateur.COLUMN_NOM + " VARCHAR(30) NOT NULL, " +
            Utilisateur.COLUMN_LOGIN + " VARCHAR(30) UNIQUE NOT NULL, " +
            Utilisateur.COLUMN_MOT_PASSE + " VARCHAR(80) NOT NULL, " +
            Utilisateur.COLUMN_COURRIEL  + " VARCHAR(40) UNIQUE NOT NULL, " +
            Utilisateur.COLUMN_ADRESSE_ID + " INTEGER DEFAULT NULL, " +
            "FOREIGN KEY (" + Utilisateur.COLUMN_ADRESSE_ID + ") REFERENCES " + Utilisateur.TABLE_NAME_REF_ADRESSE + "(id) )";


    public UtilisateurSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    public UtilisateurSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Utilisateur getUtilisateurById(int id) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(   Utilisateur.TABLE_NAME,
                new String[]{       Utilisateur.COLUMN_ID, Utilisateur.COLUMN_PRENOM, Utilisateur.COLUMN_NOM,
                                    Utilisateur.COLUMN_LOGIN, Utilisateur.COLUMN_MOT_PASSE, Utilisateur.COLUMN_COURRIEL,
                                    Utilisateur.COLUMN_TELEPHONE, Utilisateur.COLUMN_ADRESSE_ID},
                Utilisateur.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Utilisateur utilisateur = new Utilisateur(
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_NOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_MOT_PASSE)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_COURRIEL)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_TELEPHONE)),
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ADRESSE_ID)));

            cursor.close();

            return utilisateur;
        }
        return null;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public Utilisateur getUtilisateurByName(Utilisateur utilisateur) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(   Utilisateur.TABLE_NAME,
                new String[]{       Utilisateur.COLUMN_ID, Utilisateur.COLUMN_PRENOM, Utilisateur.COLUMN_NOM,
                        Utilisateur.COLUMN_LOGIN, Utilisateur.COLUMN_MOT_PASSE, Utilisateur.COLUMN_COURRIEL,
                        Utilisateur.COLUMN_TELEPHONE, Utilisateur.COLUMN_ADRESSE_ID},
                Utilisateur.COLUMN_PRENOM + "=? and " + Utilisateur.COLUMN_NOM + "=?",
                new String[]{utilisateur.getPrenom(), utilisateur.getNom()}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Utilisateur utilisateur2 = new Utilisateur(
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_NOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_MOT_PASSE)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_COURRIEL)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_TELEPHONE)),
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ADRESSE_ID)));

            cursor.close();

            return utilisateur2;
        }
        return null;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Utilisateur getUtilisateurByLogin(Utilisateur utilisateur) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(   Utilisateur.TABLE_NAME,
                new String[]{ Utilisateur.COLUMN_ID, Utilisateur.COLUMN_PRENOM, Utilisateur.COLUMN_NOM,
                        Utilisateur.COLUMN_LOGIN, Utilisateur.COLUMN_MOT_PASSE, Utilisateur.COLUMN_COURRIEL,
                        Utilisateur.COLUMN_TELEPHONE, Utilisateur.COLUMN_ADRESSE_ID},
                Utilisateur.COLUMN_LOGIN + "=? or " + Utilisateur.COLUMN_COURRIEL + "=?",
                new String[]{utilisateur.getLogin(), utilisateur.getCourriel()}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Utilisateur utilisateur2 = new Utilisateur(
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_NOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_MOT_PASSE)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_COURRIEL)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_TELEPHONE)),
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ADRESSE_ID)));

            cursor.close();

            return utilisateur2;
        }
        return null;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public Utilisateur validateLogin(Utilisateur utilisateur) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(   Utilisateur.TABLE_NAME,
                new String[]{ Utilisateur.COLUMN_ID, Utilisateur.COLUMN_PRENOM, Utilisateur.COLUMN_NOM,
                        Utilisateur.COLUMN_LOGIN, Utilisateur.COLUMN_MOT_PASSE, Utilisateur.COLUMN_COURRIEL,
                        Utilisateur.COLUMN_TELEPHONE, Utilisateur.COLUMN_ADRESSE_ID},
                Utilisateur.COLUMN_LOGIN + "=? or " + Utilisateur.COLUMN_COURRIEL + "=? and " + Utilisateur.COLUMN_MOT_PASSE + "=?",
                new String[]{utilisateur.getLogin(), utilisateur.getCourriel(), utilisateur.getMotPasse()}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Utilisateur utilisateur2 = new Utilisateur(
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_NOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_MOT_PASSE)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_COURRIEL)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_TELEPHONE)),
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ADRESSE_ID)));

            cursor.close();

            return utilisateur2;
        }
        return null;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Utilisateur.TABLE_NAME + " ORDER BY " +
                Utilisateur.COLUMN_NOM + ", " + Utilisateur.COLUMN_PRENOM + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ID)));
                utilisateur.setPrenom(cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_PRENOM)));
                utilisateur.setNom(cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_NOM)));
                utilisateur.setLogin(cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_LOGIN)));
                utilisateur.setMotPasse(cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_MOT_PASSE)));
                utilisateur.setCourriel(cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_COURRIEL)));
                utilisateur.setTelephone(cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_TELEPHONE)));

                utilisateur.setAdresseId(cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ADRESSE_ID)));


                utilisateurs.add(utilisateur);
            } while (cursor.moveToNext());
        }


        db.close();

        return utilisateurs;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getUtilisateursCount() {
        String countQuery = "SELECT  * FROM " + Utilisateur.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        return count;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public long addUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(utilisateur.COLUMN_PRENOM, utilisateur.getPrenom());
        values.put(utilisateur.COLUMN_NOM, utilisateur.getNom());
        values.put(utilisateur.COLUMN_LOGIN, utilisateur.getLogin());
        values.put(utilisateur.COLUMN_MOT_PASSE, utilisateur.getMotPasse());
        values.put(utilisateur.COLUMN_COURRIEL, utilisateur.getCourriel());
        values.put(utilisateur.COLUMN_TELEPHONE, utilisateur.getTelephone());
        if(utilisateur.getAdresseId() > 0){
            values.put(utilisateur.COLUMN_ADRESSE_ID, utilisateur.getAdresseId());
        }



        long nbAffectedRows= db.insert(Utilisateur.TABLE_NAME, null, values);

        return nbAffectedRows;
    }


    //**********************************************************************************************
    //**********************************************************************************************


    public int updateUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilisateur.COLUMN_PRENOM, utilisateur.getPrenom());
        values.put(Utilisateur.COLUMN_NOM, utilisateur.getNom());
        values.put(Utilisateur.COLUMN_LOGIN, utilisateur.getLogin());
        values.put(Utilisateur.COLUMN_MOT_PASSE, utilisateur.getMotPasse());
        values.put(Utilisateur.COLUMN_COURRIEL, utilisateur.getCourriel());
        values.put(Utilisateur.COLUMN_TELEPHONE, utilisateur.getTelephone());
        values.put(Utilisateur.COLUMN_ADRESSE_ID, utilisateur.getAdresseId());



        int nbAffectedRows = db.update(Utilisateur.TABLE_NAME, values, Utilisateur.COLUMN_ID + " = ?",
                new String[]{String.valueOf(utilisateur.getId())});

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Utilisateur.TABLE_NAME, Utilisateur.COLUMN_ID + " = ?",
                new String[]{String.valueOf(utilisateur.getId())});
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


