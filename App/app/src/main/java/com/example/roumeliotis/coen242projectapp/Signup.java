package com.example.roumeliotis.coen242projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;

// Create an account with personal and payment information
public class Signup extends AppCompatActivity{

    EditText getName;
    EditText getEmail;
    EditText getPassword;
    Button getSignup;
    Button getLogin;
    ServerHelper serverHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        getName = findViewById(R.id.nameInput);
        getEmail = findViewById(R.id.emailInput);
        getPassword = findViewById(R.id.passwordInput);
        getSignup = findViewById(R.id.signupButton);
        getLogin = findViewById(R.id.loginButton);


        getLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });

        getSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getName.equals("") || getEmail.equals("") || getPassword.equals("")){
                    // Error or empty field message
                }
                else{
                    // login in here (serverhelper.java)
                    startActivity(new Intent(Signup.this, EventRegistration.class));
                }
            }
        });
    }
}