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
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import hayen.spectacle.R;
import hayen.spectacle.activities.CalendrierActivity;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Artiste;
import hayen.spectacle.data.data.Spectacle;
import hayen.spectacle.adapter.SpectacleAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendrierFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendrierFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    ListView listSpectacles;
    SpectacleAdapter spectacleAdapter;

    public CalendrierFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment CalendrierFragment.
     */
    public static CalendrierFragment newInstance() {
        CalendrierFragment fragment = new CalendrierFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendrier, container, false);
        listSpectacles = view.findViewById(R.id.listViewSpectacle);

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(getActivity().getBaseContext());


        Calendar cal = Calendar.getInstance();

        SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
        String currentDate =  formatter.format(cal.getTime());

        final List<Spectacle> spectacles = dbHelper.getSpectaclesByDate(currentDate);


        if(spectacles != null && spectacles.size() > 0) {
            spectacleAdapter = new SpectacleAdapter(this.getActivity(), R.layout.ligne_spectacle);
            for (Spectacle spectacle : spectacles) {
              //  Log.i("RPI", "spectacle: " + spectacle);

                List<Artiste> artistes = dbHelper.getAllArtistesBySpectacleId(spectacle.getId());

                spectacle.setArtistes(artistes);

                spectacleAdapter.add(spectacle);

            }
        }

        listSpectacles.setAdapter(spectacleAdapter);

        listSpectacles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Spectacle spectacle =  spectacles.get(i);

                Fragment fiche = FicheFragment.newInstance(spectacle.getId());
                ((CalendrierActivity)getActivity()).overrideFragment(fiche);
            }
        });

        // Inflate the layout for this fragment
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
