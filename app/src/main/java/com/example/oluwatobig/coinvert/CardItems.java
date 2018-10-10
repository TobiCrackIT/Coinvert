package com.example.oluwatobig.coinvert;

/**
 * Created by OLUWATOBIG on 21/09/2018.
 */

public class CardItems {
    String localCurrency;
    double eth_value,btc_value;

    public CardItems(String localCurrency, double btc_value, double eth_value ) {
        this.localCurrency = localCurrency;
        this.eth_value = eth_value;
        this.btc_value = btc_value;
    }

    public String getLocalCurrency() {
        return localCurrency;
    }

    public double getEth_value() {
        return eth_value;
    }

    public double getBtc_value() {
        return btc_value;
    }
}
