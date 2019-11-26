package com.example.roumeliotis.coen242projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.app.ActionBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// View all of your orders to gain access to input code
public class Orders extends AppCompatActivity{

    ListView ordersList;
    ServerHelper serverHelper;
    Manager Manager;
    String eventKey;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_page);

        serverHelper = new ServerHelper();
        Manager = new Manager(this);
        Intent userInfo = getIntent();
        user = userInfo.getParcelableExtra("user");
        eventKey = userInfo.getStringExtra("eventKey");

        ordersList = findViewById(R.id.orderCodesList);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("COEN 424");

        List<Order> orders = Manager.getOrdersByUserID(user.getid());
        List<String> order_keys = new ArrayList<String>();
        for (int i = 0; i < orders.size(); i++) {
            order_keys.add(orders.get(i).getOrder_key());
        }

        ordersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, order_keys));
    }
}
