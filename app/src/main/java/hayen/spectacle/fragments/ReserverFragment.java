package hayen.spectacle.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hayen.spectacle.R;
import hayen.spectacle.activities.CalendrierActivity;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Salle;
import hayen.spectacle.data.data.Section;
import hayen.spectacle.adapter.SectionAdapter;
import hayen.spectacle.data.data.Spectacle;
import hayen.spectacle.data.data.SpectacleSection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReserverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReserverFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private static final String ARG_SPECTACLE_TITRE = "titre",
                                ARG_SPECTACLE_ID = "id",
                                ARG_SPECTACLE_DATE = "date",
                                ARG_SPECTACLE_GENRE = "genre",
                                ARG_SPECTACLE_DUREE = "duree",
                                ARG_SPECTACLE_ARTISTES = "artistes",
                                ARG_SALLE_NOM = "salle";


    private OnFragmentInteractionListener mListener;

    private ListView listViewSections;
    private SectionAdapter sectionAdapter;
    private TextView textViewTitre;
    private TextView textViewDate;

    private TextView textViewGenre;
    private TextView textViewDuree;
    private TextView textViewSalle;
    private TextView textViewTotal;


   // private EditText editTextNumSection;
   // private EditText editTextNbSieges;
    private Spinner spinnerNumSection;
    private Spinner spinnerNbSieges;

    private Button btnCalculer;
    private Button btnPayer;

    private ListView listView;

    private String titre, date;
    private String genre;
    private String salle;
    private int duree;
    private int id;
    private float montantTotal;


    private List<Integer> nbSiegesLibres;



    public ReserverFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ReserverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReserverFragment newInstance(Spectacle spectacle, Salle salle) {
        ReserverFragment fragment = new ReserverFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SPECTACLE_ID, spectacle.getId());
        bundle.putString(ARG_SPECTACLE_DATE, spectacle.getDate());
        bundle.putString(ARG_SPECTACLE_TITRE, spectacle.getTitre());
        bundle.putString(ARG_SPECTACLE_GENRE, spectacle.getGenre().getNom());
        bundle.putInt(ARG_SPECTACLE_DUREE, spectacle.getDuree());
        bundle.putString(ARG_SALLE_NOM, salle.getNom());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            titre = getArguments().getString(ARG_SPECTACLE_TITRE);
            date = getArguments().getString(ARG_SPECTACLE_DATE);
            genre = getArguments().getString(ARG_SPECTACLE_GENRE);
            duree = getArguments().getInt(ARG_SPECTACLE_DUREE);
            salle = getArguments().getString(ARG_SALLE_NOM);
            id = getArguments().getInt(ARG_SPECTACLE_ID);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserver, container, false);

        // 1. trouver et initialiser les champs
        textViewTitre = view.findViewById(R.id.reserverSpecTitre);
        textViewTitre.setText(titre);
        textViewDate = view.findViewById(R.id.reserverTxtDate);


        SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();


        Date specDate = cal.getTime();
        try {
            specDate = formatter.parse(date);
        } catch (ParseException e){
            Log.i("RPI", "Exception parsing date: " + e.getMessage());
        }

        cal.setTime(specDate);

        final String formatedDate = formatter.format(cal.getTime());
        textViewDate.setText(formatedDate);


        listViewSections = view.findViewById(R.id.reserverListViewSections);

        textViewGenre =  view.findViewById(R.id.reserverTxtGenre);
        textViewGenre.setText(genre);
        textViewDuree =  view.findViewById(R.id.reserverTxtDuree);
        textViewDuree.setText(String.valueOf(duree) + " min.");
        textViewSalle =  view.findViewById(R.id.reserverTxtSalle);
        textViewSalle.setText(salle);

       // editTextNumSection =  view.findViewById(R.id.reserverEditTxtSection);
       // editTextNbSieges =  view.findViewById(R.id.reserverEditTxtNbSieges);


        listView =  (ListView) view.findViewById(R.id.reserverListViewSections);


        textViewTotal = view.findViewById(R.id.reserverTxtTotal);

        // Spinner element
        spinnerNumSection = (Spinner) view.findViewById(R.id.reserverNumSectionSpinner);
        spinnerNumSection.setOnItemSelectedListener(this);

        spinnerNbSieges = (Spinner) view.findViewById(R.id.reserverNbSiegesSpinner);
        spinnerNbSieges.setOnItemSelectedListener(this);



        // 2. ca
        DatabaseHelper dbHelper =  DatabaseHelper.getInstance(getActivity());


        final List<Section> sections = dbHelper.getSectionsBySalleId(1);

        for (Section section: sections) {
            Log.i("RPI", "section: " + section);
        }

        // Spinner Drop down elements
        List<String> sectionNumbers = new ArrayList<>();

        final List<SpectacleSection> spectacleSections =  dbHelper.getSectionsBySpectacleId(id);
        final List<Integer> nbPlacesLibres = dbHelper.getFreePlacesBySections(id);



        int nbSections =  spectacleSections.size();
        for (int i = 0; i < nbSections; i++) {
            Log.i("RPI", "spectacleSection" + spectacleSections.get(i));
            sectionNumbers.add(String.valueOf(i+1));
        }

        int nbSiegesDispo = nbPlacesLibres.get(0);

        for (int i = 1; i < nbPlacesLibres.size() ; i++) {
            int n = nbPlacesLibres.get(i);
            if(n > nbSiegesDispo){
                nbSiegesDispo =  n;
            }
        }



        Log.i("RPI", "nbSiegesDispo: " + nbSiegesDispo);

        this.nbSiegesLibres =  new ArrayList<>();

        for (int i = 0; i < nbSiegesDispo; i++) {
            nbSiegesLibres.add(i+1);
        }

        final ArrayAdapter<String> numSectionAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, sectionNumbers);
        numSectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerNumSection.setAdapter(numSectionAdapter);

        ArrayAdapter<String> nbSiegesAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, nbSiegesLibres);
        nbSiegesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerNbSieges.setAdapter(nbSiegesAdapter);

        if(spectacleSections.size() > 0) {
            sectionAdapter =  new SectionAdapter(getActivity(), R.layout.ligne_section);

            int i = 0;
            for (SpectacleSection spectacleSection: spectacleSections) {
                sectionAdapter.add(spectacleSection);
            //    sectionAdapter.addTextItem("0");
            }

            sectionAdapter.initEditable(spectacleSections.size());
            listViewSections.setAdapter(sectionAdapter);
        }


        btnCalculer =  view.findViewById(R.id.reserverBtnCalculer);

        btnCalculer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                int nbSections =  spectacleSections.size();
                for (int i =0; i< nbSections; i++){

                    Log.i("RPI", "clic 1: " + spectacleSections.get(i).getPrice());
                    Log.i("RPI", "clic 2: " + sectionAdapter.getItem(i));
                    Log.i("RPI", "clic 3: " +  listViewSections.getItemAtPosition(i));
                }


              //  String numSectionValue =  editTextNumSection.getText().toString();

                String numSectionValue =  String.valueOf(spinnerNumSection.getSelectedItemPosition() + 1);
               // String nbSiegesValue =  editTextNbSieges.getText().toString();

                String nbSiegesValue = String.valueOf(spinnerNbSieges.getSelectedItemPosition() + 1);
                Log.i("RPI", "numSectionValue : " + numSectionValue);
                Log.i("RPI", "nbSiegesValue 1: " + nbSiegesValue);


                //TODO: Integrity verification: integers

                if(numSectionValue.length() > 0 && nbSiegesValue.length() > 0) {
                    int numSection = Integer.valueOf(numSectionValue);
                    int nbSieges = Integer.valueOf(nbSiegesValue);

                    float total = 0.0f;

                    if (numSection >= 1 && numSection <= spectacleSections.size() &&
                            nbSieges >= 0 && nbSieges <= nbPlacesLibres.get(numSection - 1)) {

                        total = nbSieges * spectacleSections.get(numSection - 1).getPrice();

                        float taxe = (15 * total) / 100;
                        total += taxe;
                        Log.i("RPI", "prix : " + total);
                        textViewTotal.setText("$ " + String.format("%.2f", Float.valueOf(total)) + " (taxes inc.)");

                        montantTotal =  total;
                    }
                }



//                String[] editables =  sectionAdapter.getEditText();
//                Log.i("RPI", "les éditables: taille: " + editables.length );
//                for (int i = 0; i < editables.length; i++){
//                    Log.i("RPI", "i: " + i + " value: " + editables[i]);
//                }
            }
        });



        btnPayer =  view.findViewById(R.id.reserverbtnPayer);
        btnPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = PaiementFragment.newInstance(montantTotal);
                ((CalendrierActivity) getActivity()).overrideFragment(frag);
            }
        });

//
//        spinnerNumSection.setOnItemClickListener(new AdapterView.OnItemSelectedListener(){
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        Log.i("RPI", "Selected: " + item);


        }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
