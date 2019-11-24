package com.example.roumeliotis.coen242projectapp;

import android.content.Context;
import android.util.Log;

import static com.android.volley.Request.*;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServerHelper {

    private static final String base_url = "http://3.15.172.12:80/";
    public static final String TAG = "ServerHelper";

    // User login
    public void getToken(final String email, final String password, final Context context, final VolleyCallback callback){
        final JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("email", email);
            //TODO hash password
            jsonRequest.put("password", password);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        final JsonObjectRequest request = new JsonObjectRequest(Method.POST, base_url + "api/v1/user/token", jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "error caught:"+ error.toString());
                callback.onError(error);
            }
        }){ //no semicolon or coma this is to add headers
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        Log.d(TAG, "Request: " + request.toString());

        VolleySingleton.getInstance(context).addToQueue(request);
    }



    // Create new user
    public void postUser(final String name, final String email, final String password, final Context context, final VolleyCallback callback){
        final JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("name", name);
            jsonRequest.put("email", email);
            //TODO hash password
            jsonRequest.put("password", password);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Method.POST, base_url + "api/v1/user", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }
        , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "error caught:"+ error.toString());
                callback.onError(error);
            }
        });

        Log.d(TAG, "request: " + request.toString());

        VolleySingleton.getInstance(context).addToQueue(request);
    }


    // Add User to Event
    public void postUserToEvent(final String eventCode, final Context context, final VolleyCallback callback){
        final JsonObjectRequest request = new JsonObjectRequest(Method.POST, base_url + "api/v1/user/event/" + eventCode, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });

        Log.d(TAG, "request: " + request.toString());

        VolleySingleton.getInstance(context).addToQueue(request);
    }


    // Create new order
    public void postNewOrder(final String eventKey, final String mixerTpe, final String alcoholType, final boolean doubleAlcohol, final Context context, final VolleyCallback callback){
        final JsonObjectRequest request = new JsonObjectRequest(Method.POST, base_url + "api/v1/order", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });

        Log.d(TAG, "request: " + request.toString());

        VolleySingleton.getInstance(context).addToQueue(request);
    }

}