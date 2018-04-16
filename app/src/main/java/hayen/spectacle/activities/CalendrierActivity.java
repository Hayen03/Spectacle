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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import hayen.spectacle.R;
import hayen.spectacle.data.dao.SpectacleSQLHelper;
import hayen.spectacle.data.data.Artiste;
import hayen.spectacle.data.data.Spectacle;
import hayen.spectacle.data.data.SpectacleAdapter;
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

    private String[] filmsPoliciersTitres = {
            "Kill Bill - vol 1",
            "Kill Bill - vol 2",
            "Otage",
            "Da Vinci Code",
            "36 Quai des Orfèvres",
            "Mystic River"

    };

    ListView listSpectacles;
    SpectacleAdapter spectacleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_calendrier);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        if (savedInstanceState == null)
//            currFrag = loadFragment(State.calendrier);
//
//        drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        NavigationView nav = findViewById(R.id.nav_view);
//        nav.setNavigationItemSelectedListener(this);


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

    //    final ArrayAdapter<String> adapterList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, filmsPoliciersTitres);


        setContentView(R.layout.activity_calendrier02);
        listSpectacles =  findViewById(R.id.listViewSpectacle);


        SpectacleSQLHelper spectacleSQLHelper =  SpectacleSQLHelper.getInstance(getBaseContext());


        final List<Spectacle>  spectacles =  spectacleSQLHelper.getAllSpectacles();



        if(spectacles != null && spectacles.size() > 0) {
            spectacleAdapter = new SpectacleAdapter(this, R.layout.ligne_spectacle);


            for (Spectacle spectacle : spectacles) {
                Log.i("RPI", "spectacle: " + spectacle);

                List<Artiste> artistes =  spectacleSQLHelper.getAllArtistesBySpectacleId(spectacle.getId());

                spectacle.setArtistes(artistes);

                spectacleAdapter.add(spectacle);

            }
        }

            listSpectacles.setAdapter(spectacleAdapter);

            listSpectacles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(CalendrierActivity.this, FicheSpectacleActivity.class);

                    Spectacle spectacle =  spectacles.get(i);

                    System.out.println("spectacle: " + spectacle);

                    Log.i("RPI", "CLick!");

                    Log.i("RPI", "Spectacle  id: " + spectacle.getId());

                    Toast toast = Toast.makeText(getApplicationContext(), "spectacle", Toast.LENGTH_LONG);
                    toast.show();

                    intent.putExtra("id", String.valueOf(spectacle.getId()));
                    startActivity(intent);
                }
            });

         //  final  ArrayAdapter<Spectacle> adapterList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, spectacles);



       //     ListView listViewSpectacle = (ListView) findViewById(R.id.listViewSpectacle);
//
   //         listViewSpectacle.setAdapter(adapterList);

   //         listViewSpectacle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    Log.i("ENI", "Position " + String.valueOf(position));
//                    String titre = adapterList.getItem(position).getTitre();
//                    Log.i("ENI", "Titre : " + titre);
//                }
//            });        }


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
