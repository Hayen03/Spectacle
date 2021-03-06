package hayen.spectacle.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import hayen.spectacle.R;
import hayen.spectacle.data.data.Utilisateur;
import hayen.spectacle.fragments.CalendrierFragment;
import hayen.spectacle.fragments.InfoFragment;
import hayen.spectacle.fragments.OnFragmentInteractionListener;
import hayen.spectacle.fragments.ProfilFragment;
import hayen.spectacle.fragments.RechercheFragment;
import hayen.spectacle.fragments.ReservationFragment;
import hayen.spectacle.util.Constant;
import hayen.spectacle.util.Util;

public class CalendrierActivity
        extends     AppCompatActivity
        implements  OnFragmentInteractionListener,
                    NavigationView.OnNavigationItemSelectedListener
{

    private DrawerLayout drawer;

    private State fragState = null;
    private boolean override = false;
    private Fragment currFrag = null;

    private Utilisateur user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier);


        Log.i("RPI", "------------>> Calendrier Activity");

        // 1. initialiser l'UI
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null)
            currFrag = loadFragment(State.calendrier);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // 2. chercher l'utilisateur
        Bundle user_bundle = getIntent().getBundleExtra("user");


        if (user_bundle != null){
            user = new Utilisateur();
            user.setMotPasse(user_bundle.getString(Utilisateur.COLUMN_MOT_PASSE));
            user.setTelephone(user_bundle.getString(Utilisateur.COLUMN_TELEPHONE));
            user.setPrenom(user_bundle.getString(Utilisateur.COLUMN_PRENOM));
            user.setNom(user_bundle.getString(Utilisateur.COLUMN_NOM));
            user.setLogin(user_bundle.getString(Utilisateur.COLUMN_COURRIEL));
            user.setCourriel(user_bundle.getString(Utilisateur.COLUMN_COURRIEL));
            user.setId(user_bundle.getInt(Utilisateur.COLUMN_ID));
            user.setAdresseId(user_bundle.getInt(Utilisateur.COLUMN_ADRESSE_ID));
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) { // pour plus tard
            case R.id.nav_calendrier:
                loadFragment(State.calendrier);
                break;
            case R.id.nav_reservation:
                loadFragment(State.reservation);
                break;
            case R.id.nav_recherche:
                loadFragment(State.recherche);
                break;
            case R.id.nav_profil:
                loadFragment(State.profil);
                break;
            case R.id.nav_info:
                loadFragment(State.info);
                break;
            case R.id.nav_deconnexion:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default:
                break;
        }
        item.setChecked(true);
        drawer.closeDrawers();
        return true;
    }

    private Fragment loadFragment(State frag){
        clearBackStack();
        if (fragState != frag || override) {
            fragState = frag;
            return replaceFrag(frag.fragClass);
        }
        return currFrag;
    }

    private enum State {
        calendrier(CalendrierFragment.class),
        reservation(ReservationFragment.class),
        recherche(RechercheFragment.class),
        profil(ProfilFragment.class),
        info(InfoFragment.class);

        public final Class fragClass;
        State(Class fc){
            fragClass = fc;
        }
    }

    void nothing(){}

    public void restore(){
        if (override) {
            if (fragState != null)
                loadFragment(fragState);
            else
                loadFragment(State.calendrier);
            override = false;
        }
    }
    public Fragment overrideFragment(Class fragClass){
        if (!Fragment.class.isAssignableFrom(fragClass)){
            throw new IllegalArgumentException("fragClass doit etre une implementation de Fragment");
        }

        override = true;
        return replaceFrag(fragClass, true);
    }
    public Fragment overrideFragment(Fragment frag){
        override = true;
        return replaceFrag(frag, true);
    }
    private Fragment replaceFrag(Fragment frag, boolean backstack){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (backstack)
            transaction.addToBackStack(null);
        transaction.replace(R.id.fragcontent, frag).commit();
        return frag;
    }
    private Fragment replaceFrag(Class fragClass, boolean backstack){
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragClass.newInstance();
            nothing();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return replaceFrag(fragment, backstack);
    }
    private Fragment replaceFrag(Class fragClass){
        return replaceFrag(fragClass, false);
    }
    private void clearBackStack(){
        FragmentManager fm = getFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public Utilisateur getCurrentUser(){
        if (Constant.fightLaDB)
            return user;
        else
            return Utilisateur.bidon;
    }
}
