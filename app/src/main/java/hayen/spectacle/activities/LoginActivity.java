package hayen.spectacle.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hayen.spectacle.R;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.data.Utilisateur;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton =  findViewById(R.id.loginButton);
        email = (EditText) findViewById(R.id.usernameEditText);
        password =  (EditText) findViewById(R.id.passwordEditText2);


        Log.i("RPI", "courriel 1: " + email.getText().toString());
        Log.i("RPI", "mot de passe 1: " + password.getText().toString());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //**********DBUG********************************
                String courriel = "ArnaudFournier@dayrep.com";
                String motPasse = "weWoh9zie";
//                String login = "Enstives97";


                //**********************************************
                Log.i("RPI", "courriel 2: " + email.getText().toString());
                Log.i("RPI", "mot de passe 2: " + password.getText().toString());



                DatabaseHelper dbHelper =   DatabaseHelper.getInstance(getBaseContext());
             //   Utilisateur utilisateur =  dbHelper.validateLogin(email.getText().toString(), password.getText().toString());
                // petite securite
                Utilisateur utilisateur = null;
                try {
                    utilisateur = dbHelper.validateLogin(courriel, motPasse);
                }
                catch (android.database.sqlite.SQLiteException e){
                    Log.e("LOGIN", "erreur lors de la recherche du compte", e);
                    // demander si l'utilisateur veut continuer hors ligne
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Continuer hors-ligne?")
                        .setMessage("Une erreur est arrivée lors du login, voulez-vous continuez hors-ligne? Certaines fonctionnalités ne seront pas présentes.");

                    // si l'utilisateur veut continuer hors ligne
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getBaseContext(), CalendrierActivity.class);
                            startActivity(intent);
                        }
                    });

                    // si l'utilisateur ne veut pas
                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // on va laisser l'add. couriel intacte, mais supprimer le mdp avant de retourner a l'ecran login
                            password.setText("");
                        }
                    });

                    builder.create().show();
                    return;
                }

                Log.i("RPI", "utilisateur: " + utilisateur);
                if(utilisateur != null){
                    Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), CalendrierActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), "Adresse courriel/mot de passe non reconnus",Toast.LENGTH_SHORT).show();

                }

            }


        });

    }

    public void login(View view){
        Intent intent = new Intent(this, CalendrierActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void mdpOublie(View view){
        startActivity(new Intent(this, RecupererMdpActivity.class));
    }

    public void inscription(View view){
        startActivity(new Intent(this, InscriptionActivity.class));
    }
}
