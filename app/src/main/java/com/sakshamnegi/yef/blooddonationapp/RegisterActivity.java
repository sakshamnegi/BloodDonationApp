package com.sakshamnegi.yef.blooddonationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        //On click listener for Register button
        CardView registerCardview = (CardView) findViewById(R.id.register_button);
        registerCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void registerUser() {
        //Name field
        EditText registerName = (EditText) findViewById(R.id.register_name);
        final String nameString = registerName.getText().toString().trim();

        //Email/Phone field
        EditText registerEmail = (EditText) findViewById(R.id.register_email);
        final String emailString = registerEmail.getText().toString().trim();

        //Password field
        EditText registerPassword = (EditText) findViewById(R.id.register_password);
        String passwordString = registerPassword.getText().toString();

        //Confirm password field
        EditText confirmRegisterPassword = (EditText) findViewById(R.id.register_confirm_password);
        String confirmPasswordString = confirmRegisterPassword.getText().toString();

        //City String
        EditText city = (EditText) findViewById(R.id.city);
        final String cityString = city.getText().toString().trim();

        if (nameString.equalsIgnoreCase("")) {
            registerName.setError("Email/Phone required!");
            return;

        } else if (emailString.equalsIgnoreCase("")) {
            registerEmail.setError("Email/Phone required!");
            return;

        } else if (passwordString.equalsIgnoreCase("") || passwordString.length()< 6) {
            registerPassword.setError("Password must be atleast 6 characters long");
            return;
        } else if (!passwordString.equals(confirmPasswordString)) {
            registerPassword.setError("Passwords don't match!");
            return;
        }
        else if(cityString.equalsIgnoreCase("")){
            city.setError("City required!");
            return;
        }
        progressDialog.setMessage("Registering...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Registered successfully
                    //Prompt to login
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Registered Successfully. You can Login now", Toast.LENGTH_LONG).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef = database.getReference("Email");
                    UserData userData = new UserData(emailString, nameString, cityString);
                    myRef.setValue(userData);
                    finish();
                    Intent intent = new Intent(getApplicationContext(), LoginScreenActivity.class);
                    startActivity(intent);




                }

                //Check if already exists



                else{
                    //Failed to register
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Problem registering.. Try again.", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}

