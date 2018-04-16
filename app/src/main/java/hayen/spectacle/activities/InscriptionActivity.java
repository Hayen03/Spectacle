package hayen.spectacle.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import hayen.spectacle.R;
import hayen.spectacle.database.dao.DatabaseHelper;
import hayen.spectacle.database.data.Adresse;
import hayen.spectacle.database.data.Utilisateur;
import hayen.spectacle.util.Constant;
import hayen.spectacle.util.Util;

public class InscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        Spinner provinces = (Spinner) findViewById(R.id.provinceSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.provinces_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinces.setAdapter(adapter);
    }

    public void inscrit(View view) {
        // 1. ramasser toute l'information
        String prenom = ((EditText) findViewById(R.id.prenomEditText)).getText().toString();
        String nom = ((EditText) findViewById(R.id.nomEditText)).getText().toString();
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        String mdp = ((EditText) findViewById(R.id.mdpEditText)).getText().toString();
        String confirmMdp = ((EditText) findViewById(R.id.confirmMdpEditText)).getText().toString();
        String adresse = ((EditText) findViewById(R.id.adresseEditText)).getText().toString();
        String ville = ((EditText) findViewById(R.id.villeEditText)).getText().toString();
//        String province = ((EditText) findViewById(R.id.provinceEditText)).getText().toString();
        String province = (String) ((Spinner) findViewById(R.id.provinceSpinner)).getSelectedItem();
        String cp = ((EditText) findViewById(R.id.codePostalEditText)).getText().toString();
        String phone = ((EditText) findViewById(R.id.telephoneEditText)).getText().toString();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(getBaseContext());

        // 2. Verifier que l'email est unique dans le systeme
        Utilisateur user = null;
        if (Constant.fightLaDB){
            user = dbHelper.getUtilisateurByLogin(email);
            if (user != null) {
                // blabla erreur
                alertInsc("Il existe déjà un utilisateur avec cette adresse couriel");
                return;
            }
        }

        // 3. Verifier que la confirmation du mdp est identique au mdp
        if (!confirmMdp.equals(mdp)){
            ((EditText)findViewById(R.id.confirmMdpEditText)).setText("");
            alertInsc("Le mot de passe et sa confirmation ne sont pas identique");
            return;
        }

        // 4. Verifier que le mdp fait au moins 8 caractere de long
        if (mdp.length() < 8){
            alertInsc("Le mot de passe doit faire 8 caractère minimum");
            return;
        }

        // 5. S'assurer que le num de telephone soit du bon format
        if (!phone.matches("\\(?\\d{3}\\)?(-| )?\\d{3}(-| )?\\d{4}")){
            alertInsc("Veuillez remplir un numéro de téléphone valide");
            return;
        }

        // 5-6. S'assurer que le code postal est valide
        if (!cp.matches("[A-Z]\\d[A-Z] \\d[A-Z]\\d")){
            alertInsc("Veuillez remplir un code postal valide");
            return;
        }

        // 6. s'assurer qu'aucun champ ne soit vide
        for (String str : new String[]{prenom, nom, adresse, ville, province}){
            if (str.equals("")){
                alertInsc("Veuillez remplir tout les champs");
                return;
            }
        }

        // 7. Rendu ici, toutes les informations sont bonnes et on peut rajouter l'utilisateur
        // 7.1 Separer le numero civil du nom de rue
        int num = -1;
        String rue = "";
        String[] adr_str = adresse.split(" ", 2);
        try {
            num = Integer.parseInt(adr_str[0]);
        }
        catch (NumberFormatException e) {
            Log.i("Insc", "Numero civic invalide, utilise le defaut de -1");
        }
        try {
            rue = adr_str[1];
        }
        catch (ArrayIndexOutOfBoundsException e){
            Log.i("Insc", "Nom de rue manquant, utilise le nom par defaut de rien pentoute");
        }
        Adresse adr = new Adresse(0, num, rue, ville, province, cp, 0, 0);
        long user_id = -1;
        if (Constant.fightLaDB) {
            long adr_id = dbHelper.addAdresse(adr);
            if (adr_id < 0) {
                // oops
                alertInsc("Une erreur c'est produite lors de la création de votre compte, veuillez réessayer plus tard.");
                return;
            }

            // 7.2 creer l'utilisateur
            user = new Utilisateur(prenom, nom, email, mdp, email, phone, (int) adr_id);
            user_id = dbHelper.addUtilisateur(user);
            if (user_id < 0) {
                alertInsc("Une erreur c'est produite lors de la création de votre compte, veuillez réessayer plus tard.");
                return;
            }
        }

        // 8. YAY tout est beau next
        Toast.makeText(getBaseContext(), "Compte créé correctement", Toast.LENGTH_SHORT);
        Intent intent = new Intent(this, CalendrierActivity.class);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }

    private void alertInsc(String msg){
        Util.alert(InscriptionActivity.this,
                "Oops",
                msg,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("ALERT", "alert ok");
                    }
                });
    }
}
