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
import hayen.spectacle.data.data.Spectacle;
import hayen.spectacle.util.Constant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RechercheFragment.OnFragmentInteractionListener} interface
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
            SQLiteDatabase db = DatabaseHelper.getInstance(getActivity()).getReadableDatabase();
            String[] args = new String[DatabaseQueries.SEARCH_QUERY_ARG_NUMBER];
            for (int i = 0; i < DatabaseQueries.SEARCH_QUERY_ARG_NUMBER; i++)
                args[i] = query;
            Cursor cursor = db.rawQuery(DatabaseQueries.SEARCH_QUERY, args);
            if (cursor != null){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Spectacle spectacle = new Spectacle(
                            cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_TITRE)),
                            cursor.getString(cursor.getColumnIndex(Spectacle.COLUMN_DATE_SPECTACLE)),
                            cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_DUREE)),
                            cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_GENRE_ID)),
                            cursor.getInt(cursor.getColumnIndex(Spectacle.COLUMN_SALLE_ID)));
                    results.add(spectacle);
                }
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
