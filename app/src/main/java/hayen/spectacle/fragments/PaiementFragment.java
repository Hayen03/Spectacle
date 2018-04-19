package hayen.spectacle.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import hayen.spectacle.R;
import hayen.spectacle.activities.CalendrierActivity;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Adresse;
import hayen.spectacle.data.data.CarteCredit;
import hayen.spectacle.data.data.Paiement;
import hayen.spectacle.data.data.Reservation;
import hayen.spectacle.data.data.Salle;
import hayen.spectacle.data.data.Spectacle;
import hayen.spectacle.data.data.Utilisateur;
import hayen.spectacle.util.Constant;
import hayen.spectacle.util.Util;


public class PaiementFragment extends Fragment {
    private static final String ARG_PAIEMENT = "montant";
    private OnFragmentInteractionListener mListener;

    private EditText nomEditText;
    private EditText numeroEditText;
    private EditText codeEditText;
    private TextView montantTextView;

    private Spinner dateAnneeSpinner;
    private Spinner dateMoisSpinner;

    private Button btnAnnuler;
    private Button btnPayer;

    private float montant;


    public PaiementFragment() {}

    public static PaiementFragment newInstance(){
        return new PaiementFragment();
    }

    public static PaiementFragment newInstance(float montant) {
        PaiementFragment fragment = new PaiementFragment();
        Bundle bundle = new Bundle();
        bundle.putFloat (ARG_PAIEMENT, montant);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            montant = getArguments().getFloat(ARG_PAIEMENT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paiement, container, false);

        // 1. trouver les champs d'editions
        nomEditText = (EditText) view.findViewById(R.id.payerNomEditText);
        numeroEditText = (EditText)view.findViewById(R.id.payerNumEditText);
        codeEditText = (EditText)view.findViewById(R.id.payerCodeSecEditText);
        dateMoisSpinner = (Spinner)view.findViewById(R.id.payerDateMoisExpSpinner);
        dateAnneeSpinner = (Spinner) view.findViewById(R.id.payerDateAnneeExpSpinner);


        montantTextView =  (TextView) view.findViewById(R.id.paiementTextViewMontant);


        montantTextView.setText("$ " +String.valueOf(String.format("%.2f", montant)));

        final CalendrierActivity activity = (CalendrierActivity) getActivity();
        final Utilisateur user = activity.getCurrentUser();

        CarteCredit carte = null;
        if (user.getId() >= 0) {
            carte = DatabaseHelper.getInstance(activity).getCarteByUserId(user.getId());
        }



        Calendar cal = Calendar.getInstance();
        int mois =  cal.get(Calendar.MONTH);
        int annee =  cal.get(Calendar.YEAR);

        if(carte != null) {

            nomEditText.setText(carte.getNomUtilisateur());
            numeroEditText.setText(carte.getNumero());
            dateMoisSpinner.setSelection(mois);
            dateAnneeSpinner.setSelection(annee);
            codeEditText.setText(carte.getCode());

        }

        List<Integer> selectionYears =  new ArrayList<>();
        List<Integer> selectionMonths =  new ArrayList<>();

        int currentYear =  cal.get(Calendar.YEAR);
        for (int i = 0; i < 12 ; i++) {
            if(i < 5) {
                selectionYears.add(currentYear + i);
            }
            selectionMonths.add(i+1);
        }


        dateMoisSpinner =  (Spinner) view.findViewById(R.id.paiementDateMoisExpSpinner);

        ArrayAdapter<String> monthsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, selectionMonths);
        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dateMoisSpinner.setAdapter(monthsAdapter);
        dateMoisSpinner.setSelection(cal.get(Calendar.MONTH));

        dateAnneeSpinner= (Spinner) view.findViewById(R.id.paiementDateAnneeExpSpinner);

        ArrayAdapter<String> yearsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, selectionYears);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dateAnneeSpinner.setAdapter(yearsAdapter);
        dateAnneeSpinner.setSelection(0);

        // 4. Creer les actions des boutons
        ((Button) view.findViewById(R.id.paiementAnnulerButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.restore();
            }
        });

        btnPayer =  (Button) view.findViewById(R.id.paiementPayerButton);


        btnPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 4.1 prendre toute les entrees
                String  nom = nomEditText.getText().toString();
                String numStr =  numeroEditText.getText().toString();
                String mois =  String.valueOf(dateMoisSpinner.getSelectedItemPosition());
                String annee =  String.valueOf(dateAnneeSpinner.getSelectedItem());
                String codeStr =  codeEditText.getText().toString();


                if(nom.length() == 0 || numStr.length()  == 0 || codeStr.length() == 0){
                    Util.alert(activity, R.string.err_titre, R.string.err_champ_vide, null);
                    return;
                }
                if(numStr.length() != 16){
                    Util.alert(activity, "Oops", "Veuillez entrer un numéro de carte valide (sans espace)", null);
                    return;
                }

                Long num;
                int code;
                try {

                    Log.i("RPI", numStr);
                    Log.i("RPI", codeStr);
                    num = Long.parseLong(numStr);
                    code = Integer.parseInt(codeStr);
                }
                catch (NumberFormatException e) {
                    Util.alert(activity, "Oops", "Veuillez entrer un numéro valide", null);
                    e.printStackTrace();
                    Log.i("RPI", "exception: " + e.getMessage());
                    return;
                }


                Random rand = new Random();

                long resConfirmnationNum = 100000 + rand.nextInt(900000);


                Calendar cal =  Calendar.getInstance();
                SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd HH:mm");

                String dateReservation = formatter.format(cal.getTime());

                Reservation reservation =  new Reservation();
                reservation.setNumeroConfirmation(String.valueOf(resConfirmnationNum));
                reservation.setDateReservation(dateReservation);
                reservation.setUserId(user.getId());

                long id = DatabaseHelper.getInstance(activity).replaceReservation(reservation);


                if(id >= 0){

                    Paiement paiement = new Paiement();
                    paiement.setDatePaiement(dateReservation);
                    paiement.setMontant(montant);
                    paiement.setReservation_id((int)id);
                    long result = DatabaseHelper.getInstance(activity).addPaiement(paiement);
                    Log.i("RPI", "id after replace in db: " + id + " result: " + result);
                }

  ;




                // 4.4 commit les modifs a la BD
//                if (Constant.fightLaDB){
//                    DatabaseHelper.getInstance(activity).updateUtilisateur(user);
//                    DatabaseHelper.getInstance(activity).updateAdresse(adresse);
//                }

                String confirmMessage = "Votre réservation a été enregistrée avec succès.\n";
                String confirmMessage2 = "Votre numéro de confirmation: " + resConfirmnationNum;
             //   Util.alert(activity, "Oops", confirmMessage, null);
                // 4.5 retourner au fragment de profil avec un message de succes
                Util.alert(activity, confirmMessage, confirmMessage2,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.restore();
                    }
                });
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
