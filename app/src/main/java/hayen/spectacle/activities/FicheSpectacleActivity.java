package hayen.spectacle.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hayen.spectacle.R;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Artiste;
import hayen.spectacle.data.data.Genre;
import hayen.spectacle.data.data.Salle;
import hayen.spectacle.data.data.Spectacle;

public class FicheSpectacleActivity extends AppCompatActivity {

    TextView textViewTitre;
    TextView textViewDate;
    TextView textViewGenre;
    TextView textViewSalle;
    TextView textViewDuree;
    TextView textViewDescription;
    Button btnReserver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_spectacle);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Toast toast = Toast.makeText(getApplicationContext(), "id: " + id, Toast.LENGTH_LONG);
        toast.show();


        DatabaseHelper dbHelper =  DatabaseHelper.getInstance(this);
        final Spectacle spectacle = dbHelper.getSpectacleById(Integer.valueOf(id));

        Genre genre =  dbHelper.getGenreById(spectacle.getGenreId());

        Salle salle =  dbHelper.getSalleById(spectacle.getSalleId());




        Log.i("RPI", "spectacle: " + spectacle);
        Log.i("RPI", "genre: " + genre);




        textViewTitre =  findViewById(R.id.ficheTxtSepcTitle);
        textViewTitre.setText(spectacle.getTitre());

        textViewDate =  findViewById(R.id.ficheTxtSpecDate);

        SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();

        String strDate = spectacle.getDate();

        Date specDate = cal.getTime();
        try {
            specDate = formatter.parse(strDate);
        } catch (ParseException e){
            Log.i("RPI", "Exception parsing date: " + e.getMessage());
        }

        cal.setTime(specDate);

        final String formatedDate = formatter.format(cal.getTime());
        textViewDate.setText(formatedDate);

        textViewGenre =  findViewById(R.id.ficheTxtGenre);
        textViewGenre.setText(genre.getNom());

        textViewSalle = findViewById(R.id.ficheTxtSalle);
        textViewSalle.setText(salle.getNom());

        Log.i("RPI", "salle: " + salle.getNom());

        textViewDuree =  findViewById(R.id.ficheTxtDuree);
        textViewDuree.setText(spectacle.getDuree() + " min.");

        final List<Artiste> artistes =  dbHelper.getAllArtistesBySpectacleId(spectacle.getId());

        for (Artiste artiste: artistes) {
            Log.i("RPI", "artiste: " + artiste);
        }

        int size =  artistes.size();

        Log.i("RPI", "size: " + size);
        Log.i("RPI", "prenom: " + artistes.get(0).getPrenom());


        if(artistes != null && size > 0){

            TextView textArtisteTitle =  findViewById(R.id.ficheTxtArtisteTitle);
            textArtisteTitle.setVisibility(View.VISIBLE);

            TextView txtArtiste01 =  findViewById(R.id.ficheTxtViewArtiste01);
            txtArtiste01.setText(artistes.get(0).getFullName());
            txtArtiste01.setVisibility(View.VISIBLE);


            if(size > 1){
                TextView txtArtiste02 =  findViewById(R.id.ficheTxtViewArtiste02);
                txtArtiste02.setText(artistes.get(1).getFullName());
                txtArtiste02.setVisibility(View.VISIBLE);
            }

            if(size > 2){
                TextView txtArtiste03 =  findViewById(R.id.ficheTxtViewArtiste03);
                txtArtiste03.setText(artistes.get(2).getFullName());
                txtArtiste03.setVisibility(View.VISIBLE);
            }
            if(size > 3){
                TextView txtArtiste04 =  findViewById(R.id.ficheTxtViewArtiste04);
                txtArtiste04.setText(artistes.get(3).getFullName());
                txtArtiste04.setVisibility(View.VISIBLE);
            }
            if(size > 4){
                TextView txtArtiste05 =  findViewById(R.id.ficheTxtViewArtiste05);
                txtArtiste05.setText(artistes.get(4).getFullName());
                txtArtiste05.setVisibility(View.VISIBLE);
            }
            if(size > 5){
                TextView txtArtiste06 =  findViewById(R.id.ficheTxtViewArtiste06);
                txtArtiste06.setText(artistes.get(5).getFullName());
                txtArtiste06.setVisibility(View.VISIBLE);
            }


        }

        btnReserver =  findViewById(R.id.btnReserver);

        btnReserver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("RPI", "clic: " + spectacle.getId());

                Intent intent = new Intent(FicheSpectacleActivity.this, ReservationActivity.class);
                intent.putExtra("id", String.valueOf(spectacle.getId()));
                intent.putExtra("titre", spectacle.getTitre());
                intent.putExtra("date", formatedDate);

                startActivity(intent);
            }
        });

    }
}
