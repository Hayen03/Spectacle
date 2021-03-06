package hayen.spectacle.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import hayen.spectacle.R;
import hayen.spectacle.activities.CalendrierActivity;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Utilisateur;
import hayen.spectacle.util.Constant;
import hayen.spectacle.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChangerMdpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangerMdpFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    EditText currMdp;
    EditText newMdp;
    EditText confirmMdp;

    public ChangerMdpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user_id l'id de l'utilisateur a modifier
     * @return A new instance of fragment ChangerMdpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangerMdpFragment newInstance(int user_id) {
        ChangerMdpFragment fragment = new ChangerMdpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changer_mdp, container, false);

        // 1. setup les listeners pour les boutons
        ((Button) view.findViewById(R.id.annulerButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annuler(v);
            }
        });
        ((Button) view.findViewById(R.id.confirmerButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmer(v);
            }
        });

        // 2. recuperer les textedit
        currMdp = view.findViewById(R.id.ancientMdpEditText);
        newMdp = view.findViewById(R.id.nouveauMdpEditText);
        confirmMdp = view.findViewById(R.id.confirmerMdpEditText);

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

    public void annuler(View view){
        ((CalendrierActivity) getActivity()).restore();
    }

    public void confirmer(View view){
        final CalendrierActivity activity = (CalendrierActivity) getActivity();
        // 1. retrouver l'utilisateur
        Utilisateur user = activity.getCurrentUser();

        // 2. recuperer les entrees
        String currMdp_str = currMdp.getText().toString();
        String newMdp_str = newMdp.getText().toString();
        String confirmMdp_str = confirmMdp.getText().toString();

        // 3. verifier que le present mdp est valide
        if (!user.getMotPasse().equals(currMdp_str)){
            Util.alert(activity, R.string.err_titre, R.string.err_mdp_invalide, null);
            return;
        }

        // 4. verifier que  le nouveau mdp est assez long et qu'il est bien confirme
        if (newMdp_str.length() < 8){
            Util.alert(activity, R.string.err_titre, R.string.err_mdp_court, null);
            return;
        }
        if (!newMdp_str.equals(confirmMdp_str)){
            Util.alert(activity, R.string.err_titre, R.string.err_mdp_not_confirm, null);
            return;
        }

        // 5. changer les infos
        user.setMotPasse(newMdp_str);
        if (Constant.fightLaDB && user.getId() >= 0){
            DatabaseHelper.getInstance(activity).updateUtilisateur(user);
        }

        // 6. afficher msg de confirmation & retourner a la page d'info
        Util.alert(activity,R.string.suc_modif_mdp, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { activity.restore(); }
        });
    }
}
