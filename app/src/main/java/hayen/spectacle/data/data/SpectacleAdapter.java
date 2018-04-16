package hayen.spectacle.data.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hayen.spectacle.R;

/**
 * Created by daristote on 18-04-13.
 */

public class SpectacleAdapter extends ArrayAdapter {


    public SpectacleAdapter(@NonNull Context context, int textViewResourceId) {
        super(context, textViewResourceId);

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View result = convertView;

        if(convertView == null){

            result = LayoutInflater.from(getContext()).inflate(R.layout.ligne_spectacle, parent, false);
        }

        Spectacle spectacle = (Spectacle)getItem(position);

        TextView titreView = (TextView) result.findViewById(R.id.ligneSpecTitre);
        titreView.setText(spectacle.getTitre());


        TextView dateView = (TextView) result.findViewById(R.id.ligneSpecDate);

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

        dateView.setText("Date: " + formatter.format(cal.getTime()));

        TextView artisteView = (TextView) result.findViewById(R.id.ligneSpecArtiste);

        Log.i("RPI", "artiste: " + artisteView.getText().toString());

        String strArtistes = "Artiste(s) : ";
        artisteView.setText(strArtistes);

        TextView artisteListView = (TextView) result.findViewById(R.id.ligneSpecListArtistes);

        List<Artiste> artistes =  spectacle.getArtistes();

      //  TextView textArtistes = (TextView) result.findViewById(R.id.ligneSpecListArtiste);

        String listArtistes = "";
        int i = 0;
        for (Artiste art : artistes) {
            listArtistes += art.getFullName() + "\n";

            Log.i("RPI", "artiste: " + listArtistes);
            if(++i < artistes.size()){
             //   listArtistes += "; ";
            }
        }
        artisteListView.setText(listArtistes);

    //    ImageView image = (ImageView) result.findViewById(R.id.ligne_specArtiste);


        return result;

    }
}
