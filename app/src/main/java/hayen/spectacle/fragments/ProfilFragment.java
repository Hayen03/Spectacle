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

import hayen.spectacle.R;
import hayen.spectacle.activities.CalendrierActivity;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Adresse;
import hayen.spectacle.data.data.Utilisateur;
import hayen.spectacle.util.Constant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ProfilFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance() {
        ProfilFragment fragment = new ProfilFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        // 1. trouver l'utilisateur et l'adresse
        CalendrierActivity activity = (CalendrierActivity) getActivity();
        Utilisateur user = activity.getCurrentUser();
        Adresse adresse = Adresse.bidon;
        if (Constant.fightLaDB && user != null){
            adresse = DatabaseHelper.getInstance(getActivity()).getAdresseById(user.getAdresseId());
            if (adresse == null)
                adresse = Adresse.bidon;
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

    public void modifierInfo(View view){
        Log.i("DEBUG", String.format("%s VS %s :=> %b", Fragment.class.getName(), ModifierInfoFragment.class.getName(), Fragment.class.isAssignableFrom(ModifierInfoFragment.class)));
        ((CalendrierActivity) getActivity()).overrideFragment(ModifierInfoFragment.class);
    }

    public void modifierMdp(View view){
        ((CalendrierActivity) getActivity()).overrideFragment(ChangerMdpFragment.class);
    }
}
