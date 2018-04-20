package hayen.spectacle.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hayen.spectacle.R;
import hayen.spectacle.activities.CalendrierActivity;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Artiste;
import hayen.spectacle.data.data.Genre;
import hayen.spectacle.data.data.Salle;
import hayen.spectacle.data.data.Spectacle;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FicheFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FicheFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SPECTACLE_ID = "id";

    // TODO: Rename and change types of parameters
    private int spectacleId;

    private OnFragmentInteractionListener mListener;

    public FicheFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id l'id du spectacle a afficher
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FicheFragment newInstance(int id) {
        FicheFragment fragment = new FicheFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SPECTACLE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            spectacleId = getArguments().getInt(ARG_SPECTACLE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fiche_spectacle, container, false);
        // Inflate the layout for this fragment

     //   Toast toast = Toast.makeText(getActivity().getApplicationContext(), "id: " + spectacleId, Toast.LENGTH_LONG);
      //  toast.show();


        DatabaseHelper dbHelper =  DatabaseHelper.getInstance(getActivity());
        final Spectacle spectacle = dbHelper.getSpectacleById(spectacleId);

        Genre genre =  dbHelper.getGenreById(spectacle.getGenreId());
        spectacle.setGenre(genre);
        final Salle salle =  dbHelper.getSalleById(spectacle.getSalleId());
//
//        Log.i("RPI", " in FicheFragnent >> spectacle: " + spectacle);
//        Log.i("RPI", ">> genre: " + genre);

        TextView textViewTitre = view.findViewById(R.id.ficheTxtSepcTitle);
        textViewTitre.setText(spectacle.getTitre());

        TextView textViewDate = view.findViewById(R.id.ficheTxtSpecDate);

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

        TextView textViewGenre = view.findViewById(R.id.ficheTxtGenre);
 //       Log.i("RPI", "salle: " + salle.getNom());
        textViewGenre.setText(genre.getNom());

        TextView textViewSalle = view.findViewById(R.id.ficheTxtSalle);
        textViewSalle.setText(salle.getNom());

     //   Log.i("RPI", "salle: " + salle.getNom());

        TextView textViewDuree = view.findViewById(R.id.ficheTxtDuree);
        textViewDuree.setText(spectacle.getDuree() + " min.");

        final List<Artiste> artistes =  dbHelper.getAllArtistesBySpectacleId(spectacle.getId());

    //    for (Artiste artiste: artistes) {
      //      Log.i("RPI", "artiste: " + artiste);
    //    }

        spectacle.setArtistes(artistes);

        int size =  artistes.size();

  //      Log.i("RPI", "size: " + size);
  //      Log.i("RPI", "prenom: " + artistes.get(0).getPrenom());


        if(artistes != null && size > 0){

            TextView textArtisteTitle =  view.findViewById(R.id.ficheTxtArtisteTitle);
            textArtisteTitle.setVisibility(View.VISIBLE);

            TextView txtArtiste01 =  view.findViewById(R.id.ficheTxtViewArtiste01);
            txtArtiste01.setText(artistes.get(0).getFullName());
            txtArtiste01.setVisibility(View.VISIBLE);


            if(size > 1){
                TextView txtArtiste02 = view.findViewById(R.id.ficheTxtViewArtiste02);
                txtArtiste02.setText(artistes.get(1).getFullName());
                txtArtiste02.setVisibility(View.VISIBLE);
            }

            if(size > 2){
                TextView txtArtiste03 = view.findViewById(R.id.ficheTxtViewArtiste03);
                txtArtiste03.setText(artistes.get(2).getFullName());
                txtArtiste03.setVisibility(View.VISIBLE);
            }
            if(size > 3){
                TextView txtArtiste04 = view.findViewById(R.id.ficheTxtViewArtiste04);
                txtArtiste04.setText(artistes.get(3).getFullName());
                txtArtiste04.setVisibility(View.VISIBLE);
            }
            if(size > 4){
                TextView txtArtiste05 = view.findViewById(R.id.ficheTxtViewArtiste05);
                txtArtiste05.setText(artistes.get(4).getFullName());
                txtArtiste05.setVisibility(View.VISIBLE);
            }
            if(size > 5){
                TextView txtArtiste06 = view.findViewById(R.id.ficheTxtViewArtiste06);
                txtArtiste06.setText(artistes.get(5).getFullName());
                txtArtiste06.setVisibility(View.VISIBLE);
            }


        }

        Button btnReserver =  view.findViewById(R.id.btnReserver);

        btnReserver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
         //       Log.i("RPI", "clic: " + spectacle.getId());
          //      Log.i("RPI", "clic: " + spectacle.getGenre());
                Fragment frag = ReserverFragment.newInstance(spectacle, salle);
                ((CalendrierActivity)getActivity()).overrideFragment(frag);
            }
        });

        return view;
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
