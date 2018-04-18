package hayen.spectacle.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import hayen.spectacle.R;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.util.Constant;
import hayen.spectacle.util.Util;

public class RecupererMdpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperer_mdp);
    }

    public void send(View view){
        // 1. Recuperer l'email
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();

        // 2. tester si un utilisateur existe
        if (Constant.fightLaDB){
            if (DatabaseHelper.getInstance(this).getUtilisateurByLogin(email) == null){
                Util.alert(this, R.string.err_titre, R.string.err_no_user, null);
                return;
            }
        }

        // "Envoyer un code de recuperation"
        // ==> '1234567'
        Intent intent = new Intent(this, NouveauMdpActivity.class);
        startActivity(intent);
    }

}
