package com.example.roumeliotis.coen242projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

// View all drinks in the cart and place the order/pay
public class Cart extends AppCompatActivity{

    Button payNowButton;
    ListView itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);

        payNowButton = findViewById(R.id.payNowButton);
        itemsList = findViewById(R.id.itemsInCartList);

        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemsList.getCount() <= 0){
                    // Error or empty field message
                }
                else{
                    // Pay now
                    startActivity(new Intent(Cart.this, Orders.class));
                }
            }
        });
    }
}
