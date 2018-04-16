package hayen.spectacle.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import hayen.spectacle.R;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Section;
import hayen.spectacle.data.data.SectionAdapter;
import hayen.spectacle.data.data.Spectacle;
import hayen.spectacle.data.data.SpectacleSection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReserverFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReserverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReserverFragment extends Fragment {
    private static final String ARG_SPECTACLE_TITRE = "titre",
                                ARG_SPECTACLE_ID = "id",
                                ARG_SPECTACLE_DATE = "date";

    private OnFragmentInteractionListener mListener;

    private ListView listSections;
    private SectionAdapter sectionAdapter;
    private TextView textViewTitre;
    private TextView textViewDate;

    private String titre, date;
    private int id;

    public ReserverFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ReserverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReserverFragment newInstance(Spectacle spectacle) {
        ReserverFragment fragment = new ReserverFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SPECTACLE_ID, spectacle.getId());
        bundle.putString(ARG_SPECTACLE_DATE, spectacle.getDate());
        bundle.putString(ARG_SPECTACLE_TITRE, spectacle.getTitre());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            titre = getArguments().getString(ARG_SPECTACLE_TITRE);
            date = getArguments().getString(ARG_SPECTACLE_DATE);
            id = getArguments().getInt(ARG_SPECTACLE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_reservation, container, false);

        // 1. trouver et initialiser les champs
        textViewTitre = view.findViewById(R.id.reservationSpecTitre);
        textViewTitre.setText(titre);
        textViewDate = view.findViewById(R.id.reservationSpecDate);
        textViewDate.setText(date);
        listSections = view.findViewById(R.id.listViewSections);

        // 2. ca
        DatabaseHelper dbHelper =  DatabaseHelper.getInstance(getActivity());


        List<Section> sections = dbHelper.getSectionsBySalleId(1);

        for (Section section: sections) {
            Log.i("RPI", "section: " + section);
        }


        List<SpectacleSection> spectacleSections =  dbHelper.getSectionsBySpectacleId(id);
        for (SpectacleSection spectacleSection: spectacleSections) {
            Log.i("RPI", "spectacleSection" + spectacleSection);
        }
        if(spectacleSections.size() > 0) {
            sectionAdapter =  new SectionAdapter(getActivity(), R.layout.ligne_section);
            for (SpectacleSection spectacleSection: spectacleSections) {
                sectionAdapter.add(spectacleSection);
            }

            listSections.setAdapter(sectionAdapter);
        }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
