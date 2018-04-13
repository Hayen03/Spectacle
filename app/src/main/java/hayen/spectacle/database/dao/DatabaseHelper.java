//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.io.File;


public class DatabaseHelper extends SQLiteOpenHelper{



    private SQLiteDatabase database;

    private static volatile DatabaseHelper databaseHelper;

    private DatabaseHelper(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        File file = context.getDatabasePath(Constant.DATABASE_NAME);

        File path =   file.getAbsoluteFile();

        Log.i("RPI", "file: " + file  + "; db path: " + path);
    }

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        File file = context.getDatabasePath(name);

        File path =   file.getAbsoluteFile();

        Log.i("RPI", "file: " + file  + "; db path: " + path);
    }


    //*******************************************************************************************************
    //*******************************************************************************************************


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

        database =  sqLiteDatabase;

        //delete tables
        dropDB();
        //create tables
        createDB();
        //load tables
        loadDB();


    }


    //*******************************************************************************************************
    //*******************************************************************************************************

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        Log.i("RPI", "Retrait des tables");
        for (int j = 0; j < DatabaseQueries.DROP_TABLES_QUERIES.length; j++) {

            String querie =  DatabaseQueries.DROP_TABLES_QUERIES[j];
            database.execSQL(querie);

            Log.i("RPI", "Retrait de la table: " + querie);
        }

        onCreate(sqLiteDatabase);

    }

    //*******************************************************************************************************
    //*******************************************************************************************************

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
            database.execSQL(querie);
            Log.i("RPI", "Retrait de la table: " + querie);
        }


    }

    //*******************************************************************************************************
    //*******************************************************************************************************

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

    //*******************************************************************************************************
    //*******************************************************************************************************

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



}
