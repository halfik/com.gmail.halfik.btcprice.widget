package com.gmail.halfik.btcprice.model;


import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.gmail.halfik.btcprice.R;

public class DataStorage
{
    private static final String PREF_PRICE = "price";
    private static final String PREF_LOW = "low";
    private static final String PREF_HIGH = "high";
    private static final String FONT_SIZE = "fontSize";
    private static final String DEFAULT_FONT_COLOR = "defaultFontColor";
    private static final String UP_COLOR_1 = "upColor1";
    private static final String UP_COLOR_2 = "upColor2";
    private static final String DOWN_COLOR_1 = "DownColor1";
    private static final String DOWN_COLOR_2 = "DownColor2";

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

    //Price Up Primary Color
    public static void setPriceUpPrimaryColor(Context context, int color){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(UP_COLOR_1, color)
                .apply()
        ;
    }
    public static int getStoredPriceUpPrimaryColor(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(UP_COLOR_1, context.getResources().getColor(R.color.green1))
                ;
    }

    //Price Up Secondary Color
    public static void setPriceUpSecondaryyColor(Context context, int color){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(UP_COLOR_2, color)
                .apply()
        ;
    }
    public static int getStoredPriceUpSecondaryColor(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(UP_COLOR_2, context.getResources().getColor(R.color.green2))
                ;
    }


    //Price Down Primary Color
    public static void setPriceDownPrimaryColor(Context context, int color){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(DOWN_COLOR_1, color)
                .apply()
        ;
    }
    public static int getStoredPriceDownPrimaryColor(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(DOWN_COLOR_1, context.getResources().getColor(R.color.red1))
                ;
    }

    //Price Down Secondary Color
    public static void setPriceDownSecondaryColor(Context context, int color){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(DOWN_COLOR_2, color)
                .apply()
        ;
    }
    public static int getStoredPriceDownSecondaryColor(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(DOWN_COLOR_2, context.getResources().getColor(R.color.red2))
                ;
    }

    //Font color
    public static void setDefaultFontColor(Context context, int size){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(DEFAULT_FONT_COLOR, size)
                .apply()
        ;
    }
    public static int getStoredDefaultFontColor(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(DEFAULT_FONT_COLOR, context.getResources().getColor(R.color.white))
                ;
    }

    //Font size
    public static void setFontSize(Context context, int size){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(FONT_SIZE, size)
                .apply()
        ;
    }
    public static int getStoredFontSize(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(FONT_SIZE, 10)
                ;
    }
}
