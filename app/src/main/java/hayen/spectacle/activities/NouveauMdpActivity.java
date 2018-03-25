package hayen.spectacle.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import hayen.spectacle.R;

public class NouveauMdpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_mdp);
    }

    public void toLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void resend(View view){}
}
