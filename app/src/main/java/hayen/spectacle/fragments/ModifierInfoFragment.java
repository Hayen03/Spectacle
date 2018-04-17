package hayen.spectacle.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import hayen.spectacle.R;
import hayen.spectacle.activities.CalendrierActivity;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Adresse;
import hayen.spectacle.data.data.Utilisateur;
import hayen.spectacle.util.Constant;
import hayen.spectacle.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModifierInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModifierInfoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private EditText nomET, prenomET, emailET, phoneET, adrET, villeET, codeET;
    private Spinner provinceSpin;

    public ModifierInfoFragment() {
        // Required empty public constructor
    }

    public static ModifierInfoFragment newInstance(){
        return new ModifierInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modifier_info, container, false);

        // 1. trouver les champs d'editions
        nomET = view.findViewById(R.id.nomEditText);
        prenomET = view.findViewById(R.id.prenomEditText);
        emailET = view.findViewById(R.id.emailEditText);
        phoneET = view.findViewById(R.id.phoneEditText);
        adrET = view.findViewById(R.id.adresseEditText);
        villeET = view.findViewById(R.id.villeEditText);
        codeET = view.findViewById(R.id.codeEditText);
        provinceSpin = view.findViewById(R.id.provinceSpinner);

        // 2. Obtenir l'utilisateur et son adresse
        final CalendrierActivity activity = (CalendrierActivity) getActivity();
        final Utilisateur user = activity.getCurrentUser();
        final Adresse adresse;
        if (user.getId() >= 0)
            adresse = DatabaseHelper.getInstance(activity).getAdresseById(user.getAdresseId());
        else
            adresse = Adresse.bidon;

        // 3. initialiser les champs
        nomET.setText(user.getNom());
        prenomET.setText(user.getPrenom());
        emailET.setText(user.getCourriel());
        phoneET.setText(user.getTelephone());
        adrET.setText(String.format("%d %s", adresse.getNumero(), adresse.getRue()));
        villeET.setText(adresse.getVille());
        codeET.setText(adresse.getCodePostal());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.provinces_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpin.setAdapter(adapter);
        provinceSpin.setSelection(adapter.getPosition(adresse.getProvince()));

        // 4. Creer les actions des boutons
        ((Button) view.findViewById(R.id.annulerButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.restore();
            }
        });
        ((Button) view.findViewById(R.id.confirmerButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 4.1 prendre toute les entrees
                String  nom_str = nomET.getText().toString(),
                        prenom_str = prenomET.getText().toString(),
                        email_str = emailET.getText().toString(),
                        phone_str = phoneET.getText().toString(),
                        adr_str = adrET.getText().toString(),
                        ville_str = villeET.getText().toString(),
                        code_str = codeET.getText().toString(),
                        province_str = provinceSpin.getSelectedItem().toString();

                // 4.2 Verifier les entrees et retourner un message d'erreur si necessaire
                // 4.2.1 email dans le bon format (regex trouve sur internet http://emailregex.com
                if (!email_str.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
                    Util.alert(activity, "Oops", "L'adresse couriel entrée est incorecte.", null);
                    return;
                }
                // 4.2.2 telephone dans le bon format (regex trouve a https://www.safaribooksonline.com/library/view/regular-expressions-cookbook/9781449327453/ch04s02.html)
                if (!phone_str.matches("^\\(?([0-9]{3})\\)?[-.●]?([0-9]{3})[-.●]?([0-9]{4})$")){
                    Util.alert(activity, "Oops", "Le numéro de téléphone entré est invalide.", null);
                    return;
                }
                // 4.2.3 code postal dans le bon format
                if (!code_str.matches("^[A-Z]\\d[A-Z] \\d[A-Z]\\d$")){
                    Util.alert(activity, "Oops", "Le code postal entré est invalide.", null);
                    return;
                }
                // 4.2.n s'assurer qu'aucun champ soit vide
                for (String str : new String[]{nom_str, prenom_str, adr_str, ville_str}){
                    if (str.length() == 0){
                        Util.alert(activity, "Oops", "Veuillez remplir tout les champs.", null);
                        return;
                    }
                }
                // 4.2.n+1 parse l'adresse civic
                int num = -1;
                String rue = "";
                String[] adr_arr = adr_str.split(" ", 2);
                try {
                    num = Integer.parseInt(adr_arr[0]);
                    rue = adr_arr[1];
                }
                catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    Util.alert(activity, "Oops", "L'adresse entrée est incomplète.", null);
                    return;
                }

                // 4.3 modifier les informations
                user.setCourriel(email_str);
                user.setLogin(email_str);
                user.setNom(nom_str);
                user.setPrenom(prenom_str);
                user.setTelephone(phone_str);
                adresse.setCodePostal(code_str);
                adresse.setNumero(num);
                adresse.setRue(rue);
                adresse.setProvince(province_str);
                adresse.setVille(ville_str);

                // 4.4 commit les modifs a la BD
                if (Constant.fightLaDB){
                    DatabaseHelper.getInstance(activity).updateUtilisateur(user);
                    DatabaseHelper.getInstance(activity).updateAdresse(adresse);
                }

                // 4.5 retourner au fragment de profil avec un message de succes
                Util.alert(activity, null, "Vos informations ont été modifiées.", null);
                activity.restore();
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
