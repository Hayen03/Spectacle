package hayen.spectacle.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import hayen.spectacle.R;
import hayen.spectacle.activities.CalendrierActivity;
import hayen.spectacle.database.dao.DatabaseHelper;
import hayen.spectacle.database.data.Adresse;
import hayen.spectacle.database.data.Utilisateur;
import hayen.spectacle.util.Constant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER_ID = "user_id";

    // TODO: Rename and change types of parameters
    private int user_id = -1;

    private OnFragmentInteractionListener mListener;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user_id l'id de l'utilisateur a afficher.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(int user_id) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, user_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_id = getArguments().getInt(ARG_USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        // 1. trouver l'utilisateur et l'adresse
        Utilisateur user = Utilisateur.bidon;
        Adresse adresse = Adresse.bidon;
        if (Constant.fightLaDB && user_id != -1){
            DatabaseHelper dbHelper = DatabaseHelper.getInstance(getActivity());
            Utilisateur user2 = dbHelper.getUtilisateurById(user_id);
            if (user2 != null) {
                user = user2;
                Adresse adr2 = dbHelper.getAdresseById(user.getAdresseId());
                if (adr2 != null)
                    adresse = adr2;
            }
        }

        // 2. afficher les informations
        TextView    nomTV = view.findViewById(R.id.nomTextView),
                    emailTV = view.findViewById(R.id.emailTextView),
                    phoneTV = view.findViewById(R.id.phoneTextView),
                    adresseTV = view.findViewById(R.id.adresseTextView),
                    villeTV = view.findViewById(R.id.villeTextView),
                    provinceTV = view.findViewById(R.id.provinceTextView),
                    codeTV = view.findViewById(R.id.codeTextView);
        nomTV.setText(String.format("%s, %s", user.getNom(), user.getPrenom()));
        emailTV.setText(String.format("Email: %s", user.getCourriel()));
        phoneTV.setText(String.format("Téléphone: %s", user.getTelephone()));
        adresseTV.setText(String.format("Adresse: %d, %s", adresse.getNumero(), adresse.getRue()));
        villeTV.setText(String.format("Ville: %s", adresse.getVille()));
        provinceTV.setText(String.format("Province: %s", adresse.getProvince()));
        codeTV.setText(String.format("Code postal: %s", adresse.getCodePostal()));

        // 3. ajouter les listeners au boutons
        ((Button) view.findViewById(R.id.modifierInfoButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifierInfo(v);
            }
        });
        ((Button) view.findViewById(R.id.modifierMdpButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifierMdp(v);
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

    public void modifierInfo(View view){

    }

    public void modifierMdp(View view){
        ((CalendrierActivity) getActivity()).overrideFragment(ChangerMdpFragment.class);
    }
}
