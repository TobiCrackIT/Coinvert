package com.example.oluwatobig.coinvert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConversionActivity extends AppCompatActivity {

    double btc_unit,eth_unit,btc_amount,eth_amount,btc_local_value,eth_local_value;
    private TextView btcTitle,ethTitle, local1TV, local2TV,destCurrTV1,destCurrTV2;
    private EditText btcET,ethET;
    private Button btcButton,ethButton;

    public static final String BTC_VALUE="Bitcoin Value";
    public static final String ETH_VALUE="Ethereum Value";
    public static final String BTC_EQUIV="Bitcoin Equivalent";
    public static final String ETH_EQUIV="Ethereum Equivalent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (savedInstanceState==null){
            btc_amount=0; eth_amount=0;
            btc_local_value=0;eth_local_value=0;
        }else{
            btc_amount=savedInstanceState.getDouble(BTC_VALUE,0);
            eth_amount=savedInstanceState.getDouble(ETH_VALUE,0);
            btc_local_value=savedInstanceState.getDouble(BTC_EQUIV,0);
            eth_local_value=savedInstanceState.getDouble(ETH_EQUIV,0);
        }

        Intent bundle=getIntent();
        String currName=bundle.getStringExtra(Constants.conversion_curr);
        btc_unit=bundle.getDoubleExtra(Constants.conversion_btc,0);
        eth_unit=bundle.getDoubleExtra(Constants.conversion_eth,0);

        btcTitle=(TextView)findViewById(R.id.btc_to_local);
        ethTitle=(TextView)findViewById(R.id.eth_to_local);
        btcET=(EditText)findViewById(R.id.btc_editText);
        ethET=(EditText)findViewById(R.id.eth_editText);
        local1TV =(TextView)findViewById(R.id.local1_textView);
        local2TV =(TextView)findViewById(R.id.local2_textView);
        destCurrTV1=(TextView)findViewById(R.id.destination_curr1);
        destCurrTV2=(TextView)findViewById(R.id.destination_curr2);
        btcButton=(Button)findViewById(R.id.btc_convert_button);
        ethButton=(Button)findViewById(R.id.eth_convert_button);

        btcTitle.setText("BTC - "+currName);
        ethTitle.setText("ETH - "+currName);
        destCurrTV1.setText(currName+"");
        destCurrTV2.setText(currName+"");

        btcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btcET.getText().toString().isEmpty()){
                    Toast.makeText(ConversionActivity.this, "Enter a valid amount of Bitcoin", Toast.LENGTH_SHORT).show();
                }else{
                    btc_amount=Double.parseDouble(btcET.getText().toString());
                    btc_local_value=convertBTC(btc_amount);
                    local1TV.setText(String.format("%1$,.2f",btc_local_value));
                }
            }
        });

        ethButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ethET.getText().toString().isEmpty()){
                    Toast.makeText(ConversionActivity.this, "Enter a valid amount of Ethereum", Toast.LENGTH_SHORT).show();
                }else{
                    eth_amount=Double.parseDouble(ethET.getText().toString());
                    eth_local_value=convertETH(eth_amount);
                    local2TV.setText(String.format("%1$,.2f",eth_local_value));
                }
            }
        });

    }

    public Double convertBTC(double nBTC){
        double local_Equivalent=nBTC*btc_unit;
        return local_Equivalent;
    }

    public Double convertETH(double nETH){
        double local_Equivalent=nETH*eth_unit;
        return local_Equivalent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(BTC_VALUE,btc_amount);
        outState.putDouble(BTC_EQUIV,btc_local_value);

        outState.putDouble(ETH_VALUE,eth_amount);
        outState.putDouble(ETH_EQUIV,eth_local_value);
    }
}
