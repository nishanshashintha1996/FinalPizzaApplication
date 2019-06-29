package com.example.pizzaapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

public class SingleViewActivity extends AppCompatActivity {
    private static String sts_now ="";
    private static int exId;
    private static String location;
    private static int checkout_item_count = 0;
    public int countItem = 1;
    private static double cartTotal = 0;
    private static double DouPrice = 0;
    private static double newDouPrice = 0;
    Button plus,minus ,addtoCart ,checkout_btn;
    ImageView imageView;
    TextView count , title, description ,rating , status , price ,items_count , warning;
    private static final String TAG = "SingleViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_view);
        addtoCart = findViewById(R.id.addtocart);
        count = findViewById(R.id.count);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        items_count = findViewById(R.id.itemCount);
        checkout_btn = findViewById(R.id.btn_visit_cart);
        warning = findViewById(R.id.warning);

        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CartViewActivity.class);
                startActivity(i);
            }
        });
        if(checkout_item_count == 0 || checkout_item_count == 5){
            if(checkout_item_count ==0){
                warning.setText("Cart Empty !");
            }else{
                warning.setText("Cart Full !");
            }
        }else{
            warning.setText("");
        }
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sta = 1;
                ActiveCount(sta);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sta = 0;
                ActiveCount(sta);
            }
        });
        imageView = findViewById(R.id.image);
        Intent intent = getIntent();
        exId = intent.getIntExtra("id",0);
        //Toast.makeText(this,""+exId,Toast.LENGTH_LONG).show();
        String exTitle =intent.getStringExtra("title");
        String exDescription =intent.getStringExtra("description" );
        String exRating =intent.getStringExtra("rating");
        String exStatus =intent.getStringExtra("status");
        String exImage =intent.getStringExtra("image");
        String exPrice =intent.getStringExtra("price");
        sts_now = intent.getStringExtra("sts");
        DouPrice = intent.getDoubleExtra("DouPrice",DouPrice);
        location = intent.getStringExtra("location");

        items_count.setText("Items:"+checkout_item_count);
        title = findViewById(R.id.title);
        rating = findViewById(R.id.Rating);
        description = findViewById(R.id.description_text);
        price = findViewById(R.id.Price);
        status = findViewById(R.id.Status);
        title.setText(exDescription);
        rating.setText(exRating);
        price.setText("Price : "+DouPrice+"0 LKR");
        newDouPrice = +DouPrice;
        status.setText(exStatus);
        description.setText(exTitle);
        loadImage(exImage);
    }

    public void addtocart(View view) {
        addToCartDone(exId,countItem,location);
    }

    private void addToCartDone(int itemId, int itemQuantity ,String location){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://"+UserIdSession.getIpAdress()+":8080/system/addToCart?customerId="+UserIdSession.getUsId()+"&itemId="+itemId+"&itemQuantity="+itemQuantity+"&itemLocation="+location+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SingleViewActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SingleViewActivity.this,"Error :"+error,Toast.LENGTH_LONG).show();
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SingleViewActivity.this);
                        builder.setTitle("Warning!")
                                .setMessage("Server connection error").setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SingleViewActivity.super.onBackPressed();
                                    }
                                }).create().show();

            }
        });
        Volley.newRequestQueue(SingleViewActivity.this).add(stringRequest);
    }



    private void loadImage(String exImage) {

        Picasso.with(this).load(exImage).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

    }

    public void ActiveCount(int sta){
        if(sta==1){
            countItem ++;
            newDouPrice+=DouPrice;
            price.setText("Price : "+newDouPrice+"0 LKR");
            count.setText(String.valueOf(countItem));
        }else{
            if(countItem!=1){
                countItem--;
                newDouPrice-=DouPrice;
                price.setText("Price : "+newDouPrice+"0 LKR");
                count.setText(String.valueOf(countItem));
            }
        }
    }

    public void onBackPressed() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Warning!")
                .setMessage("Do you want add more?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cartTotal+=newDouPrice;
                        newDouPrice = 0;
                        SingleViewActivity.super.onBackPressed();

                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create().show();


    }

    public void logout(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void open_settings(View view) {
        Toast.makeText(SingleViewActivity.this,"This button have some errors",Toast.LENGTH_LONG).show();
        /*Button btn_settings;
        dialog.setContentView(R.layout.settings_popup_for_after_login);
        btn_settings = dialog.findViewById(R.id.settings_btn);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ShowPopup();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();*/
    }


    /*public void ShowPopup() {
        TextView txtclose;Button popUpIpGet;
        final EditText ipAddressGet;
        dialog.setContentView(R.layout.activity_ippopup);
        txtclose =(TextView) dialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        popUpIpGet = dialog.findViewById(R.id.getIpAdrss);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ipAddressGet = dialog.findViewById(R.id.ipadress);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        popUpIpGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserIdSession.setIpAdress(String.valueOf(ipAddressGet.getText()));
                if(UserIdSession.getIpAdress().equals(String.valueOf(ipAddressGet.getText()))){
                    Toast.makeText(SingleViewActivity.this,"Ip successfully added!",Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }*/
}
