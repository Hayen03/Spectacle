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
  //  RecyclerView.ViewHolder holder;

    ViewHolder holder;

    String[] editText;
    private int currentPosition;
    //protected ArrayList<Get_All_Class_Model> get_class_details;
    public SectionAdapter(@NonNull Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        Log.i("RPI", "SectionAdapter Constuctor: ");
        currentPosition = 0;
    }

    public void initEditable(int size){
        editText =  new String[size];
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View result = convertView;


        if(result == null){

            result = LayoutInflater.from(getContext()).inflate(R.layout.ligne_section, parent, false);

       //     holder =  new ViewHolder();
//
      //      holder.editSection = (EditText) result.findViewById(R.id.reserverEditTxtSection);
          //  holder.editSection.setTag(position);
           // result.setTag(holder);

            //Fill EditText with the value you have in data source
        //    holder.editSection.setText(myItems.get(position).caption);
         //   holder.caption.setId(position);


//            holder.editSection.addTextChangedListener(new TextWatcher() {
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    int pos = start;
//
//                    Log.i("RPI", "position: " + start + "; before: "+before + "; count: " + count +"; s: "  + s);
//
//                  //  get_class_details.get(pos).setEdtsections(s.toString());
//
//                }
//
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    Log.i("RPI", "before: " + s);
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    Log.i("RPI", "position: " + position +"; after: " + editable.toString());
//                   // editText[position] = editable.toString();
//                }
//
//
//            });






        //****************************************




        SpectacleSection spectacleSection = (SpectacleSection)getItem(position);
        Log.i("RPI", "spectacleSection: " + spectacleSection);

        DatabaseHelper dbHelper =  DatabaseHelper.getInstance(getContext());
        List<Section> sections = dbHelper.getSectionsBySalleId(1);

        List<Integer> nbPlacesLibres = dbHelper.getNbFreePlacesBySections(spectacleSection.getSpectacleId());


        for (Integer nb: nbPlacesLibres) {
            Log.i("RPI", "nb: " + nb);

        }
        TextView nameView = (TextView) result.findViewById(R.id.lineSectionName);
        nameView.setText(sections.get(position).getName());

        Log.i("RPI", "position: " + position);
        TextView numView = (TextView) result.findViewById(R.id.lineSectionNum);
        numView.setText(String.valueOf(position+1));

        TextView priceView = (TextView) result.findViewById(R.id.lineSectionPrice);
        priceView.setText(String.format("$ %.2f", Float.valueOf(spectacleSection.getPrice())));

        TextView nbPlaces =  (TextView) result.findViewById(R.id.lineSectionNbPlaces);
        nbPlaces.setText(String.valueOf(nbPlacesLibres.get(position)));

      //  EditText editNbPlaces =  (EditText) result.findViewById(R.id.reserverEditTxtSection);


     //   editNbPlaces.getText().toString();
        }
//        else{
//           // holder = (ViewHolder) convertView.getTag();
//        }

        return result;

    }

//    public void addTextItem(String text){
 //       this.editText.add(text);
  //  }


    public void setEditTextItem(int pos, String text){
      //  this.editText.set(pos, text);
        this.editText[pos] =  text;
    }

    public String[] getEditText() {
        return this.editText;
    }

    private class ViewHolder {
        EditText editSection;


    }
}
