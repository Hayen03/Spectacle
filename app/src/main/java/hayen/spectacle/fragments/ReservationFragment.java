package hayen.spectacle.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import hayen.spectacle.R;
import hayen.spectacle.activities.CalendrierActivity;
import hayen.spectacle.adapter.ReservationAdapter;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Reservation;
import hayen.spectacle.data.data.Utilisateur;
import hayen.spectacle.util.Constant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ReservationFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ReservationFragment.
     */
    public static ReservationFragment newInstance() {
        ReservationFragment fragment = new ReservationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        // 1. trouver nos reservations
        ListView reservations = view.findViewById(R.id.reservationsListView);
        CalendrierActivity activity = (CalendrierActivity) getActivity();
        Utilisateur user = activity.getCurrentUser();
        List<Reservation> allReservation;
        if (Constant.fightLaDB) {
            allReservation = DatabaseHelper.getInstance(getActivity()).getAllReservations();
            // 1.2 Filtrer les reservations
            Iterator<Reservation> it = allReservation.iterator();
            while (it.hasNext()) {
                Reservation reservation = it.next();
                if (reservation.getUserId() != user.getId()) {
                    it.remove();
                }
            }
        }
        else {
            allReservation = new LinkedList<>();
            allReservation.add(Reservation.bidon);
            allReservation.add(Reservation.bidon);
            allReservation.add(Reservation.bidon);
        }

        // 2. Creer l'adapter
        ReservationAdapter adapter = new ReservationAdapter(getActivity(), R.layout.ticket_layout);
        for (Reservation reservation : allReservation){
            adapter.add(reservation);
        }

        // 3. set l'adapter
        reservations.setAdapter(adapter);

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
