package com.example.roumeliotis.coen242projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.app.ActionBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

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

//        serverHelper.getUser(user.getToken(), getApplicationContext(),  new VolleyCallback() {
//            @Override
//            public void onSuccess(JSONObject response) {
//                try {
//
//                } catch(JSONException e){
//                    e.printStackTrace();
//                    Toast toast=Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                }
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//                LayoutInflater inflater = getLayoutInflater();
//                View layoutToast = inflater.inflate(R.layout.toast,
//                        (ViewGroup) findViewById(R.id.toast_layout));
//                TextView textToast = layoutToast.findViewById(R.id.toast_text);
//                textToast.setText("Order not completed");
//                Toast toastWrongName = new Toast(getApplicationContext());
//                toastWrongName.setGravity(Gravity.CENTER, 0, 0);
//                toastWrongName.setDuration(Toast.LENGTH_SHORT);
//                toastWrongName.setView(layoutToast);
//                toastWrongName.show();
//            }
//        });

    }
}
