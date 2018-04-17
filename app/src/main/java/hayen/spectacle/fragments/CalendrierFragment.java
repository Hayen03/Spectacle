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
 * {@link CalendrierFragment.OnFragmentInteractionListener} interface
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
        final List<Spectacle> spectacles = dbHelper.getAllSpectacles();

        if(spectacles != null && spectacles.size() > 0) {
            spectacleAdapter = new SpectacleAdapter(this.getActivity(), R.layout.ligne_spectacle);
            for (Spectacle spectacle : spectacles) {
                Log.i("RPI", "spectacle: " + spectacle);

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

                System.out.println("spectacle: " + spectacle);
                Log.i("RPI", "CLick!");
                Log.i("RPI", "Spectacle  id: " + spectacle.getId());

                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "spectacle", Toast.LENGTH_LONG);
                toast.show();

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
