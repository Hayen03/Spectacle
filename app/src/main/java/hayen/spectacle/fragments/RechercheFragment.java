package hayen.spectacle.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.LinkedList;
import java.util.List;

import hayen.spectacle.R;
import hayen.spectacle.adapter.SpectacleAdapter;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.dao.DatabaseQueries;
import hayen.spectacle.data.data.Artiste;
import hayen.spectacle.data.data.Spectacle;
import hayen.spectacle.util.Constant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RechercheFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RechercheFragment extends Fragment implements SearchView.OnQueryTextListener{

    private SearchView search;
    private SpectacleAdapter results;

    private OnFragmentInteractionListener mListener;

    public RechercheFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment RechercheFragment.
     */
    public static RechercheFragment newInstance() {
        RechercheFragment fragment = new RechercheFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recherche, container, false);

        // trouver les elements de l'UI
        search = view.findViewById(R.id.searchView);
        ListView resultView = view.findViewById(R.id.searchResultListView);
        results = new SpectacleAdapter(getActivity(), R.layout.ligne_spectacle);
        resultView.setAdapter(results);

        search.setOnQueryTextListener(this);

        return view;
    }

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

    @Override
    public boolean onQueryTextSubmit(String query) {
        // 1. vider la listview des resultats precedent
        results.clear();

        // 1. Utiliser la db pour chercher les infos necessaires
//        List<Spectacle> spectacles = new LinkedList<>();
        if (Constant.fightLaDB) {
            DatabaseHelper dbHelper = DatabaseHelper.getInstance(getActivity());
            List<Spectacle> spectacles = dbHelper.getAllSpectacles();
            for (Spectacle spectacle : spectacles) {
                List<Artiste> artistes = dbHelper.getAllArtistesBySpectacleId(spectacle.getId());
                spectacle.setArtistes(artistes);
                if (spectacle.getTitre().contains(query)) {
                    results.add(spectacle);
                }
                else {
                    for (Artiste artiste : artistes){
                        if (artiste.getNom().contains(query) ||
                                artiste.getNom().contains(query) ||
                                artiste.getFullName().contains(query)){
                            results.add(spectacle);
                        }
                }
            }
/*            List<Artiste> artistes = dbHelper.getAllArtistes();
            for (Artiste artiste : artistes){
                if (artiste.getNom().contains(query) || artiste.getNom().contains(query) || artiste.getFullName().contains(query)){
                    String sqlquery = "select spectacle.* from spectacle join spectacle_artiste as sa on spectacle.id = sa.id_spectacle join artiste on sa.id_artiste = artiste.id where artiste.id = '" + artiste.getId() + "'";
                    Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sqlquery, null);

                }
                */
            }
        }
        else {
            for (Spectacle spectacle : Spectacle.bidons){
                if (spectacle.getTitre().contains(query))
                    results.add(spectacle);
            }
        }

        results.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

}
