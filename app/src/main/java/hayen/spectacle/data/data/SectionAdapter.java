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
import hayen.spectacle.data.dao.SectionSQLHelper;

/**
 * Created by daristote on 18-04-13.
 */

public class SectionAdapter extends ArrayAdapter {


    public SectionAdapter(@NonNull Context context, int textViewResourceId) {
        super(context, textViewResourceId);

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View result = convertView;

        if(convertView == null){

            result = LayoutInflater.from(getContext()).inflate(R.layout.ligne_section, parent, false);
        }

        SpectacleSection spectacleSection = (SpectacleSection)getItem(position);
        Log.i("RPI", "spectacleSection: " + spectacleSection);

        SectionSQLHelper sectionSQLHelper =  SectionSQLHelper.getInstance(getContext());
        List<Section> sections = sectionSQLHelper.getSectionsBySalleId(1);

        List<Integer> nbPlacesLibres = sectionSQLHelper.getFreePlacesBySections(spectacleSection.getSpectacleId());


        for (Integer nb: nbPlacesLibres) {
            Log.i("RPI", "nb: " + nb);

        }
        TextView nameView = (TextView) result.findViewById(R.id.lineSectionName);
        nameView.setText(sections.get(position).getName());

        Log.i("RPI", "position: " + position);
        TextView numView = (TextView) result.findViewById(R.id.lineSectionNum);
        numView.setText(String.valueOf(position+1));

        TextView priceView = (TextView) result.findViewById(R.id.lineSectionPrice);
        priceView.setText(String.valueOf(spectacleSection.getPrice()));

        TextView nbPlaces =  (TextView) result.findViewById(R.id.lineSectionNbPlaces);
        nbPlaces.setText(String.valueOf(nbPlacesLibres.get(position)));



        return result;

    }
}
