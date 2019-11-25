package com.example.roumeliotis.coen242projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

// Create an account with personal and payment information
public class Signup extends AppCompatActivity{

    EditText getName;
    EditText getEmail;
    EditText getPassword;
    Button getSignup;
    Button getLogin;
    ServerHelper serverHelper;
    Manager Manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("COEN424");

        getName = findViewById(R.id.nameInput);
        getEmail = findViewById(R.id.emailInput);
        getPassword = findViewById(R.id.passwordInput);
        getSignup = findViewById(R.id.signupButton);
        getLogin = findViewById(R.id.loginButton);

        getLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });

        getSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getName.getText().toString();
                String email = getEmail.getText().toString();
                //TODO hash password
                String password = getPassword.getText().toString();
                if(name.equals("") || email.equals("") || password.equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(),"Invalid input",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else{
                    // sign up here (serverhelper.java)
                    serverHelper.postUser(name, email, password, getApplicationContext(),  new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            try {
                                User signedInUser = new User(-1,
                                        response.getString("name"),
                                        response.getString("email"),
                                        "password",
                                        response.getString("token"));
                                Manager.insertUser(signedInUser);
                                goToNextActivity(signedInUser);
                            } catch(JSONException e){
                                e.printStackTrace();
                                Toast toast=Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            LayoutInflater inflater = getLayoutInflater();
                            View layoutToast = inflater.inflate(R.layout.toast,
                                    (ViewGroup) findViewById(R.id.toast_layout));
                            TextView textToast = layoutToast.findViewById(R.id.toast_text);
                            textToast.setText("Invalid Name or Email or Password");
                            Toast toastWrongName = new Toast(getApplicationContext());
                            toastWrongName.setGravity(Gravity.CENTER, 0, 0);
                            toastWrongName.setDuration(Toast.LENGTH_SHORT);
                            toastWrongName.setView(layoutToast);
                            toastWrongName.show();
                        }
                    });
                }
            }
        });
    }

    void goToLogin() {
        Intent intent = new Intent();
        intent.setClass(Signup.this, Login.class);
        startActivity(intent);
    }

    void goToNextActivity(User signedInUser) {
        Intent intent = new Intent();
        intent.putExtra("signedInUser", signedInUser);
        intent.setClass(Signup.this, EventRegistration.class);
        startActivity(intent);
    }

    // Testing
    void goToNextActivity() {
        Intent intent = new Intent();
        intent.setClass(Signup.this, EventRegistration.class);
        startActivity(intent);
    }
}