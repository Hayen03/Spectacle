package hayen.spectacle.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import hayen.spectacle.R;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Section;
import hayen.spectacle.data.data.SpectacleSection;

/**
 * Created by daristote on 18-04-13.
 */

public class SectionAdapter extends ArrayAdapter {

    public SectionAdapter(@NonNull Context context, int textViewResourceId) {
        super(context, textViewResourceId);
//        Log.i("RPI", "SectionAdapter Constuctor: ");
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null) {

            view = LayoutInflater.from(getContext()).inflate(R.layout.ligne_section, parent, false);
        }


        SpectacleSection spectacleSection = (SpectacleSection)getItem(position);

        DatabaseHelper dbHelper =  DatabaseHelper.getInstance(getContext());
        List<Section> sections = dbHelper.getSectionsBySalleId(1);

        List<Integer> nbPlacesLibres = dbHelper.getNbFreePlacesBySections(spectacleSection.getSpectacleId());

        TextView nameView = (TextView) view.findViewById(R.id.lineSectionName);
        nameView.setText(sections.get(position).getName());

        TextView numView = (TextView) view.findViewById(R.id.lineSectionNum);
        numView.setText(String.valueOf(position+1));

        TextView priceView = (TextView) view.findViewById(R.id.lineSectionPrice);
        priceView.setText(String.format("$ %.2f", Float.valueOf(spectacleSection.getPrice())));

        TextView nbPlaces =  (TextView) view.findViewById(R.id.lineSectionNbPlaces);
        nbPlaces.setText(String.valueOf(nbPlacesLibres.get(position)));


        return view;

    }


}
