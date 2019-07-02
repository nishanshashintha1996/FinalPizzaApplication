package com.example.pizzaapp;


import android.Manifest;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CartViewActivity extends AppCompatActivity {
    private static String sts_now = "";
    Button checkout;
    LinearLayout visaDetailsLayout,paypalDetailsLayout,agreement,agreemntContentLayout;
    RecyclerView recyclerView;
    ProgressBar loading;
    RadioGroup btnGroup;
    RadioButton autoCheck,manualCheck,yesRadioButton,noRadioButton,visaMethord,paypalMethord;
    Geocoder geocoder;
    List<Address> addresses;
    EditText editTextAddress,editTextCity;
    TextView btnLableText,temsAndConditionsText;
    CartItemAdapter cartItemAdapter;
    List<CartItemClass> cartItemClassList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_item_view);
        geocoder = new Geocoder(this,Locale.getDefault());
        editTextAddress = findViewById(R.id.address);
        editTextCity = findViewById(R.id.city);
        temsAndConditionsText = findViewById(R.id.temsAndConditions);
        visaMethord = findViewById(R.id.visaRadioButton);
        visaDetailsLayout = findViewById(R.id.visaCardLayout);
        paypalDetailsLayout = findViewById(R.id.paypalLayout);
        paypalMethord = findViewById(R.id.paypalRadioButton);
        recyclerView = findViewById(R.id.cartRecyclerView);
        btnLableText = findViewById(R.id.btnRadioLableText);
        agreement = findViewById(R.id.layoutAgreement);
        agreemntContentLayout = findViewById(R.id.agreemntContent);
        agreemntContentLayout.setVisibility(View.GONE);
        checkout = findViewById(R.id.btnCheckout);
        checkout.setVisibility(View.GONE);
        agreement.setVisibility(View.GONE);
        btnLableText.setVisibility(View.GONE);
        loading = findViewById(R.id.loading);
        yesRadioButton = findViewById(R.id.giftRadioButtonYes);
        noRadioButton = findViewById(R.id.giftRadioButtonNo);
        loading.setVisibility(View.GONE);
        ActivityCompat.requestPermissions(CartViewActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        autoCheck = findViewById(R.id.autoCheck);
        manualCheck = findViewById(R.id.manualCheck);
        manualCheck.setChecked(true);
        btnGroup = findViewById(R.id.btnGroup);
        visaDetailsLayout.setVisibility(View.GONE);
        paypalDetailsLayout.setVisibility(View.GONE);
        yesRadioButton.setChecked(false);
        noRadioButton.setChecked(false);
        visaMethord.setChecked(false);
        paypalMethord.setChecked(false);
        temsAndConditionsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temsAndConditionsText.setText("1.UOR TERMS AND CONDITIONS\n" +
                        "PLEASE NOTE: We reserve the right, at our sole discretion, to change, modify or otherwise alter these Terms and Conditions at any time. Unless otherwise indicated, amendments will become effective immediately. Please review these Terms and Conditions periodically. Your continued use of the Site following the posting of changes and/or modifications will constitute your acceptance of the revised Terms and Conditions and the reasonableness of these standards for notice of changes. For your information, this page was last updated as of the date at the top of these terms and conditions.\n" +
                        "2. PRIVACY\n" +
                        "Please review our Privacy Policy, which also governs your visit to this Site, to understand our practices.\n" +
                        "\n" +
                        "3. LINKED SITES\n" +
                        "This Site may contain links to other independent third-party Web sites (\"Linked Sites”). These Linked Sites are provided solely as a convenience to our visitors. Such Linked Sites are not under our control, and we are not responsible for and does not endorse the content of such Linked Sites, including any information or materials contained on such Linked Sites. You will need to make your own independent judgment regarding your interaction with these Linked Sites.\n" +
                        "\n" +
                        "4. FORWARD LOOKING STATEMENTS\n" +
                        "All materials reproduced on this site speak as of the original date of publication or filing. The fact that a document is available on this site does not mean that the information contained in such document has not been modified or superseded by events or by a subsequent document or filing. We have no duty or policy to update any information or statements contained on this site and, therefore, such information or statements should not be relied upon as being current as of the date you access this site.\n" +
                        "\n" +
                        "5. DISCLAIMER OF WARRANTIES AND LIMITATION OF LIABILITY\n" +
                        "A. THIS SITE MAY CONTAIN INACCURACIES AND TYPOGRAPHICAL ERRORS. WE DOES NOT WARRANT THE ACCURACY OR COMPLETENESS OF THE MATERIALS OR THE RELIABILITY OF ANY ADVICE, OPINION, STATEMENT OR OTHER INFORMATION DISPLAYED OR DISTRIBUTED THROUGH THE SITE. YOU EXPRESSLY UNDERSTAND AND AGREE THAT: (i) YOUR USE OF THE SITE, INCLUDING ANY RELIANCE ON ANY SUCH OPINION, ADVICE, STATEMENT, MEMORANDUM, OR INFORMATION CONTAINED HEREIN, SHALL BE AT YOUR SOLE RISK; (ii) THE SITE IS PROVIDED ON AN \"AS IS\" AND \"AS AVAILABLE\" BASIS; (iii) EXCEPT AS EXPRESSLY PROVIDED HEREIN WE DISCLAIM ALL WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, WORKMANLIKE EFFORT, TITLE AND NON-INFRINGEMENT; (iv) WE MAKE NO WARRANTY WITH RESPECT TO THE RESULTS THAT MAY BE OBTAINED FROM THIS SITE, THE PRODUCTS OR SERVICES ADVERTISED OR OFFERED OR MERCHANTS INVOLVED; (v) ANY MATERIAL DOWNLOADED OR OTHERWISE OBTAINED THROUGH THE USE OF THE SITE IS DONE AT YOUR OWN DISCRETION AND RISK; and (vi) YOU WILL BE SOLELY RESPONSIBLE FOR ANY DAMAGE TO YOUR COMPUTER SYSTEM OR FOR ANY LOSS OF DATA THAT RESULTS FROM THE DOWNLOAD OF ANY SUCH MATERIAL.\n" +
                        "\n" +
                        "B. YOU UNDERSTAND AND AGREE THAT UNDER NO CIRCUMSTANCES, INCLUDING, BUT NOT LIMITED TO, NEGLIGENCE, SHALL WE BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, PUNITIVE OR CONSEQUENTIAL DAMAGES THAT RESULT FROM THE USE OF, OR THE INABILITY TO USE, ANY OF OUR SITES OR MATERIALS OR FUNCTIONS ON ANY SUCH SITE, EVEN IF WE HAVE BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. THE FOREGOING LIMITATIONS SHALL APPLY NOTWITHSTANDING ANY FAILURE OF ESSENTIAL PURPOSE OF ANY LIMITED REMEDY.");
            }
        });
        visaMethord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visaDetailsLayout.setVisibility(View.VISIBLE);
                paypalDetailsLayout.setVisibility(View.GONE);
                agreement.setVisibility(View.VISIBLE);
                checkout.setVisibility(View.VISIBLE);
                agreemntContentLayout.setVisibility(View.VISIBLE);
            }
        });
        paypalMethord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //paypalDetailsLayout.setVisibility(View.VISIBLE);
                visaDetailsLayout.setVisibility(View.GONE);
                agreement.setVisibility(View.VISIBLE);
                agreemntContentLayout.setVisibility(View.VISIBLE);
                checkout.setVisibility(View.VISIBLE);
            }
        });
        yesRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLableText.setVisibility(View.VISIBLE);
                btnLableText.setText("We prepare your gift!");
            }
        });
        noRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLableText.setVisibility(View.VISIBLE);
                btnLableText.setText("We keep your order!");
            }
        });
        manualCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAddress.setText("");
                editTextCity.setText("");
            }
        });
        loadCartItems();
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


    }
    private void loadCartItems() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://172.16.41.77:8080/system/getAllCartDetails",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Toast.makeText(CartViewActivity.this,response,Toast.LENGTH_LONG).show();
                        try {
                            JSONArray cartProducts = new JSONArray(response);
                            for (int i = 0; i < cartProducts.length(); i++) {
                                JSONObject cartProductObject = cartProducts.getJSONObject(i);
                                int cartId = cartProductObject.getInt("cartId");
                                int customerId = cartProductObject.getInt("customerId");
                                int itemId = cartProductObject.getInt("itemId");
                                int itemQuantity = cartProductObject.getInt("itemQuantity");
                                String itemStatus = cartProductObject.getString("itemStatus");
                                String itemLocation = cartProductObject.getString("itemLocation");
                                CartItemClass CartItemClass = new CartItemClass(cartId,customerId,itemId,itemQuantity,itemStatus,itemLocation);
                                //cartItemClassList.add(CartItemClass);
                            }
                            //cartItemAdapter = new CartItemAdapter(CartViewActivity.this, cartItemClassList);
                            //recyclerView.setAdapter(cartItemAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(CartViewActivity.this,"Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
