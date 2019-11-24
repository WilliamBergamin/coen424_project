package com.example.roumeliotis.coen242projectapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.os.Bundle;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.CheckBox;
import java.util.ArrayList;
import java.util.List;

// Create a custom drink and add it to your cart
public abstract class CreateDrink extends AppCompatActivity implements OnItemSelectedListener{
        //Activity implements OnItemSelectedListener {

    private Spinner spinnerAlcohol;
    private Spinner spinnerMixer;
    Button payNowButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createdrink_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("COEN 424");

        // Alcohol dropdown menu
        spinnerAlcohol = (Spinner) findViewById(R.id.alcoholDropdown);
        spinnerAlcohol.setOnItemSelectedListener(this);
        List<String> list = new ArrayList<String>();
        list.add("None");
        list.add("Rum");
        list.add("Vodka");
        list.add("Gin");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlcohol.setAdapter(adapter);

        // Mixer dropdown menu
        spinnerMixer = (Spinner) findViewById(R.id.mixerDropdown);
        spinnerMixer.setOnItemSelectedListener(this);
        List<String> city = new ArrayList<String>();
        city.add("None");
        city.add("Orange juice");
        city.add("Apple juice");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, city);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMixer.setAdapter(adapter2);

        // See if user selected double drink
        final CheckBox checkBox = (CheckBox) findViewById(R.id.doubleCheckbox);
        if (checkBox.isChecked()) {
            // Perform operation for double here

        }

        // Create the official drink and add the order to the cart
        payNowButton = findViewById(R.id.payNowButton);
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerAlcohol.equals("") || spinnerMixer.equals("")){
                    // Error message here for empty field
                }
                else{
                    // Add order to the cart
                    goToNextActivity();
                }
            }
        });
    }

    void goToNextActivity() {
        Intent intent = new Intent();
        intent.setClass(CreateDrink.this, Orders.class);
        startActivity(intent);
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        // TODO Auto-generated method stub
//        Toast.makeText(this, "YOUR SELECTION IS : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//        // TODO Auto-generated method stub
//
//    }


}