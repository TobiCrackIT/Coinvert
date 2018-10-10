package com.example.oluwatobig.coinvert;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by OLUWATOBIG on 21/09/2018.
 */

//1 This adapter is for thr RV to hold CurrencyAdapter views
public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    private Context context;
    private List<CardItems> cardItemsList;

    public CurrencyAdapter(List<CardItems> cardItemsList, Context context) {
        this.cardItemsList=cardItemsList;
        this.context=context;
    }

    //2
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    //3
    @Override
    public int getItemCount() {
        return cardItemsList.size();
    }

    //4
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView currency,btc,eth;
        RelativeLayout cardRelativeLayout;

        public ViewHolder(View view) {
            super(view);
            currency=(TextView)view.findViewById(R.id.localCurrency_TV);
            btc=(TextView)view.findViewById(R.id.bitcoin_TV);
            eth=(TextView)view.findViewById(R.id.ethereum_TV);

            cardRelativeLayout =(RelativeLayout)view.findViewById(R.id.card_relative_layout);
        }
    }

    //5
    @Override
    public void onBindViewHolder(CurrencyAdapter.ViewHolder holder, int position) {
        CardItems cardItem=cardItemsList.get(position);
        final String currencyString=cardItem.getLocalCurrency();
        final double bitcoin_value=cardItem.getBtc_value();
        final double ethereum_value=cardItem.getEth_value();

        holder.currency.setText(currencyString);
        holder.btc.setText(String.format("%1$,.2f",bitcoin_value));
        holder.eth.setText(String.format("%1$,.2f",ethereum_value));

        holder.cardRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conversionIntent=new Intent(v.getContext(),ConversionActivity.class);
                conversionIntent.putExtra(Constants.conversion_curr,currencyString);
                conversionIntent.putExtra(Constants.conversion_btc,bitcoin_value);
                conversionIntent.putExtra(Constants.conversion_eth,ethereum_value);
                v.getContext().startActivity(conversionIntent);
            }
        });
    }

}
