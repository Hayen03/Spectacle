//package com.ift2905.reservation;
package hayen.spectacle.activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//import com.ift2905.reservation.database.DBManager;

import java.util.List;

import hayen.spectacle.R;
import hayen.spectacle.database.DBManager;
import hayen.spectacle.database.dao.ReservationSQLHelper;
import hayen.spectacle.database.dao.SectionSQLHelper;
import hayen.spectacle.database.dao.UtilisateurSQLHelper;
import hayen.spectacle.database.data.Reservation;
import hayen.spectacle.database.data.Section;
import hayen.spectacle.database.data.Utilisateur;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Log.i("RPI", "Activity main");

       dbManager =  DBManager.getInstance(this);

    //    dbManager.open();

    ////      dbManager.createDB();
   //        dbManager.close();

        Utilisateur user = dbManager.login(this, "ArnaudFournier@dayrep.com", "weWoh9zie");
        UtilisateurSQLHelper dbHelper =   UtilisateurSQLHelper.getInstance(this); //Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);


        String courriel = "ArnaudFournier@dayrep.com";
        String motPasse = "weWoh9zie";
        String login = "Enstives97";


        Utilisateur utilisateur =  dbHelper.validateLogin(courriel, motPasse);


        if(utilisateur == null){
            Log.i("RPI", "Échec du login");
        }
         else{
            Log.i("RPI", "Login valide");
        }




        List<Section> sections = SectionSQLHelper.getInstance(this).getAllSections();

        Log.i("RPI", "Nombre de sections: " + sections.size());

        for (Section section :sections) {
            Log.i("RPI", "section: " + section);
        }


        List<Reservation> reservations = ReservationSQLHelper.getInstance(this).getAllReservations();

        for (Reservation reservation: reservations) {
            Log.i("RPI", "reservation: " + reservation);
        }


    }


}
