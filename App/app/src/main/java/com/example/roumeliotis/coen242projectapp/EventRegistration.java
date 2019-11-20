package com.example.roumeliotis.coen242projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

// Sign in to an event, so that all orders are placed at event machines
public class EventRegistration extends AppCompatActivity {

    EditText getEventCode;
    Button getEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventreg_page);

        getEventCode = findViewById(R.id.eventCodeInput);
        getEventButton = findViewById(R.id.eventButton);

        getEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getEventCode.equals("")){
                    // Error or empty field message
                }
                else{
                    // Get event here
                    startActivity(new Intent(EventRegistration.this, CreateDrink.class));
                }
            }
        });
    }
}
