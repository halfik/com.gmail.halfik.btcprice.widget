package com.gmail.halfik.btcprice.model;


import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class MarketData
{
    private static final String PREF_PRICE = "price";
    private static final String PREF_LOW = "low";
    private static final String PREF_HIGH = "high";

    public static final String PRICE_SEPARATOR = ";";

    public static String getStoredPrice(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_PRICE, "")
                ;
    }

    public static void putPrice(Context context, String price) {
        String[] prices = getStoredPrice(context).split(PRICE_SEPARATOR);
        String[] newPrices = new String[4];
        newPrices[0] = price;

        int max = prices.length;
        if (max > 3){
            max = 3;
        }
        for(int i=0; i<max; i++){
            newPrices[i+1] = prices[i];
        }

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_PRICE, TextUtils.join(PRICE_SEPARATOR, newPrices))
                .apply()
        ;
    }


    public static String getStoredLow(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LOW, "-")
                ;
    }

    public static void setLow(Context context, String price) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LOW, price)
                .apply()
        ;
    }


    public static String getStoredHigh(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_HIGH, "-")
                ;
    }

    public static void setHigh(Context context, String price) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_HIGH, price)
                .apply()
        ;
    }
}
