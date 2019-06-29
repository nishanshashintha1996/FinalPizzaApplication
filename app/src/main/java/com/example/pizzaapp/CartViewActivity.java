package com.example.pizzaapp;


import android.Manifest;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CartViewActivity extends AppCompatActivity {
    private static String sts_now = "";
    RecyclerView recyclerView;
    ProgressBar loading;
    RadioGroup btnGroup;
    RadioButton autoCheck,manualCheck;
    Geocoder geocoder;
    List<Address> addresses;
    EditText editTextAddress,editTextCity;
    CartItemAdapter cartItemAdapter;
    List<CartItemClass> cartItemClassList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_item_view);
        geocoder = new Geocoder(this,Locale.getDefault());
        editTextAddress = findViewById(R.id.address);
        editTextCity = findViewById(R.id.city);
        recyclerView = findViewById(R.id.cartRecyclerView);
        loading = findViewById(R.id.loading);
        loading.setVisibility(View.GONE);
        ActivityCompat.requestPermissions(CartViewActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        autoCheck = findViewById(R.id.autoCheck);
        manualCheck = findViewById(R.id.manualCheck);
        manualCheck.setChecked(true);
        btnGroup = findViewById(R.id.btnGroup);
        manualCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAddress.setText("");
                editTextCity.setText("");
            }
        });

        autoCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGroup.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                Location location = gpsTracker.getLocation();
                if(location != null){
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    try {
                        addresses = geocoder.getFromLocation(lat, lon, 1);
                        String address = addresses.get(0).getAddressLine(0);
                        editTextAddress.setText(address);
                        String city = addresses.get(0).getLocality();
                        editTextCity.setText(city);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Error: "+e,Toast.LENGTH_LONG).show();
                    }
                    loading.setVisibility(View.GONE);
                    btnGroup.setVisibility(View.VISIBLE);
                }
                else{
                    loading.setVisibility(View.GONE);
                    btnGroup.setVisibility(View.VISIBLE);
                    manualCheck.setChecked(true);
                }
            }
        });
        loadCartItems();
    }
    private void loadCartItems() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.43.66:8080/system/getAllProducts",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray products = new JSONArray(response);
                            for (int i = 0; i < products.length(); i++) {
                                JSONObject productObject = products.getJSONObject(i);
                                int id = productObject.getInt("hotPizzaId");
                                String title = productObject.getString("title");
                                String shortDescription = productObject.getString("shortdesc");
                                double price = productObject.getDouble("price");
                                double rating = productObject.getDouble("rating");
                                String image = productObject.getString("image");
                                String status = productObject.getString("status");
                                CartItemClass cartItemClass = new CartItemClass(id, title, shortDescription, rating, price, image, status, sts_now);
                                cartItemClassList.add(cartItemClass);
                            }
                            cartItemAdapter = new CartItemAdapter(CartViewActivity.this, cartItemClassList);
                            recyclerView.setAdapter(cartItemAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CartViewActivity.this);
                builder.setTitle("Warning!")
                        .setMessage("Server connection error").setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CartViewActivity.super.onBackPressed();
                            }
                        }).create().show();

            }
        });
        Volley.newRequestQueue(CartViewActivity.this).add(stringRequest);
    }

    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Warning!")
                .setMessage("Do you want to logout ?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CartViewActivity.super.onBackPressed();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create().show();
    }

}
