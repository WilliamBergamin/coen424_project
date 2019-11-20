package com.example.roumeliotis.coen242projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


// Log in to your account
public class Login extends AppCompatActivity{

    EditText getEmail;
    EditText getPassword;
    Button getLogin;
    Button getSignup;
    ServerHelper serverHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        getEmail = findViewById(R.id.emailInput);
        getPassword = findViewById(R.id.passwordInput);
        getLogin = findViewById(R.id.loginButton);
        getSignup = findViewById(R.id.signup);
        serverHelper = new ServerHelper();

        getSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });

        getLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getEmail.equals("") || getPassword.equals("")){
                    // Error or empty field message
                }
                else{
                    // login in here (serverhelper.java)
                    startActivity(new Intent(Login.this, EventRegistration.class));
                }
            }
        });
    }
}
