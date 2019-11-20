package com.example.roumeliotis.coen242projectapp;

import android.util.Log;

import static com.android.volley.Request.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class ServerHelper {

    private static final String base_url = "http://3.15.172.12:80/";
    public static final String TAG = "ServerHelper";

    // User login
    public void getLogin(final String email, final String password, final VolleyCallback callback){
        final JsonObjectRequest request = new JsonObjectRequest(Method.GET, base_url + "api/v1/user/token/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                callback.onError(error);
            }
        });

        Log.d(TAG, "Request: " + request.toString());

        //Is this needed?
        // VolleySingleton.getInstance(context).addToQueue(request);
    }


    // Get user??


    // Create new user
    public void postUser(final String name, final String email, final String password, final VolleyCallback callback){
        final JsonObjectRequest request = new JsonObjectRequest(Method.POST, base_url + "api/v1/user", null, new Response.Listener<JSONObject>() {
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

        // Do I need this? From 390 we have singleton there
        // VolleySingleton.getInstance(context).addToQueue(request);
    }


    // Add User to Event
    public void postUserToEvent(final String eventCode, final VolleyCallback callback){
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

        // Do I need this? From 390 we have singleton there
        // VolleySingleton.getInstance(context).addToQueue(request);
    }


    // Create new order
    public void postNewOrder(final String eventKey, final String mixerTpe, final String alcoholType, final boolean doubleAlcohol, final VolleyCallback callback){
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

        // Do I need this? From 390 we have singleton there
        // VolleySingleton.getInstance(context).addToQueue(request);
    }

}