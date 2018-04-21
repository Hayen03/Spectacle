//package com.ift2905.reservation.database.dao;
package hayen.spectacle.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hayen.spectacle.data.data.Adresse;
import hayen.spectacle.data.data.Artiste;
import hayen.spectacle.data.data.CarteCredit;
import hayen.spectacle.data.data.Genre;
import hayen.spectacle.data.data.Paiement;
import hayen.spectacle.data.data.Reservation;
import hayen.spectacle.data.data.Salle;
import hayen.spectacle.data.data.Section;
import hayen.spectacle.data.data.Siege;
import hayen.spectacle.data.data.Spectacle;
import hayen.spectacle.data.data.SpectacleArtiste;
import hayen.spectacle.data.data.SpectacleSection;
import hayen.spectacle.data.data.Utilisateur;


public class DatabaseHelper extends SQLiteOpenHelper{



    private SQLiteDatabase database;

    private Context context;

    private static volatile DatabaseHelper databaseHelper;

    private DatabaseHelper(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        this.context =  context;

    }
    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        this.context =  context;

        //Uncomment to delete the database at the start and recreate it
       //this.context.deleteDatabase(Constant.DATABASE_NAME);



    }
    public static DatabaseHelper getInstance(Context context){

        if(databaseHelper == null){
            synchronized (DatabaseHelper.class){
                if(databaseHelper == null){
                    databaseHelper = new DatabaseHelper(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
                }
            }
        }
        return databaseHelper;
    }

    //*******************************************************************************************************
    //*******************************************************************************************************



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("RPI", "In SQLHelper onCreate: " + sqLiteDatabase);


        database =  sqLiteDatabase;

        //delete tables
        dropDB();
        //create tables
        createDB();
        //load tables
        loadDB();


    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

 /*       Log.i("RPI", "Retrait des tables");
        for (int j = 0; j < DatabaseQueries.DROP_TABLES_QUERIES.length; j++) {

            String querie =  DatabaseQueries.DROP_TABLES_QUERIES[j];
            database.execSQL(querie);

            Log.i("RPI", "Retrait de la table: " + querie);
        }
*/
        database = sqLiteDatabase;
      //  dropDB();
        onCreate(sqLiteDatabase);

    }
    @Override
    public synchronized void close() {

        try {
            if(database.isOpen()) {
                database.close();
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



    private void dropDB(){



        Log.i("RPI", "Retrait des tables");
        for (int i = 0; i < DatabaseQueries.DROP_TABLES_QUERIES.length; i++) {
            String querie =  DatabaseQueries.DROP_TABLES_QUERIES[i];
            if (querie != null) {
                database.execSQL(querie);
                Log.i("RPI", "Retrait de la table: " + querie);
            }
            else {
                Log.e("DB", "null querie in dbhelper dropDB()");
            }
        }


    }
    private void createDB(){

        Log.i("RPI", "Création des tables dans la BD");

        for (int i = 0; i < DatabaseQueries.CREATE_TABLES_RESERVATION.length; i++) {
            String table_name = DatabaseQueries.TABLE_RESERVATION_NAMES[i];
            String querie =  DatabaseQueries.CREATE_TABLES_RESERVATION[i];

            database.execSQL(querie);

            Log.i("RPI", "Création de la table '" + table_name +"'");

            String selectQuery = "SELECT * FROM  " + table_name + " ";
            Cursor result = database.rawQuery(selectQuery, null);
            int nbColumns = result.getColumnCount();
            Log.i("RPI", "Nombre de colonnes créées dans la table '"+table_name+"': " + String.valueOf(nbColumns));
        }


        Log.i("RPI", "Création des tables réussie ");

    }
    private void loadDB(){


        //

        Log.i("RPI", "Insertion dans la table adresse");
        String insertQuery = DatabaseQueries.INSERT_ADRESSE_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        String selectQuery = "select * from adresse";
        Cursor result = database.rawQuery(selectQuery, null);
        int nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'adresse': " + String.valueOf(nbRows));


        Log.i("RPI", "Insertion dans la table salle");
        insertQuery = DatabaseQueries.INSERT_SALLE_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from salle";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'salle': " + String.valueOf(nbRows));

        Log.i("RPI", "Insertion dans la table utilisateur");
        insertQuery = DatabaseQueries.INSERT_USER_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from utilisateur";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'utilsateur': " + String.valueOf(nbRows));


        Log.i("RPI", "Insertion dans la table genre");
        insertQuery = DatabaseQueries.INSERT_GENRE_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from genre";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'genre': " + String.valueOf(nbRows));

        Log.i("RPI", "Insertion dans la table artiste");
        insertQuery = DatabaseQueries.INSERT_ARTISTE_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from artiste";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'artiste': " + String.valueOf(nbRows));

        Log.i("RPI", "Insertion dans la table spectacle");
        insertQuery = DatabaseQueries.INSERT_SPECTACLE_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from spectacle";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'spectacle': " + String.valueOf(nbRows));

        Log.i("RPI", "Insertion dans la table spectacle_artiste");
        insertQuery = DatabaseQueries.INSERT_SPECTACLE_ARTISTE_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from spectacle_artiste";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'spectacle_artiste': " + String.valueOf(nbRows));

        Log.i("RPI", "Insertion dans la table section");
        insertQuery = DatabaseQueries.INSERT_SECTION_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from section";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'section': " + String.valueOf(nbRows));


        Log.i("RPI", "Insertion dans la table spectacle_section");
        insertQuery = DatabaseQueries.INSERT_SPECTACLE_SECTION_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from spectacle_section";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'spectacle_section': " + String.valueOf(nbRows));


        Log.i("RPI", "Insertion dans la table siege");
        insertQuery = DatabaseQueries.INSERT_SIEGE_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from siege";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'siege': " + String.valueOf(nbRows));

        Log.i("RPI", "Insertion dans la table spectacle_siege");
        insertQuery = DatabaseQueries.INSERT_SPECTACLE_SIEGE_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from spectacle_siege";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'spectacle_siege': " + String.valueOf(nbRows));

        Log.i("RPI", "Insertion dans la table 'reservation");
        insertQuery = DatabaseQueries.INSERT_RESERVATION_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from reservation";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'reservation': " + String.valueOf(nbRows));


        Log.i("RPI", "Mise à jour de la table 'spectacle_siege");
        int i = 0;
        for ( i = 0; i < DatabaseQueries.UPDATE_SPECTACLE_SIEGE_QUERIES.length; i++) {
            String updateQuery = DatabaseQueries.UPDATE_SPECTACLE_SIEGE_QUERIES[i];
            Log.i("RPI", "Query: " + updateQuery);
            database.execSQL(updateQuery);
            Log.i("RPI", "Mise à jour de la table 'spectacle_siege': " + i);

        }
        Log.i("RPI", "Nombre de tuples mis à jour dans la table 'spectacle_siege': " + i);



        Log.i("RPI", "Insertion dans la table 'reservation_spectacle_siege");
        insertQuery = DatabaseQueries.INSERT_RESERVATION_SPECTACLE_SIEGE_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from reservation_spectacle_siege";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'reservation_spectacle_siege': " + String.valueOf(nbRows));


        Log.i("RPI", "Insertion dans la table 'carte_credit");
        insertQuery = DatabaseQueries.INSERT_CARTE_CREDIT_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from carte_credit";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'carte_credit': " + String.valueOf(nbRows));


        Log.i("RPI", "Insertion dans la table 'paiement");
        insertQuery = DatabaseQueries.INSERT_PAIEMENT_QUERIES[0];
        Log.i("RPI", "Query: " + insertQuery);
        database.execSQL(insertQuery);
        selectQuery = "select * from paiement";
        result = database.rawQuery(selectQuery, null);
        nbRows = result.getCount();
        Log.i("RPI", "Nombre de tuples insérés dans la table 'paiement': " + String.valueOf(nbRows));


       // printTable(CarteCredit.TABLE_NAME, database);
       // printTable(Section.TABLE_NAME, database);


    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    //************ FONCTIONS DES AUTRES SQL HELPER ************\\

    // ADRESSE
    public Adresse getAdresseById(int id) {


        Adresse adresse = null;
        database = this.getReadableDatabase();

        Cursor cursor = database.query(Adresse.TABLE_NAME,
                new String[]{   Adresse.COLUMN_ID, Adresse.COLUMN_NUMERO, Adresse.COLUMN_RUE,
                        Adresse.COLUMN_VILLE, Adresse.COLUMN_PROVINCE, Adresse.COLUMN_CODE_POSTAL,
                        Adresse.COLUMN_LONGITUDE, Adresse.COLUMN_LATITUDE },
                Adresse.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            adresse = new Adresse(
                    cursor.getInt(cursor.getColumnIndex(Adresse.COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(Adresse.COLUMN_NUMERO)),
                    cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_RUE)),
                    cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_VILLE)),
                    cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_PROVINCE)),
                    cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_CODE_POSTAL)),
                    cursor.getLong(cursor.getColumnIndex(Adresse.COLUMN_LONGITUDE)),
                    cursor.getLong(cursor.getColumnIndex(Adresse.COLUMN_LATITUDE)));

            cursor.close();

        }

        close();

        return adresse;
    }

    //************************************************************************************************
    //************************************************************************************************


    public List<Adresse> getAllAdresses() {
        List<Adresse> adresses = null;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Adresse.TABLE_NAME + " ORDER BY " +
                Adresse.COLUMN_ID + " ASC";

        database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            adresses = new ArrayList<>();

            do {
                Adresse adresse = new Adresse();
                adresse.setId(cursor.getInt(cursor.getColumnIndex(Adresse.COLUMN_ID)));
                adresse.setNumero(cursor.getInt(cursor.getColumnIndex(Adresse.COLUMN_NUMERO)));
                adresse.setRue(cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_RUE)));
                adresse.setVille(cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_VILLE)));
                adresse.setProvince(cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_PROVINCE)));
                adresse.setCodePostal(cursor.getString(cursor.getColumnIndex(Adresse.COLUMN_CODE_POSTAL)));
                adresse.setLongitude(cursor.getLong(cursor.getColumnIndex(Adresse.COLUMN_LONGITUDE)));
                adresse.setLatitude(cursor.getLong(cursor.getColumnIndex(Adresse.COLUMN_LATITUDE)));

                adresses.add(adresse);
            } while (cursor.moveToNext());

            cursor.close();
        }


        close();

        return adresses;
    }

    //************************************************************************************************
    //************************************************************************************************

    public int getAdressesCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Adresse.TABLE_NAME;

        Cursor cursor = database.rawQuery(countQuery, null);

        int count = 0;
        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        close();
        return count;
    }

    //************************************************************************************************
    //************************************************************************************************

    public long addAdresse(Adresse adresse) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Adresse.COLUMN_NUMERO, adresse.getNumero());
        values.put(Adresse.COLUMN_RUE, adresse.getRue());
        values.put(Adresse.COLUMN_VILLE, adresse.getVille());
        values.put(Adresse.COLUMN_PROVINCE, adresse.getProvince());
        values.put(Adresse.COLUMN_CODE_POSTAL, adresse.getCodePostal());
        if(adresse.getLongitude() > 0 && adresse.getLatitude() > 0) {
            values.put(Adresse.COLUMN_LONGITUDE, adresse.getLongitude());
            values.put(Adresse.COLUMN_LATITUDE, adresse.getLatitude());
        }


        long nbAffectedRows = database.insert(Adresse.TABLE_NAME, null, values);

        close();
        return nbAffectedRows;
    }

    //************************************************************************************************
    //************************************************************************************************


    public int updateAdresse(Adresse adresse) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Adresse.COLUMN_NUMERO, adresse.getNumero());
        values.put(Adresse.COLUMN_RUE, adresse.getRue());
        values.put(Adresse.COLUMN_VILLE, adresse.getVille());
        values.put(Adresse.COLUMN_PROVINCE, adresse.getProvince());
        values.put(Adresse.COLUMN_CODE_POSTAL, adresse.getCodePostal());

        if(adresse.getLongitude() > 0 && adresse.getLatitude() > 0) {
            values.put(Adresse.COLUMN_LONGITUDE, adresse.getLongitude());
            values.put(Adresse.COLUMN_LATITUDE, adresse.getLatitude());
        }


        int nbAffectedRows= database.update (Adresse.TABLE_NAME, values, Adresse.COLUMN_ID + " = ?",
                new String[]{String.valueOf(adresse.getId())});

        close();

        return nbAffectedRows;
    }

    //************************************************************************************************
    //************************************************************************************************

    public int deleteAdresse(Adresse adresse) {

        database= this.getWritableDatabase();

        int result = database.delete(Adresse.TABLE_NAME, Adresse.COLUMN_ID + " = ?",
                new String[]{String.valueOf(adresse.getId())});
        close();

        return result;
    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************


    // ARTISTE
    public Artiste getArtisteById(int id) {

        database = this.getReadableDatabase();

        Artiste artiste = null;
        Cursor cursor = database.query(Artiste.TABLE_NAME,
                new String[]{ Artiste.COLUMN_ID, Artiste.COLUMN_FIRSTNAME, Artiste.COLUMN_LASTNAME },
                Artiste.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            artiste = new Artiste(
                    cursor.getInt(cursor.getColumnIndex(Artiste.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_FIRSTNAME)),
                    cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_LASTNAME)));

            cursor.close();
        }

        close();

        return artiste;
    }

    //************************************************************************************************
    //************************************************************************************************

    public Artiste getArtisteByName(Artiste artiste) {

        database = this.getReadableDatabase();

        Artiste artiste2 = null;
        Cursor cursor = database.query(Artiste.TABLE_NAME,
                new String[]{ Artiste.COLUMN_ID, Artiste.COLUMN_FIRSTNAME, Artiste.COLUMN_LASTNAME },
                Artiste.COLUMN_FIRSTNAME + "=? and " + Artiste.COLUMN_LASTNAME + "=?",
                new String[]{artiste.getPrenom(), artiste.getNom()}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            artiste2 = new Artiste(
                    cursor.getInt(cursor.getColumnIndex(Artiste.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_FIRSTNAME)),
                    cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_LASTNAME)));

            cursor.close();

        }

        close();

        return artiste2;
    }

    //************************************************************************************************
    //************************************************************************************************

    public List<Artiste> getAllArtistes() {
        List<Artiste> artistes = null;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Artiste.TABLE_NAME + " ORDER BY " + Artiste.COLUMN_ID;
        // Artiste.COLUMN_LASTNAME + ", " + Artiste.COLUMN_FIRSTNAME + " ASC";

        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            artistes = new ArrayList<>();

            do {
                Artiste artiste = new Artiste();
                artiste.setId(cursor.getInt(cursor.getColumnIndex(Artiste.COLUMN_ID)));
                artiste.setPrenom(cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_FIRSTNAME)));
                artiste.setNom(cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_LASTNAME)));

                artistes.add(artiste);
            } while (cursor.moveToNext());

            cursor.close();
        }

        close();

        return artistes;
    }

    //************************************************************************************************
    //************************************************************************************************

    public List<Artiste> getAllArtistesBySpectacleId(int spectacleId) {

      //  Log.i("RPI", " >> In DBHelper: get artistes for spectacleId : " + spectacleId);

        List<Artiste> artistes = null;

        // Select All Query
//        String selectQuery = "SELECT  * FROM artiste " +
//                " inner join spectacle_artiste as SA on SA.id_artiste=artiste.id " +
//                " inner join spectacle on spectacle.id=SA.id_spectacle " +
//                "   where id_spectacle=?";


        String selectQuery = " select * from artiste " +
                " where id in " +
                " (select id_artiste from spectacle_artiste where id_spectacle=?)";

        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, new String[]{Integer.toString(spectacleId)});

     //   Log.i("RPI", " >> In DBHelper: cursor.count : " + cursor.getCount());

        if (cursor != null && cursor.moveToFirst()) {

            artistes = new ArrayList<>();

            do {
                Artiste artiste = new Artiste();
                artiste.setId(cursor.getInt(cursor.getColumnIndex(Artiste.COLUMN_ID)));
                artiste.setPrenom(cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_FIRSTNAME)));
                artiste.setNom(cursor.getString(cursor.getColumnIndex(Artiste.COLUMN_LASTNAME)));

                artistes.add(artiste);
            } while (cursor.moveToNext());

            cursor.close();
        }

        //******************************************************************************************
