package com.sakshamnegi.yef.blooddonationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        firebaseAuth = FirebaseAuth.getInstance();

        //Navigation Drawer stuff
        //Set Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        }

        Button logoutButton =(Button) findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginScreenActivity.class);
                startActivity(intent);
            }
        });


    }
}

