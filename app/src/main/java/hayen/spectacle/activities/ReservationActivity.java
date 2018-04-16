package hayen.spectacle.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hayen.spectacle.R;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Section;
import hayen.spectacle.data.data.SectionAdapter;
import hayen.spectacle.data.data.SpectacleSection;

public class ReservationActivity extends AppCompatActivity {

    ListView listSections;
    SectionAdapter sectionAdapter;

    TextView textViewTitre;
    TextView textViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Toast toast = Toast.makeText(getApplicationContext(), "Reservation id: " + id, Toast.LENGTH_LONG);
        toast.show();

        Log.i("RPI", "dans ReservationActivity - id:  " + id);

        String titre = intent.getStringExtra("titre");

        textViewTitre = findViewById(R.id.reservationSpecTitre);
        textViewTitre.setText(titre);

        String date =  intent.getStringExtra("date");

        textViewDate = findViewById(R.id.reservationSpecDate);
        textViewDate.setText(date);


        listSections =  findViewById(R.id.listViewSections);



        DatabaseHelper dbHelper =  DatabaseHelper.getInstance(this);


        List<Section> sections = dbHelper.getSectionsBySalleId(1);

        for (Section section: sections) {
            Log.i("RPI", "section: " + section);
        }


        List<SpectacleSection> spectacleSections =  dbHelper.getSectionsBySpectacleId(Integer.parseInt(id));
        for (SpectacleSection spectacleSection: spectacleSections) {
            Log.i("RPI", "spectacleSection" + spectacleSection);
        }
        if(spectacleSections.size() > 0) {
            sectionAdapter =  new SectionAdapter(this, R.layout.ligne_section);
            for (SpectacleSection spectacleSection: spectacleSections) {
                sectionAdapter.add(spectacleSection);
            }

            listSections.setAdapter(sectionAdapter);
        }
    }

}