//        Log.i("RPI", " >> In DBHelper: get artistes size : " + artistes.size());
//
//        // Select All Query
//        selectQuery = "SELECT  * FROM spectacle_artiste ";
//
//        cursor = database.rawQuery(selectQuery, null);
//        if (cursor != null && cursor.moveToFirst()) {
//
//            List<Integer> liste  = new ArrayList<>();
//            int i = 0;
//            do {
//                liste.add(cursor.getInt(cursor.getColumnIndex("id_spectacle")));
//                liste.add(cursor.getInt(cursor.getColumnIndex("id_artiste")));
//                Log.i("RPI", " >> In DBHelper: spectacle_artiste i : " + i);
//                i++;
//
//            } while (cursor.moveToNext());
//
//            Log.i("RPI", " >> In DBHelper: spectacle_artiste liste.size : " + liste.size());
//            for (Integer integer: liste) {
//                Log.i("RPI", " >> In DBHelper: spectacle_artiste integer : " + integer);
//            }
//
//        }

        //******************************************************************************************

        close();

        return artistes;
    }

    //************************************************************************************************
    //************************************************************************************************

    public int getArtistesCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Artiste.TABLE_NAME;

        Cursor cursor = database.rawQuery(countQuery, null);

        int count = 0;

        if(cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        close();

        return count;
    }

    //************************************************************************************************
    //************************************************************************************************


    public int updateArtiste(Artiste artiste) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Artiste.COLUMN_FIRSTNAME, artiste.getPrenom());
        values.put(Artiste.COLUMN_LASTNAME, artiste.getNom());

        int nbAffectedRows= database.update (Artiste.TABLE_NAME, values, Artiste.COLUMN_ID + " = ?",
                new String[]{String.valueOf(artiste.getId())});

        close();

        return nbAffectedRows;
    }

    //************************************************************************************************
    //************************************************************************************************


    public long addArtiste(Artiste artiste) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Artiste.COLUMN_FIRSTNAME, artiste.getPrenom());
        values.put(Artiste.COLUMN_LASTNAME, artiste.getNom());

        long nbAffectedRows= database.insert(Artiste.TABLE_NAME, null, values);

        close();

        return nbAffectedRows;
    }



    //************************************************************************************************
    //************************************************************************************************


    public long replaceArtiste(Artiste artiste) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Artiste.COLUMN_FIRSTNAME, artiste.getPrenom());
        values.put(Artiste.COLUMN_LASTNAME, artiste.getNom());

        long nbAffectedRows = database.replace (Artiste.TABLE_NAME, null,  values);

        close();

        return nbAffectedRows;
    }
    //************************************************************************************************
    //************************************************************************************************

    public int deleteArtiste(Artiste artiste) {

        database= this.getWritableDatabase();
        int result = database.delete(Artiste.TABLE_NAME, Artiste.COLUMN_ID + " = ?",
                new String[]{String.valueOf(artiste.getId())});

        close();

        return result;
    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************


    // CARTES DE CREDIT
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

    //************************************************************************************************
    //************************************************************************************************


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

    //************************************************************************************************
    //************************************************************************************************


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

    //************************************************************************************************
    //************************************************************************************************


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

    //************************************************************************************************
    //************************************************************************************************


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

    //************************************************************************************************
    //************************************************************************************************

    public int deleteCarteCredit(CarteCredit carte) {

        database = this.getWritableDatabase();
        int result = database.delete(CarteCredit.TABLE_NAME, CarteCredit.COLUMN_ID + " = ?",
                new String[]{String.valueOf(carte.getId())});
        close();
        return result;
    }


    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************


    // Genre
    public Genre getGenreById(int id) {

        Genre genre = null;

        // get a readable database
        database= this.getReadableDatabase();

        Cursor cursor = database.query(Genre.TABLE_NAME,
                new String[]{ Genre.COLUMN_ID, Genre.COLUMN_NAME },
                Genre.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            genre = new Genre(
                    cursor.getInt(cursor.getColumnIndex(Genre.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Genre.COLUMN_NAME)));

            cursor.close();
        }

        close();

        return genre;


    }

    //************************************************************************************************
    //************************************************************************************************

    public List<Genre> getAllGenres() {

        List<Genre> genres = null;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Genre.TABLE_NAME + " ORDER BY " + Genre.COLUMN_NAME + " ASC";

        database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            genres = new ArrayList<>();

            do {
                Genre genre = new Genre();
                genre.setId(cursor.getInt(cursor.getColumnIndex(Genre.COLUMN_ID)));
                genre.setNom(cursor.getString(cursor.getColumnIndex(Genre.COLUMN_NAME)));
                genres.add(genre);
            } while (cursor.moveToNext());

            cursor.close();
        }

        close();

        return genres;
    }

    //************************************************************************************************
    //************************************************************************************************


    public int getGenresCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Genre.TABLE_NAME;
        Cursor cursor = database.rawQuery(countQuery, null);

        int count = 0;

        if(cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        close();

        return count;
    }

    //************************************************************************************************
    //************************************************************************************************


    public long addGenre(Genre genre) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Genre.COLUMN_NAME, genre.getNom());

        long nbAffectedRows= database.insert (Genre.TABLE_NAME, null, values);


        close();

        return nbAffectedRows;
    }

    //************************************************************************************************
    //************************************************************************************************


    public int updateGenre(Genre genre) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Genre.COLUMN_NAME, genre.getNom());

        int nbAffectedRows= database.update (Genre.TABLE_NAME, values, Genre.COLUMN_ID + " = ?",
                new String[]{String.valueOf(genre.getId())});

        close();

        return nbAffectedRows;
    }

    //************************************************************************************************
    //************************************************************************************************


    public int deleteGenre(Genre genre) {

        database = this.getWritableDatabase();

        int result = database.delete(Genre.TABLE_NAME, Genre.COLUMN_ID + " = ?",
                new String[]{String.valueOf(genre.getId())});

        close();

        return result;
    }


    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    // Paiement

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

    //************************************************************************************************
    //************************************************************************************************

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

    //************************************************************************************************
    //************************************************************************************************

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

    //************************************************************************************************
    //************************************************************************************************

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

    //************************************************************************************************
    //************************************************************************************************

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

    //************************************************************************************************
    //************************************************************************************************

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

    //************************************************************************************************
    //************************************************************************************************

    public int deletePaiement(Paiement paiement) {
        database= this.getWritableDatabase();
        int result = database.delete(Paiement.TABLE_NAME, Paiement.COLUMN_ID + " = ?",
                new String[]{String.valueOf(paiement.getId())});

        close();

        return result;
    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    // Reservation
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

    //************************************************************************************************
    //************************************************************************************************

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

    //************************************************************************************************
    //************************************************************************************************


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

    //************************************************************************************************
    //************************************************************************************************

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

    //************************************************************************************************
    //************************************************************************************************

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

    //************************************************************************************************
    //************************************************************************************************

    public long replaceReservation(Reservation Reservation) {
        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Reservation.COLUMN_NUMERO_CONFIRMATION, Reservation.getNumeroConfirmation());
        values.put(Reservation.COLUMN_DATE_RESERVATION, Reservation.getDateReservation());
        values.put(Reservation.COLUMN_USER_ID, Reservation.getUserId());

        long nbAffectedRows= database.replace (Reservation.TABLE_NAME, null,  values);

        close();

        return nbAffectedRows;
    }

    //************************************************************************************************
    //************************************************************************************************

    public int deleteReservation(Reservation reservation) {
        //TODO: delete entry from reservation_siege table before

        database = this.getWritableDatabase();
        int result = database.delete(Reservation.TABLE_NAME, Reservation.COLUMN_ID + " = ?",
                new String[]{String.valueOf(reservation.getId())});

        close();

        return result;
    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************



    // Salle
    public Salle getSalleById(int id) {

        database = this.getReadableDatabase();

        Salle salle = null;

        Cursor cursor = database.query(   Salle.TABLE_NAME,
                new String[]{       Salle.COLUMN_ID, Salle.COLUMN_NAME, Salle.COLUMN_TELEPHONE, Salle.COLUMN_FAX,
                        Salle.COLUMN_COURRIEL, Salle.COLUMN_DESCRIPTION, Salle.COLUMN_ADRESSE_ID},
                Salle.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            salle = new Salle(
                    cursor.getInt(cursor.getColumnIndex(Salle.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Salle.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(Salle.COLUMN_TELEPHONE)),
                    cursor.getString(cursor.getColumnIndex(Salle.COLUMN_FAX)),
                    cursor.getString(cursor.getColumnIndex(Salle.COLUMN_COURRIEL)),
                    cursor.getInt(cursor.getColumnIndex(Salle.COLUMN_ADRESSE_ID)),
                    cursor.getString(cursor.getColumnIndex(Salle.COLUMN_DESCRIPTION)));


            cursor.close();
        }

        close();

        return salle;

    }

    //************************************************************************************************
    //************************************************************************************************


    public List<Salle> getAllSalles() {

        database = this.getReadableDatabase();

        List<Salle> salles = null;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Salle.TABLE_NAME + " ORDER BY " +
                Salle.COLUMN_ID +  " ASC";

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            salles = new ArrayList<>();

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

            cursor.close();
        }

        close();

        return salles;
    }

    //************************************************************************************************
    //************************************************************************************************

    public int getSallesCount() {

        database= this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Salle.TABLE_NAME;

        Cursor cursor = database.rawQuery(countQuery, null);

        int count = 0;

        if(cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        close();

        return count;
    }

    //************************************************************************************************
    //************************************************************************************************

    public long addSalle(Salle salle) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Salle.COLUMN_NAME, salle.getNom());
        values.put(Salle.COLUMN_TELEPHONE, salle.getTelephone());
        values.put(Salle.COLUMN_FAX, salle.getFax());
        values.put(Salle.COLUMN_COURRIEL, salle.getCourriel());
        values.put(Salle.COLUMN_DESCRIPTION, salle.getDescription());
        values.put(Salle.COLUMN_ADRESSE_ID, salle.getAdresseId());

        long nbAffectedRows= database.insert(Salle.TABLE_NAME, null, values);

        close();

        return nbAffectedRows;
    }

    //************************************************************************************************
    //************************************************************************************************

    public int updateSalle(Salle salle) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Salle.COLUMN_NAME, salle.getNom());
        values.put(Salle.COLUMN_TELEPHONE, salle.getTelephone());
        values.put(Salle.COLUMN_FAX, salle.getFax());
        values.put(Salle.COLUMN_COURRIEL, salle.getCourriel());
        values.put(Salle.COLUMN_DESCRIPTION, salle.getDescription());
        values.put(Salle.COLUMN_ADRESSE_ID, salle.getAdresseId());

        int nbAffectedRows= database.update (Salle.TABLE_NAME, values, Salle.COLUMN_ID + " = ?",
                new String[]{String.valueOf(salle.getId())});

        close();

        return nbAffectedRows;
    }

    //************************************************************************************************
    //************************************************************************************************

    public int deleteSalle(Salle salle) {
        database = this.getWritableDatabase();
        int result = database.delete(Salle.TABLE_NAME, Salle.COLUMN_ID + " = ?",
                new String[]{String.valueOf(salle.getId())});

        close();

        return result;
    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    // Section

    public Section getSectionById(int id) {


        database = this.getReadableDatabase();

        Section section = null;

        Cursor cursor = database.query(Section.TABLE_NAME,
                new String[]{   Section.COLUMN_ID, Section.COLUMN_NAME, Section.COLUMN_CATEGORIE,
                        Section.COLUMN_NB_SIEGES, Section.COLUMN_SALLE_ID },
                Section.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            section = new Section(
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Section.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_CATEGORIE)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_NB_SIEGES)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_SALLE_ID)));

            cursor.close();
        }

        return section;

    }

    //************************************************************************************************
    //************************************************************************************************

    public Section getSectionBySalleId(int idSalle) {

        database = this.getReadableDatabase();

        Section section = null;

        Cursor cursor = database.query(Section.TABLE_NAME,
                new String[]{   Section.COLUMN_ID, Section.COLUMN_NAME, Section.COLUMN_CATEGORIE,
                        Section.COLUMN_NB_SIEGES, Section.COLUMN_SALLE_ID },
                Section.COLUMN_SALLE_ID + "=?",
                new String[]{String.valueOf(idSalle)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            section = new Section(
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Section.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_CATEGORIE)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_NB_SIEGES)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_SALLE_ID)));

            cursor.close();
        }

        close();

        return section;

    }

    //************************************************************************************************
    //************************************************************************************************

    public List<Section> getSectionsBySalleId(int salleId) {

        database = this.getReadableDatabase();

        List<Section> sections = null;

        String selectQuery = "SELECT  * FROM " + Section.TABLE_NAME +
                " where "+ Section.COLUMN_SALLE_ID +"=" + salleId;


        Cursor cursor = database.rawQuery(selectQuery, null);

        Log.i("RPI", "Section count: " + cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {

            sections = new ArrayList<>();

            do {


                Section section = new Section();
                section.setId(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_ID)));
                section.setName(cursor.getString(cursor.getColumnIndex(Section.COLUMN_NAME)));
                section.setCategorie(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_CATEGORIE)));
                section.setNbSieges(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_NB_SIEGES)));
                section.setSalleId(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_SALLE_ID)));

                sections.add(section);
            } while (cursor.moveToNext());

            cursor.close();
        }


        close();

        return sections;
    }

    //************************************************************************************************
    //************************************************************************************************

    public List<Section> getAllSections() {

        database = this.getReadableDatabase();

        List<Section> sections = null;

        String selectQuery = "SELECT  * FROM " + Section.TABLE_NAME + " ORDER BY " +
                Section.COLUMN_ID + " ASC";

        Cursor cursor = database.rawQuery(selectQuery, null);

        Log.i("RPI", "Section count: " + cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {

            sections = new ArrayList<>();

            do {


                Section section = new Section();
                section.setId(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_ID)));
                section.setName(cursor.getString(cursor.getColumnIndex(Section.COLUMN_NAME)));
                section.setCategorie(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_CATEGORIE)));
                section.setNbSieges(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_NB_SIEGES)));
                section.setSalleId(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_SALLE_ID)));

                sections.add(section);
            } while (cursor.moveToNext());

            cursor.close();
        }


        close();

        return sections;
    }

    //************************************************************************************************
    //************************************************************************************************

    public int getSectionsCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Section.TABLE_NAME;

        Cursor cursor = database.rawQuery(countQuery, null);

        int count = 0;

        if(cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        close();

        return count;
    }

    //************************************************************************************************
    //************************************************************************************************

    public long addSection(Section section) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Section.COLUMN_NAME, section.getName());
        values.put(Section.COLUMN_CATEGORIE, section.getCategorie());
        values.put(Section.COLUMN_NB_SIEGES, section.getNbSieges());
        values.put(Section.COLUMN_SALLE_ID, section.getSalleId());

        long nbAffectedRows= database.insert (Section.TABLE_NAME, null, values);

        close();

        return nbAffectedRows;
    }

    //************************************************************************************************
    //************************************************************************************************

    public int updateSection(Section section) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Section.COLUMN_NAME, section.getName());
        values.put(Section.COLUMN_CATEGORIE, section.getCategorie());
        values.put(Section.COLUMN_NB_SIEGES, section.getNbSieges());
        values.put(Section.COLUMN_SALLE_ID, section.getSalleId());

        int nbAffectedRows= database.update (Section.TABLE_NAME, values, Section.COLUMN_ID + " = ?",
                new String[]{String.valueOf(section.getId())});

        close();

        return nbAffectedRows;
    }

    //************************************************************************************************
    //************************************************************************************************

    public int deleteSection(Section section) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Section.TABLE_NAME, Section.COLUMN_ID + " = ?",
                new String[]{String.valueOf(section.getId())});


        close();

        return result;
    }

    //************************************************************************************************
    //************************************************************************************************

    public List<Integer> getNbFreePlacesBySections(int spectacleId) {

        database = this.getReadableDatabase();

        List<Integer> freeSieges = null;

        // String selectQuery = "SELECT  * FROM " + SpectacleSection.TABLE_NAME +
        //        " where "+ SpectacleSection.COLUMN_SPECTACLE_ID +"=" + spectacleId;
        String selectQuery =
                "select count(*) as nb_libres" +

                        " from siege " +
                        " inner join spectacle_siege as SPSI on SPSI.id_siege=siege.id " +
                        " inner join section on section.id=siege.id_section " +
                        " where SPSI.id_spectacle=? and SPSI.reserve=0 " +
                        " group by section.id " +
                        " order by section.id asc "
                ;

        ;

        Cursor cursor = database.rawQuery(selectQuery, new String[]{Integer.toString(spectacleId)});

        Log.i("RPI", "Section count: " + cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {

            freeSieges= new ArrayList<>();

            do {


                int nbSieges = cursor.getInt(cursor.getColumnIndex("nb_libres"));
                freeSieges.add(nbSieges);
                Log.i("RPI", "nbSieges: " + nbSieges);
            } while (cursor.moveToNext());

            cursor.close();
        }


        close();

        return freeSieges;
    }


    //************************************************************************************************
    //************************************************************************************************

    public List<Siege> getFreeSiegeBySections(int spectacleId, int sectionId, int limit) {

        database = this.getReadableDatabase();

        List<Siege> sieges = null;


        String selectQuery =
                " select siege.* from siege " +
                "   inner join spectacle_siege as SSI on SSI.id_siege = siege.id " +
                "   inner join section on section.id = siege.id_section " +
                "   where siege.id_section=? " +
                "   and SSI.reserve= 0 " +
                "   and id_spectacle=? " +
                "     limit ? "


        ;

        Cursor cursor = database.rawQuery(selectQuery,
                        new String[]{Integer.toString(sectionId),
                                    Integer.toString(spectacleId),
                                    Integer.toString(limit)});

      //  Log.i("RPI", "Section count: " + cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {

            sieges= new ArrayList<>();

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

        Log.i("RPI", "nbSieges: "  + sieges.size());
        close();

        return sieges;
    }


    //************************************************************************************************
    //************************************************************************************************

    public List<SpectacleSection> getSectionsBySpectacleId(int spectacleId) {

        database = this.getReadableDatabase();

        List<SpectacleSection> spectacleSections = null;

        String selectQuery = "SELECT  * FROM " + SpectacleSection.TABLE_NAME +
                " where "+ SpectacleSection.COLUMN_SPECTACLE_ID +"=" + spectacleId;


        Cursor cursor = database.rawQuery(selectQuery, null);

        Log.i("RPI", "Section count: " + cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {

            spectacleSections = new ArrayList<>();

            do {


                SpectacleSection spectacleSection = new SpectacleSection();
                spectacleSection.setSpectacleId(cursor.getInt(cursor.getColumnIndex(SpectacleSection.COLUMN_SPECTACLE_ID)));
                spectacleSection.setSectionid(cursor.getInt(cursor.getColumnIndex(SpectacleSection.COLUMN_SECTION_ID)));
                spectacleSection.setPrice(cursor.getFloat(cursor.getColumnIndex(SpectacleSection.COLUMN_PRICE)));


                spectacleSections.add(spectacleSection);
            } while (cursor.moveToNext());

            cursor.close();
        }


        close();

        return spectacleSections;
    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    // Siege

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

    //************************************************************************************************
    //************************************************************************************************


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

    //************************************************************************************************
    //************************************************************************************************

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

    //************************************************************************************************
    //************************************************************************************************

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


    //************************************************************************************************
    //************************************************************************************************

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



    //************************************************************************************************
    //************************************************************************************************

    public int updateSpectacleSiege(int spectacleId, int siegeId, int reserve) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id_spectacle", spectacleId);
        values.put("id_siege", siegeId);
        values.put("reserve", reserve);

        String updareQuery = " update spectacle_siege set reserve=? where id_spectacle=? and id_siege=?";

        int nbAffectedRows= database.update ("spectacle_siege", values,
                "id_spectacle = ? and id_siege = ? ",
                new String[]{String.valueOf(spectacleId), String.valueOf(siegeId)});

        close();

        return nbAffectedRows;
    }



    //************************************************************************************************
    //************************************************************************************************

    public int deleteSiege(Siege siege) {

        database = this.getWritableDatabase();
        int result = database.delete(Siege.TABLE_NAME, Siege.COLUMN_ID + " = ?",
                new String[]{String.valueOf(siege.getId())});

        close();

        return result;
    }


    //************************************************************************************************
    //************************************************************************************************

    public List<Siege> getAllSiegesBySalle(int salleId) {

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



    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    // SPECTACLE

    public Spectacle getSpectacleById(int id) {


        database = this.getReadableDatabase();

        Spectacle spectacle = null;

        Cursor cursor = database.query(Spectacle.TABLE_NAME,
                new String[]{   Spectacle.COLUMN_ID, Spectacle.COLUMN_TITRE, Spectacle.COLUMN_DATE_SPECTACLE,
                        Spectacle.COLUMN_GENRE_ID, Spectacle.COLUMN_SALLE_ID, Spectacle.COLUMN_DUREE, Spectacle.COLUMN_DESCRIPTION },
                Spectacle.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            spectacle = new Spectacle(
                    cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_TITRE)),
                    cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_DATE_SPECTACLE)),
                    cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_DUREE)),
                    cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_GENRE_ID)),
                    cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_SALLE_ID)),
                    cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_DESCRIPTION)));

            cursor.close();
        }

        return spectacle;

    }

    //************************************************************************************************
    //************************************************************************************************

    public List<Spectacle> getAllSpectacles() {

        database = this.getReadableDatabase();

        List<Spectacle> spectacles = null;

        String selectQuery = "SELECT  * FROM " + Spectacle.TABLE_NAME + " ORDER BY " +
                Spectacle.COLUMN_DATE_SPECTACLE + " ASC";

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            spectacles = new ArrayList<>();

            do {

                Spectacle spectacle = new Spectacle();
                spectacle.setId(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_ID)));
                spectacle.setTitre(cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_TITRE)));
                spectacle.setDate(cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_DATE_SPECTACLE)));
                spectacle.setDuree(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_DUREE)));
                spectacle.setGenreId(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_GENRE_ID)));
                spectacle.setSalleId(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_SALLE_ID)));
                spectacle.setDescription(cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_DESCRIPTION)));


                spectacles.add(spectacle);

            } while (cursor.moveToNext());

            cursor.close();
        }

        close();

        return spectacles;
    }

    //************************************************************************************************
    //************************************************************************************************

    public List<Spectacle> getSpectaclesByDate(String currentDate) {

        database = this.getReadableDatabase();

        List<Spectacle> spectacles = null;

        String selectQuery = "SELECT  * FROM " + Spectacle.TABLE_NAME +
                " where date_spectacle>=? " +
                " ORDER BY " + Spectacle.COLUMN_DATE_SPECTACLE + " ASC";

        Cursor cursor = database.rawQuery(selectQuery,
                new String[]{currentDate});
        if (cursor != null && cursor.moveToFirst()) {

            spectacles = new ArrayList<>();

            do {

                Spectacle spectacle = new Spectacle();
                spectacle.setId(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_ID)));
                spectacle.setTitre(cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_TITRE)));
                spectacle.setDate(cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_DATE_SPECTACLE)));
                spectacle.setDuree(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_DUREE)));
                spectacle.setGenreId(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_GENRE_ID)));
                spectacle.setSalleId(cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_SALLE_ID)));
                spectacle.setDescription(cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_DESCRIPTION)));

                spectacles.add(spectacle);

            } while (cursor.moveToNext());

            cursor.close();
        }


        close();


        return spectacles;
    }

    //************************************************************************************************
    //************************************************************************************************

    public int getSpectaclesCount() {

        database = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Spectacle.TABLE_NAME;

        Cursor cursor = database.rawQuery(countQuery, null);

        int count = 0;

        if(cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        close();

        return count;
    }

    //************************************************************************************************
    //************************************************************************************************

    public long addSpectacle(Spectacle spectacle) {


        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Spectacle.COLUMN_TITRE, spectacle.getTitre());
        values.put(Spectacle.COLUMN_DATE_SPECTACLE, spectacle.getDate());
        values.put(Spectacle.COLUMN_GENRE_ID, spectacle.getGenreId());
        values.put(Spectacle.COLUMN_SALLE_ID, spectacle.getSalleId());
        values.put(Spectacle.COLUMN_DESCRIPTION, spectacle.getDescription());



        long result= database.insert(Spectacle.TABLE_NAME, null, values);

        close();

        return result;
    }




    //************************************************************************************************
    //************************************************************************************************

    public long addSpectacleArtistes(Spectacle spectacle, List<Artiste> artistes) {




        database= this.getWritableDatabase();
        long result = -1;


        for (Artiste artiste: artistes) {
            ContentValues values = new ContentValues();
            values.put(SpectacleArtiste.COLUMN_SPECTACLE_ID, spectacle.getId());
            values.put(SpectacleArtiste.COLUMN_ARTISTE_ID, artiste.getId());
            result= database.insert(SpectacleArtiste.TABLE_NAME, null, values);
            Log.i("RPI", "result: "  + result);
        }


        close();

        return result;
    }

    //************************************************************************************************
    //************************************************************************************************

    public int updateSpectacle(Spectacle spectacle) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Spectacle.COLUMN_TITRE, spectacle.getTitre());
        values.put(Spectacle.COLUMN_DATE_SPECTACLE, spectacle.getDate());
        values.put(Spectacle.COLUMN_GENRE_ID, spectacle.getGenreId());
        values.put(Spectacle.COLUMN_DUREE, spectacle.getDuree());
        values.put(Spectacle.COLUMN_SALLE_ID, spectacle.getSalleId());
        values.put(Spectacle.COLUMN_DESCRIPTION, spectacle.getDescription());

        int result= database.update (Spectacle.TABLE_NAME, values, Spectacle.COLUMN_ID + " = ?",
                new String[]{String.valueOf(spectacle.getId())});

        close();

        return result;
    }
    //************************************************************************************************
    //************************************************************************************************

    public long replaceSpectacle(Spectacle spectacle) {

      //  Log.i("RPI", "Ajout d'un spectacle: " + spectacle.getId());
        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(spectacle.getId() > 0){

            values.put(Spectacle.COLUMN_ID, spectacle.getId());
        }
        values.put(Spectacle.COLUMN_TITRE, spectacle.getTitre());
        values.put(Spectacle.COLUMN_DATE_SPECTACLE, spectacle.getDate());
        values.put(Spectacle.COLUMN_GENRE_ID, spectacle.getGenreId());
        values.put(Spectacle.COLUMN_DUREE, spectacle.getDuree());
        values.put(Spectacle.COLUMN_SALLE_ID, spectacle.getSalleId());
        values.put(Spectacle.COLUMN_DESCRIPTION, spectacle.getDescription());


        long result= database.replace(Spectacle.TABLE_NAME, null, values);

        close();

        return result;
    }

    //************************************************************************************************
    //************************************************************************************************

    //************************************************************************************************
    //************************************************************************************************

    public int  deleteSpectacle(Spectacle spectacle) {

        database = this.getWritableDatabase();

        int result = database.delete(Spectacle.TABLE_NAME, Spectacle.COLUMN_ID + " = ?",
                new String[]{String.valueOf(spectacle.getId())});

        close();

        return result;
    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    // UTILISATEUR
    public Utilisateur getUtilisateurById(int id) {

        Utilisateur utilisateur = null;
        database = this.getReadableDatabase();

        Cursor cursor = database.query(   Utilisateur.TABLE_NAME,
                new String[]{       Utilisateur.COLUMN_ID, Utilisateur.COLUMN_PRENOM, Utilisateur.COLUMN_NOM,
                        Utilisateur.COLUMN_LOGIN, Utilisateur.COLUMN_MOT_PASSE, Utilisateur.COLUMN_COURRIEL,
                        Utilisateur.COLUMN_TELEPHONE, Utilisateur.COLUMN_ADRESSE_ID},
                Utilisateur.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            utilisateur = new Utilisateur(
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_NOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_MOT_PASSE)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_COURRIEL)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_TELEPHONE)),
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ADRESSE_ID)));

            cursor.close();

        }

        close();

        return utilisateur;
    }

    //************************************************************************************************
    //************************************************************************************************

    public Utilisateur getUtilisateurByName(String firstname, String lastnname) {

        Utilisateur utilisateur2 = null;
        database = this.getReadableDatabase();


        Cursor cursor = database.query(   Utilisateur.TABLE_NAME,
                new String[]{       Utilisateur.COLUMN_ID, Utilisateur.COLUMN_PRENOM, Utilisateur.COLUMN_NOM,
                        Utilisateur.COLUMN_LOGIN, Utilisateur.COLUMN_MOT_PASSE, Utilisateur.COLUMN_COURRIEL,
                        Utilisateur.COLUMN_TELEPHONE, Utilisateur.COLUMN_ADRESSE_ID},
                Utilisateur.COLUMN_PRENOM + "=? and " + Utilisateur.COLUMN_NOM + "=?",
                new String[]{firstname, lastnname}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            utilisateur2 = new Utilisateur(
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_NOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_MOT_PASSE)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_COURRIEL)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_TELEPHONE)),
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ADRESSE_ID)));

            cursor.close();
        }

        close();

        return utilisateur2;
    }

    //************************************************************************************************
    //************************************************************************************************

    public Utilisateur getUtilisateurByName(Utilisateur utilisateur) {

        Utilisateur utilisateur2 = null;
        database = this.getReadableDatabase();


        Cursor cursor = database.query(   Utilisateur.TABLE_NAME,
                new String[]{       Utilisateur.COLUMN_ID, Utilisateur.COLUMN_PRENOM, Utilisateur.COLUMN_NOM,
                        Utilisateur.COLUMN_LOGIN, Utilisateur.COLUMN_MOT_PASSE, Utilisateur.COLUMN_COURRIEL,
                        Utilisateur.COLUMN_TELEPHONE, Utilisateur.COLUMN_ADRESSE_ID},
                Utilisateur.COLUMN_PRENOM + "=? and " + Utilisateur.COLUMN_NOM + "=?",
                new String[]{utilisateur.getPrenom(), utilisateur.getNom()}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            utilisateur2 = new Utilisateur(
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_NOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_MOT_PASSE)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_COURRIEL)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_TELEPHONE)),
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ADRESSE_ID)));

            cursor.close();
        }

        close();

        return utilisateur2;
    }

    //************************************************************************************************
    //************************************************************************************************

    public Utilisateur getUtilisateurByLogin(String login) {

        Utilisateur utilisateur2 = null;
        database= this.getReadableDatabase();

        Cursor cursor = database.query(   Utilisateur.TABLE_NAME,
                new String[]{ Utilisateur.COLUMN_ID, Utilisateur.COLUMN_PRENOM, Utilisateur.COLUMN_NOM,
                        Utilisateur.COLUMN_LOGIN, Utilisateur.COLUMN_MOT_PASSE, Utilisateur.COLUMN_COURRIEL,
                        Utilisateur.COLUMN_TELEPHONE, Utilisateur.COLUMN_ADRESSE_ID},
                Utilisateur.COLUMN_LOGIN + "=? or " + Utilisateur.COLUMN_COURRIEL + "=?",
                new String[]{login, login}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            utilisateur2 = new Utilisateur(
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_NOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_MOT_PASSE)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_COURRIEL)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_TELEPHONE)),
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ADRESSE_ID)));

            cursor.close();
        }

        close();

        return utilisateur2;

    }

    //************************************************************************************************
    //************************************************************************************************

    public Utilisateur getUtilisateurByLogin(Utilisateur utilisateur) {

        Utilisateur utilisateur2 = null;
        database = this.getReadableDatabase();

        Cursor cursor = database.query(   Utilisateur.TABLE_NAME,
                new String[]{ Utilisateur.COLUMN_ID, Utilisateur.COLUMN_PRENOM, Utilisateur.COLUMN_NOM,
                        Utilisateur.COLUMN_LOGIN, Utilisateur.COLUMN_MOT_PASSE, Utilisateur.COLUMN_COURRIEL,
                        Utilisateur.COLUMN_TELEPHONE, Utilisateur.COLUMN_ADRESSE_ID},
                Utilisateur.COLUMN_LOGIN + "=? or " + Utilisateur.COLUMN_COURRIEL + "=?",
                new String[]{utilisateur.getLogin(), utilisateur.getCourriel()}, null, null, null, null);


        if (cursor != null && cursor.moveToFirst()) {

            utilisateur2 = new Utilisateur(
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_NOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_MOT_PASSE)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_COURRIEL)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_TELEPHONE)),
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ADRESSE_ID)));

            cursor.close();
        }

        close();

        return utilisateur2;

    }

    //************************************************************************************************
    //************************************************************************************************

    public Utilisateur validateLogin(Utilisateur utilisateur) {

        Utilisateur utilisateur2 = null;
        database = this.getReadableDatabase();

        Cursor cursor = database.query(   Utilisateur.TABLE_NAME,
                new String[]{ Utilisateur.COLUMN_ID, Utilisateur.COLUMN_PRENOM, Utilisateur.COLUMN_NOM,
                        Utilisateur.COLUMN_LOGIN, Utilisateur.COLUMN_MOT_PASSE, Utilisateur.COLUMN_COURRIEL,
                        Utilisateur.COLUMN_TELEPHONE, Utilisateur.COLUMN_ADRESSE_ID},
                Utilisateur.COLUMN_LOGIN + "=? or " + Utilisateur.COLUMN_COURRIEL + "=? and " + Utilisateur.COLUMN_MOT_PASSE + "=?",
                new String[]{utilisateur.getLogin(), utilisateur.getCourriel(), utilisateur.getMotPasse()}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            utilisateur2 = new Utilisateur(
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_NOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_MOT_PASSE)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_COURRIEL)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_TELEPHONE)),
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ADRESSE_ID)));

            cursor.close();
        }

        close();

        return utilisateur2;

    }

    //************************************************************************************************
    //************************************************************************************************

    public Utilisateur validateLogin(String login, String motPasse) {


        Log.i("RPI", "login: " + login);
        Log.i("RPI", "motPasse: " + motPasse);

        Log.i("RPI", "equal 1: " + "ArnaudFournier@dayrep.com".equals(login));
        Log.i("RPI", "equal 2: " + "weWoh9zie".equals(motPasse));


        Utilisateur utilisateur2 = null;

        database = this.getReadableDatabase();

        Cursor cursor = database.query( Utilisateur.TABLE_NAME,
                new String[]{ Utilisateur.COLUMN_ID, Utilisateur.COLUMN_PRENOM, Utilisateur.COLUMN_NOM,
                        Utilisateur.COLUMN_LOGIN, Utilisateur.COLUMN_MOT_PASSE, Utilisateur.COLUMN_COURRIEL,
                        Utilisateur.COLUMN_TELEPHONE, Utilisateur.COLUMN_ADRESSE_ID},
                Utilisateur.COLUMN_COURRIEL + "=? and " + Utilisateur.COLUMN_MOT_PASSE + "=?",
                new String[]{login, motPasse}, null, null, null, null);

        Log.i("RPI", "cursor count: " + cursor.getCount());

        if (cursor != null && cursor.moveToFirst()) {


            utilisateur2 = new Utilisateur(
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_NOM)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_MOT_PASSE)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_COURRIEL)),
                    cursor.getString(cursor.getColumnIndex(Utilisateur.COLUMN_TELEPHONE)),
                    cursor.getInt(cursor.getColumnIndex(Utilisateur.COLUMN_ADRESSE_ID)));

            cursor.close();
        }

        Log.i("RPI", "cursor: " + cursor + "; database: " + database);
        close();

        return utilisateur2;

    }

    //************************************************************************************************
    //************************************************************************************************

    public List<Utilisateur> getAllUtilisateurs() {

        List<Utilisateur> utilisateurs = null;
        String selectQuery = "SELECT  * FROM " + Utilisateur.TABLE_NAME + " ORDER BY " +
                Utilisateur.COLUMN_NOM + ", " + Utilisateur.COLUMN_PRENOM + " DESC";

        database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {

            utilisateurs = new ArrayList<>();
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

            cursor.close();
        }

        close();

        return utilisateurs;

    }

    //************************************************************************************************
    //************************************************************************************************

    public int getUtilisateursCount() {

        database= this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + Utilisateur.TABLE_NAME;

        Cursor cursor = database.rawQuery(countQuery, null);
        int count = 0;
        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        close();

        return count;

    }

    //************************************************************************************************
    //************************************************************************************************

    public long addUtilisateur(Utilisateur utilisateur) {

        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilisateur.COLUMN_PRENOM, utilisateur.getPrenom());
        values.put(Utilisateur.COLUMN_NOM, utilisateur.getNom());
        values.put(Utilisateur.COLUMN_LOGIN, utilisateur.getLogin());
        values.put(Utilisateur.COLUMN_MOT_PASSE, utilisateur.getMotPasse());
        values.put(Utilisateur.COLUMN_COURRIEL, utilisateur.getCourriel());
        values.put(Utilisateur.COLUMN_TELEPHONE, utilisateur.getTelephone());

        if(utilisateur.getAdresseId() > 0){
            values.put(Utilisateur.COLUMN_ADRESSE_ID, utilisateur.getAdresseId());
        }

        long nbAffectedRows= database.insert(Utilisateur.TABLE_NAME, null, values);

        close();

        return nbAffectedRows;

    }

    //************************************************************************************************
    //************************************************************************************************

    public int updateUtilisateur(Utilisateur utilisateur) {

        database= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilisateur.COLUMN_PRENOM, utilisateur.getPrenom());
        values.put(Utilisateur.COLUMN_NOM, utilisateur.getNom());
        values.put(Utilisateur.COLUMN_LOGIN, utilisateur.getLogin());
        values.put(Utilisateur.COLUMN_MOT_PASSE, utilisateur.getMotPasse());
        values.put(Utilisateur.COLUMN_COURRIEL, utilisateur.getCourriel());
        values.put(Utilisateur.COLUMN_TELEPHONE, utilisateur.getTelephone());
        values.put(Utilisateur.COLUMN_ADRESSE_ID, utilisateur.getAdresseId());

        int nbAffectedRows = database.update(Utilisateur.TABLE_NAME, values, Utilisateur.COLUMN_ID + " = ?",
                new String[]{String.valueOf(utilisateur.getId())});

        close();

        return nbAffectedRows;

    }

    //************************************************************************************************
    //************************************************************************************************

    public int deleteUtilisateur(Utilisateur utilisateur) {

        database = this.getWritableDatabase();

        int result = database.delete(Utilisateur.TABLE_NAME, Utilisateur.COLUMN_ID + " = ?",
                new String[]{String.valueOf(utilisateur.getId())});

        close();

        return result;
    }

}


//************************************************************************************************
//************************************************************************************************
//************************************************************************************************
//************************************************************************************************

