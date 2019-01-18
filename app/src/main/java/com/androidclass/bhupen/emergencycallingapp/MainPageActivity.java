package com.androidclass.bhupen.emergencycallingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREFERENCE_ID = "UserData";
    public static final String Name = "Name";
    public static final String Email = "Email";
    public static final String SignedIn = "SignedIn";

    public static final String phoneNumber = "Number";
    public static final String Addressname = "Address";
    public static final String LocSet = "LocationSet";


    SharedPreferences sharedPreferences;

    TextView textViewName;
    TextView textViewEmail;

    FragmentManager fragmentManager;

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference ref;


    String userUID;

    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userUID = user.getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child((userUID));

        sharedPreferences = getSharedPreferences(PREFERENCE_ID, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putBoolean(LocSet, false);
        editor.commit();


        Log.i("set up auth", "set up auth");

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                Log.i("Debugging stuff", "getting datat");
                User post = dataSnapshot.getValue(User.class);
                System.out.println(post);
                Log.i("Debugging stuff", post.getEmail());

                editor.putString(Name, post.getFirstName());
                editor.putString(Email, post.getEmail());
                editor.putString(phoneNumber, post.getEmergencyContact());
                editor.putString(Addressname, post.getHomeAddress());

                editor.commit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Debugging stuff", "data failed");
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.myloc);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with find my location and other actions", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        View headerView = navigationView.getHeaderView(0);




        textViewName = (TextView) headerView.findViewById(R.id.userName);
        textViewEmail = (TextView) headerView.findViewById(R.id.userEmail);

        textViewName.setText(sharedPreferences.getString(Name, "Your Name"));
        textViewEmail.setText(sharedPreferences.getString(Email, "Your Email"));


        EmergencyMapFragment emergencyMapFragment = new EmergencyMapFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_map_layout, emergencyMapFragment).commit();



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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_map) {
            EmergencyMapFragment emergencyMapFragment = new EmergencyMapFragment();

            fragmentManager.beginTransaction().replace(R.id.content_map_layout, emergencyMapFragment).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lost) {
            // Handle the camera action

            LostFragment lostFragment = new LostFragment();

            fragmentManager.beginTransaction().replace(R.id.content_map_layout, lostFragment).commit();


        } else if (id == R.id.nav_accident) {

            AccidentFragment accidentFragment = new AccidentFragment();

            fragmentManager.beginTransaction().replace(R.id.content_map_layout, accidentFragment).commit();

        } else if (id == R.id.nav_naturaldisaster) {

            DisasterFragment disasterFragment = new DisasterFragment();

            fragmentManager.beginTransaction().replace(R.id.content_map_layout, disasterFragment).commit();

        } else if (id == R.id.nav_drunk) {

            DrunkFragment drunkFragment = new DrunkFragment();

            fragmentManager.beginTransaction().replace(R.id.content_map_layout, drunkFragment).commit();

        } else if (id == R.id.nav_accountedit) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_logout) {


            auth.signOut();
            startActivity(new Intent(MainPageActivity.this, LoginActivity.class));
            //    finish();
      /*      FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) {
                        startActivity(new Intent(MainPageActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            };
            */



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}