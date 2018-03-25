package hayen.spectacle.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import hayen.spectacle.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
