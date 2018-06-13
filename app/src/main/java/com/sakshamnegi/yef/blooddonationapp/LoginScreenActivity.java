package com.sakshamnegi.yef.blooddonationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreenActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        //Check if user is already logged in
        if(firebaseAuth.getCurrentUser()!= null)
        {
            //Start LoggedIn activity
            finish();
            Intent intent=new Intent(getApplicationContext(),LoggedInActivity.class);
            startActivity(intent);
        }


        //Circular login icon
        ImageView imageView = (ImageView) findViewById(R.id.login_icon);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.login);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);

        //On click listener for login
        CardView loginButton = (CardView) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                userLogin();
            }
        });


        //On click listener for Register
        TextView register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    private void userLogin(){

        EditText loginEmail = (EditText) findViewById(R.id.login_username);
        String  emailString = loginEmail.getText().toString().trim();

        EditText loginPassword = (EditText) findViewById(R.id.login_password);
        String  passwordString = loginPassword.getText().toString();

        if(emailString.equalsIgnoreCase(""))
        {
            loginEmail.setError("Email/Phone required!");//it gives user to info message //use any one //
            return;

        }
        else if(passwordString.equalsIgnoreCase("") ) {
            loginPassword.setError("Password required!");
            return;
        }
        //Email and passwords entered

        progressDialog.setMessage("Logging In");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    //Logged in
                    //Start LoggedIn activity
                    finish();
                    Intent intent=new Intent(getApplicationContext(),LoggedInActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
