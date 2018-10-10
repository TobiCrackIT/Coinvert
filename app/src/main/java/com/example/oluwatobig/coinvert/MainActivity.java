package com.example.oluwatobig.coinvert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView localTV,btcTV,ethTV,emptyTV;
    private ImageView emptyIV;
    private View lineV;
    private LinearLayout titleLL;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CardItems> cardItemsList;
    private ProgressBar progressBar;

    private static  final String BASE_URL="https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&" +
            "tsyms=NGN,USD,EUR,JPY,GBP,AUD,CAD,CHF,CNY,KES,GHS,UGX,ZAR,XAF,NZD,MYR,BND,GEL,RUB,INR";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineV=findViewById(R.id.lineView);
        titleLL=(LinearLayout)findViewById(R.id.titleLL);
        localTV=(TextView)findViewById(R.id.localCurrency_TV);
        btcTV=(TextView)findViewById(R.id.bitcoin_TV);
        ethTV=(TextView)findViewById(R.id.ethereum_TV);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardItemsList=new ArrayList<>();

        //Loads data from api
        loadWebData();



        adapter=new CurrencyAdapter(cardItemsList,this);
        recyclerView.setAdapter(adapter);

    }

    private void loadWebData(){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, BASE_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject btc_value = response.getJSONObject("BTC".trim());
                            JSONObject eth_value = response.getJSONObject("ETH".trim());

                            Iterator<?> btc_keys = btc_value.keys();
                            Iterator<?> eth_keys = eth_value.keys();

                            while (btc_keys.hasNext() && eth_keys.hasNext()) {
                                String btc_key = (String) btc_keys.next();
                                String eth_key = (String) eth_keys.next();

                                CardItems displayCardItem = new CardItems(btc_key, btc_value.getDouble(btc_key), eth_value.getDouble(eth_key));
                                cardItemsList.add(displayCardItem);
                            }

                            adapter = new CurrencyAdapter(cardItemsList, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            progressBar=(ProgressBar)findViewById(R.id.loadingBar);
                            progressBar.setVisibility(View.INVISIBLE);
                            lineV.setVisibility(View.VISIBLE);
                            titleLL.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MainActivity.this,"ERROR: "+error.toString(),Toast.LENGTH_SHORT).show();
                emptyTV=(TextView)findViewById(R.id.empty_list);
                emptyIV=(ImageView)findViewById(R.id.emptyIV);
                emptyTV.setVisibility(View.VISIBLE);
                emptyIV.setVisibility(View.VISIBLE);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    //JsonObjectRequest js=new JsonObjectRequest(Request.Method.GET)
}
