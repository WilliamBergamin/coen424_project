package com.example.roumeliotis.coen242projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


// Log in to your account
public class Login extends AppCompatActivity{

    EditText getEmail;
    EditText getPassword;
    Button getLogin;
    Button getSignup;
    ServerHelper serverHelper;
    Manager Manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        getEmail = findViewById(R.id.emailInput);
        getPassword = findViewById(R.id.passwordInput);
        getLogin = findViewById(R.id.loginButton);
        getSignup = findViewById(R.id.signup);
        serverHelper = new ServerHelper();
        Manager = new Manager(this);

        getSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignup();
            }
        });

        getLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getEmail.getText().toString();
                String password = getPassword.getText().toString();
                if(email.equals("") || password.equals("")){
                    // Error or empty field message
                }else{
                    // login in here (serverhelper.java)
                    serverHelper.getToken(email, password, getApplicationContext(),  new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            try {
                                User loggedInUser = new User(-1,
                                                                response.getString("name"),
                                                                response.getString("email"),
                                                                "password",
                                                                response.getString("token"));
                                Manager.insertUser(loggedInUser);
                                goToNextActivity(loggedInUser);
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
                            textToast.setText("Invalid Email or Password");
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

    void goToSignup() {
        Intent intent = new Intent();
        intent.setClass(Login.this, Signup.class);
        startActivity(intent);
    }

    void goToNextActivity(User loggedInUser) {
        Intent intent = new Intent();
        intent.putExtra("loggerInUser", loggedInUser);
        intent.setClass(Login.this, EventRegistration.class);
        startActivity(intent);
    }
}
