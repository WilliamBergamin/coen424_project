package com.example.roumeliotis.coen242projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Sign in to an event, so that all orders are placed at event machines
public class EventRegistration extends AppCompatActivity {

    EditText getEventCode;
    Button getEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventreg_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("COEN424");

        getEventCode = findViewById(R.id.eventCodeInput);
        getEventButton = findViewById(R.id.eventButton);

        getEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event = getEventCode.getText().toString();
                if(event.equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(),"Invalid input",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else{
                    // Get event here
                    goToNextActivity();
                }
            }
        });
    }

    void goToNextActivity() {
        Intent intent = new Intent();
        intent.setClass(EventRegistration.this, CreateDrink.class);
        startActivity(intent);
    }
}
