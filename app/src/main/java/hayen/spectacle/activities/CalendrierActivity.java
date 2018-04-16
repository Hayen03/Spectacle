package hayen.spectacle.activities;

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
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import hayen.spectacle.R;
import hayen.spectacle.fragments.CalendrierFragment;
import hayen.spectacle.fragments.InfoFragment;
import hayen.spectacle.fragments.ProfilFragment;
import hayen.spectacle.fragments.RechercheFragment;
import hayen.spectacle.fragments.ReservationFragment;

public class CalendrierActivity
        extends     AppCompatActivity
        implements  CalendrierFragment.OnFragmentInteractionListener,
                    InfoFragment.OnFragmentInteractionListener,
                    ProfilFragment.OnFragmentInteractionListener,
                    RechercheFragment.OnFragmentInteractionListener,
                    ReservationFragment.OnFragmentInteractionListener,
                    NavigationView.OnNavigationItemSelectedListener
{

    private DrawerLayout drawer;

    private State fragState = null;
    private Fragment currFrag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier);

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
        if (fragState != frag) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = frag.fragClass;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                nothing();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragcontent, fragment).commit();
            return fragment;
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
}
