package hayen.spectacle.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hayen.spectacle.R;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Utilisateur;
import hayen.spectacle.util.Constant;
import hayen.spectacle.util.Util;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        loginButton =  findViewById(R.id.loginButton);
        email = findViewById(R.id.usernameEditText);
        password =  findViewById(R.id.passwordEditText);

    }

    public void login(View view){
        Intent intent = new Intent(this, CalendrierActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);


        if (Constant.fightLaDB){



            // 1. recuperer les informations
            String email_str = email.getText().toString();
            String mdp_str = password.getText().toString();

            //******************************* FOR DEBUG ONLY **************************
            Log.i("RPI", "courriel 1: " + email.getText().toString());
            Log.i("RPI", "mot de passe 1: " + password.getText().toString());

            email_str = "ArnaudFournier@dayrep.com";
            mdp_str = "weWoh9zie";

            Log.i("RPI", "courriel 2: " + email_str);
            Log.i("RPI", "mot de passe 2: " + email_str);
            //******************************* FOR DEBUG ONLY **************************

            // 2. recuperer l'utilisateur
            Utilisateur user = null;
            try {
                user = DatabaseHelper.getInstance(this).validateLogin(email_str, mdp_str);
            }
            catch (android.database.sqlite.SQLiteException e){
                Util.alert(this, R.string.err_titre, R.string.err_general, null);
                password.setText("");
                return;
            }

            // 3. si utilisateur existe, creer le bundle
            if (user != null){
                Bundle user_bundle = new Bundle();
                user_bundle.putString(Utilisateur.COLUMN_PRENOM, user.getPrenom());
                user_bundle.putString(Utilisateur.COLUMN_NOM, user.getNom());
                user_bundle.putString(Utilisateur.COLUMN_COURRIEL, user.getCourriel());
                // on se fou un peu de la securite dans cette application...
                user_bundle.putString(Utilisateur.COLUMN_MOT_PASSE, user.getMotPasse());
                user_bundle.putString(Utilisateur.COLUMN_TELEPHONE, user.getTelephone());
                user_bundle.putInt(Utilisateur.COLUMN_ADRESSE_ID, user.getAdresseId());
                user_bundle.putInt(Utilisateur.COLUMN_ID, user.getId());
                intent.putExtra("user", user_bundle);
            }
            else {
                Util.alert(this, R.string.err_titre, R.string.err_connexion, null);
                password.setText("");
                return;
            }
        }

        // 4. si on se rend jusqu'ici, demarrer l'activite
        startActivity(intent);
    }

    public void mdpOublie(View view){
        startActivity(new Intent(this, RecupererMdpActivity.class));
    }

    public void inscription(View view){
        startActivity(new Intent(this, InscriptionActivity.class));
    }
}
