//package com.ift2905.reservation;
package hayen.spectacle.activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//import com.ift2905.reservation.database.DBManager;

import hayen.spectacle.R;
import hayen.spectacle.database.DBManager;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Log.i("RPI", "Activity main");

        dbManager =  DBManager.getInstace(this);

        dbManager.open();

        dbManager.createDB();

        dbManager.close();




    }


}
