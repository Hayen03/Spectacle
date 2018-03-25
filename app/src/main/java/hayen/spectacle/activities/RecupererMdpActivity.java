package hayen.spectacle.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import hayen.spectacle.R;

public class RecupererMdpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperer_mdp);
    }

    public void send(View view){
        Intent intent = new Intent(this, NouveauMdpActivity.class);
        startActivity(intent);
    }

}
