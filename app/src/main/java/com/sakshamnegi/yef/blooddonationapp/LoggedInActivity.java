package com.sakshamnegi.yef.blooddonationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoggedInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    //TOOLBAR OVERLAY CODE
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        firebaseAuth = FirebaseAuth.getInstance();

        //Navigation Drawer stuff
        //Set Navigation Drawer


        // TOOLBAR OVERLAY CODE
        mToolbar = (Toolbar) findViewById(R.id.navigation_overlay_toolbar) ;
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }
                else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
//SOME CODE TO OPEN OR CLOSE DRAWER
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        //If user not logged in
        //Go to login screen
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null)
        {
            Intent intent = new Intent(getApplicationContext(), LoginScreenActivity.class);
            startActivity(intent);
        }
        else {
            TextView userEmailText = (TextView) findViewById(R.id.logged_in_textview);
            userEmailText.setText("Logged in as: " + user.getEmail());
            //Display Current username
            getSupportActionBar().setTitle(user.getEmail());

        }


        //Listeners for navigation menu items
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.nav_new_request){
                    //Add new Request here
                    Toast.makeText(LoggedInActivity.this,"Pressed 1",Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("New Request");

                }
                if(id == R.id.nav_show_requests){
                    //Display requests here
                    Toast.makeText(LoggedInActivity.this,"Pressed 2",Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Requests");

                }
                if(id == R.id.nav_logout){
                    firebaseAuth.signOut();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), LoginScreenActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoggedInActivity.this,"Logged Out Successfully",Toast.LENGTH_SHORT).show();

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });



    }
}

