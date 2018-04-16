//package com.ift2905.reservation;
package hayen.spectacle.activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//import com.ift2905.reservation.database.DBManager;

import java.util.List;

import hayen.spectacle.R;
import hayen.spectacle.data.DBManager;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Reservation;
import hayen.spectacle.data.data.Section;
import hayen.spectacle.data.data.Utilisateur;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Log.i("RPI", "Activity main");

        dbManager =  DBManager.getInstance(this);


        dbManager.createDB();


        Utilisateur user = dbManager.login(this, "ArnaudFournier@dayrep.com", "weWoh9zie");
        DatabaseHelper dbHelper =   DatabaseHelper.getInstance(this); //Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);


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




        List<Section> sections = DatabaseHelper.getInstance(this).getSectionsBySalleId(1);

        Log.i("RPI", "Nombre de sections: " + sections.size());

        for (Section section :sections) {
            Log.i("RPI", "section: " + section);
        }


        List<Reservation> reservations = DatabaseHelper.getInstance(this).getAllReservations();

        for (Reservation reservation: reservations) {
            Log.i("RPI", "reservation: " + reservation);
        }


    }


}
