package com.example.malladam.AppUsuarios.adapters;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malladam.AppUsuarios.models.CustomRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by malladam on 17/05/2016.
 */
public class VolleyS {
    private static VolleyS mVolleyS = null;
    private RequestQueue mRequestQueue;


    private VolleyS(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static VolleyS getInstance(Context context) {
        if (mVolleyS == null) {
            mVolleyS = new VolleyS(context);
        }
        return mVolleyS;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public void llamarWS(int metodo, String urlCreateUser, JSONObject jsonBody,  Response.Listener<JSONObject> response, Response.ErrorListener errorListener,final String token) throws TimeoutException, ExecutionException, InterruptedException {
        JsonObjectRequest request = new JsonObjectRequest(metodo,urlCreateUser,jsonBody, response, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                if (token != null){
                    headers.put("Authorization", token);
                }
                return headers;
            }
        };
        addToQueue(request);
    }

    public void llamarWSarray(int metodo, String urlCreateUser, JSONArray jsonBody, Response.Listener<JSONArray> response, Response.ErrorListener errorListener, final String token) throws TimeoutException, ExecutionException, InterruptedException {
        JsonArrayRequest request = new JsonArrayRequest(metodo,urlCreateUser,jsonBody, response, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                if (token != null){
                    headers.put("Authorization", token);
                }
                return headers;
            }
        };
        addToQueue(request);
    }


    public void llamarWString(int metodo, String url,final JSONObject jsonObject, Response.Listener<String> response, Response.ErrorListener errorListener) throws TimeoutException, ExecutionException, InterruptedException {
        StringRequest myReq = new StringRequest(metodo, url, response, errorListener) {

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            };

            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonObject.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        addToQueue(myReq);
    }


    public void addToQueue(Request request) {
        if (request != null) {
            request.setTag(this);
            if (mRequestQueue == null)
                mRequestQueue = this.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(request);
        }
    }


    public void llamarWSCustomArray(int metodo, String url, JSONObject jsonBody, Response.Listener<JSONArray> response, Response.ErrorListener errorListener, final String token) throws TimeoutException, ExecutionException, InterruptedException {

        CustomRequest request = new CustomRequest(metodo, url, jsonBody, response, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                if (token != null){
                    headers.put("Authorization", token);
                }
                return headers;
            }
        };
        addToQueue(request);
    }

}