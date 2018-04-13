package hayen.spectacle.database.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

        TextView titre = (TextView) result.findViewById(R.id.ligne_specTitre);
        titre.setText(spectacle.getTitre());


        TextView date = (TextView) result.findViewById(R.id.ligne_specDate);

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

        date.setText("Date: " + formatter.format(cal.getTime()));

        TextView artiste = (TextView) result.findViewById(R.id.ligne_specArtiste);

        List<Artiste> artistes =  spectacle.getArtistes();

        String strArtistes = "Artiste(s) : ";
        int i = 0;
        for (Artiste art : artistes) {
            strArtistes += art.getPrenom() + " " + art.getNom();

            if(++i < artistes.size()){
                strArtistes += "; ";
            }
        }
        artiste.setText(strArtistes);

    //    ImageView image = (ImageView) result.findViewById(R.id.ligne_specArtiste);


        return result;

    }
}
