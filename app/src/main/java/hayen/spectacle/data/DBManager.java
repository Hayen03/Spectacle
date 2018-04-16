//package com.ift2905.reservation.database;
package hayen.spectacle.data;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import hayen.spectacle.data.dao.Constant;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.dao.UtilisateurSQLHelper;
import hayen.spectacle.data.data.Utilisateur;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;


/**
 * Created by daristote on 18-03-15.
 */

public class DBManager  {


    private static volatile DBManager dbManager;
    private ProgressDialog progressDialog;

    //databse helper
    private SQLiteOpenHelper dbHelper;
  //  private ArtisteSQLHelper artistSQLHelper;


    private Context context;

    private SQLiteDatabase database;

    private static final String dbName = "ReservationDB.db";
    private static final int DB_VERSION = 1;

    private DBManager(){
        context = null;
    }

    private DBManager(Context context) {
        this.context = context;
    }


    public static DBManager getInstance(Context context){
        if(dbManager == null){
            synchronized (DBManager.class){
                if(dbManager == null){
                  dbManager = new DBManager(context);
             }
            }
        }
        return dbManager;
    }



    //**********************************************************************************************
    //**********************************************************************************************

    public Utilisateur login(Context context, String login, String motPasse){

        UtilisateurSQLHelper dbHelper =   UtilisateurSQLHelper.getInstance(context); //Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

        Utilisateur utilisateur =  dbHelper.validateLogin(login, motPasse);

        return utilisateur;


    }


    //**********************************************************************************************
    //**********************************************************************************************

    public void createDB(){

        open();

        Log.i("RPI", "createDB - context: " + this.context);
        Log.i("RPI", "createDB - helper: " + this.dbHelper);
        Log.i("RPI", "createDB - database: " + this.database);

        database =  dbHelper.getWritableDatabase();

        dbHelper.onCreate(database);

        if(Constant.DEBUG){

            printDB();
        }

        close();
    }


    //**********************************************************************************************
    //**********************************************************************************************



    public DBManager open() throws SQLException {
        Log.i("RPI", "open: " + this.context);
        dbHelper = DatabaseHelper.getInstance(this.context); //(this.context, DBManager.dbName, null, DBManager.DB_VERSION);
        Log.i("RPI", "open: " + this.dbHelper);
        database = dbHelper.getWritableDatabase();
        Log.i("RPI", "open: " + this.database);
        return this;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public void close() {
        Log.i("RPI", "close: " + this.dbHelper);
        if(dbHelper != null){
        try {
            dbHelper.close();
        } catch (SQLException ex){
            Log.i ("RPI", "Error trying to close DBHelper: " + ex.getMessage());
            ex.printStackTrace();
        }

        }
    }


    //**********************************************************************************************
    //**********************************************************************************************


    public void saveDB(SQLiteDatabase database){

        Log.i("RPI", "Saving Database: " );
//        File f=new File("/data/data/your.app.package/databases/your_db.db3");
        File file = new File(database.getPath());
        FileInputStream fis=null;
        FileOutputStream fos=null;

        try
        {
            fis = new FileInputStream(file);
           // fos = new FileOutputStream("/home/daristote/AndroidStudioProjects/Reservation/Reservation.db");
            fos = new FileOutputStream("reservation.db");
           Log.i("RPI", "fos: " + fos.toString());
            while(true) {
                int i = fis.read();
                if (i != -1) {
                    fos.write(i);
                }
                else{
                    break;
                }
            }
            fos.flush();
           // Toast.makeText(this, "DB dump OK", Toast.LENGTH_LONG).show();

            Log.i("RPI", "Database copied");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //Toast.makeText(this, "DB dump ERROR", Toast.LENGTH_LONG).show();
            Log.i("RPI", "Error while copying database: " + e.getMessage());
        }
        finally
        {
            try{
                if(fos != null) {
                    fos.close();
                }
                if(fis != null) {
                    fis.close();
                }
            }
            catch(IOException ioe) {


            }
        }
    }


    //*******************************************************************************************************
    //*******************************************************************************************************
    //*******************************************************************************************************
    //*******************************************************************************************************
    //***************************************** FOR DEBUG ONLY **********************************************
    //*******************************************************************************************************
    //*******************************************************************************************************
    //*******************************************************************************************************




    public void printDB(){

        SQLiteDatabase db =  dbHelper.getReadableDatabase();

        printTable(Constant.ARTISTE_TABLE_NAME, db);
        printTable(Constant.ADRESSE_TABLE_NAME, db);
        printTable(Constant.GENRE_TABLE_NAME, db);
        printTable(Constant.PAIEMENT_TABLE_NAME, db);
        printTable(Constant.RESERVATION_TABLE_NAME, db);
        printTable(Constant.SECTION_TABLE_NAME, db);
        printTable(Constant.SIEGE_TABLE_NAME, db);
        printTable(Constant.SALLE_TABLE_NAME, db);
        printTable(Constant.CARTE_CREDIT_TABLE_NAME, db);
        printTable(Constant.UTILISATEUR_TABLE_NAME, db);
        printTable(Constant.SPECTACLE_TABLE_NAME, db);
        printTable(Constant.SPECTACLE_SECTION_TABLE_NAME, db);
        printTable(Constant.SPECTACLE_SIEGE_TABLE_NAME, db);
        printTable(Constant.RESERVATION_SPECTACLE_SIEGE_TABLE_NAME, db);

    }


    private void printTable(String table, SQLiteDatabase db){

        db.setLocale(Locale.CANADA_FRENCH);

        Cursor result = db.query(table, null, null, null, null, null, null, null);

        int nbRecords = result.getCount();
        int nbFields = result.getColumnCount();

        Log.i("RPI", "Nombre de résultats dans la table '" + table + "': " + String.valueOf(nbRecords));


        String [] columnNames = result.getColumnNames();

        if (nbRecords > 0) {

            result.moveToFirst();
            while (!result.isAfterLast()) {

                for (int i = 0; i < columnNames.length; i++){
                    result.getColumnIndex(columnNames[i]);
                    int type =  result.getType(i);
                    int intField;
                    float floatField;
                    String stringField;
                    byte[] blobField;

                    switch(type){

                        case Cursor.FIELD_TYPE_NULL:
                            Log.i("RPI" , "SQL Error: type NULL");
                            break;
                        case Cursor.FIELD_TYPE_INTEGER:
                            intField = result.getInt(i);
                            Log.i("RPI" , columnNames[i] + " : " + intField);
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            floatField =  result.getFloat(i);
                            Log.i("RPI" , columnNames[i] + " : " + floatField);
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            stringField =  result.getString(i);
                            Log.i("RPI" , columnNames[i] + " : " + stringField);
                            break;

                        case Cursor.FIELD_TYPE_BLOB:
                            blobField =  result.getBlob(i);
                            Log.i("RPI" , columnNames[i] + " : " + blobField);
                            break;

                        default:
                            Log.i("RPI" , "SQL Error: unknown type: i: " + i + " ; name: "+ columnNames[i]);
                    }

                }

                result.moveToNext();
            }


        }

    }


}
