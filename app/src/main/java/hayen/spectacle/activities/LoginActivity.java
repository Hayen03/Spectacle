package hayen.spectacle.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hayen.spectacle.R;
import hayen.spectacle.data.dao.UtilisateurSQLHelper;
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



                UtilisateurSQLHelper dbHelper =   UtilisateurSQLHelper.getInstance(getBaseContext());
             //   Utilisateur utilisateur =  dbHelper.validateLogin(email.getText().toString(), password.getText().toString());
                  Utilisateur utilisateur =  dbHelper.validateLogin(courriel, motPasse);

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
        startActivity(intent);
    }

    public void mdpOublie(View view){
        startActivity(new Intent(this, RecupererMdpActivity.class));
    }

    public void inscription(View view){
        startActivity(new Intent(this, InscriptionActivity.class));
    }
}
