package hayen.spectacle.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import hayen.spectacle.R;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Utilisateur;
import hayen.spectacle.util.Constant;
import hayen.spectacle.util.Util;

public class NouveauMdpActivity extends AppCompatActivity {

    private EditText codeET, emailET, mdpET, confirmET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_mdp);

        codeET = findViewById(R.id.codeEditText);
        emailET = findViewById(R.id.emailEditText);
        mdpET = findViewById(R.id.newMdpEditText);
        confirmET = findViewById(R.id.confirmMdpEditText);

    }

    public void toLogin(View view){
        // 1. recuperer les entres
        String  code_str = codeET.getText().toString(),
                email_str = emailET.getText().toString(),
                mdp_str = mdpET.getText().toString(),
                confirm_str = confirmET.getText().toString();

        // 2. tester le code
        if (!code_str.equals(Constant.codeSecurite)){
            Util.alert(this, R.string.err_titre, R.string.err_code_recup, null);
            return;
        }

        // 3. verifier que le mot de passe est asser long
        if (mdp_str.length() < 8){
            Util.alert(this, R.string.err_titre, R.string.err_mdp_court, null);
            return;
        }

        // 4. verifier la confirmation du mdp
        if (!mdp_str.equals(confirm_str)){
            Util.alert(this, R.string.err_titre, R.string.err_mdp_not_confirm, null);
            return;
        }

        // 5. Tenter de modifier le mdp dans la BD
        if (Constant.fightLaDB){
            Utilisateur user = null;
            user = DatabaseHelper.getInstance(this).getUtilisateurByLogin(email_str);
            if (user == null){
                Util.alert(this, R.string.err_titre, R.string.err_email_invalide, null);
                return;
            }
            user.setMotPasse(mdp_str);
            DatabaseHelper.getInstance(this).updateUtilisateur(user);
        }

        // 6. message de succes et retour a l'ecran de login
        Util.alert(this, R.string.suc_modif_mdp, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(NouveauMdpActivity.this, LoginActivity.class));
            }
        });

    }

    public void resend(View view){
        // "renvoi le code"
        // ==> '1234567'

        Toast.makeText(this, "Code renvoye", Toast.LENGTH_SHORT).show();
    }
}
