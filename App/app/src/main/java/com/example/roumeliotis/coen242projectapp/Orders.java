package com.example.roumeliotis.coen242projectapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.support.v7.app.ActionBar;

// View all of your orders to gain access to input code
public class Orders extends AppCompatActivity{

    ListView ordersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_page);

        ordersList = findViewById(R.id.orderCodesList);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("COEN 424");



    }
}
