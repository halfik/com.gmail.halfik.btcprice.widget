package com.gmail.halfik.btcprice.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.gmail.halfik.btcprice.R;
import com.gmail.halfik.btcprice.model.MarketData;
import com.gmail.halfik.btcprice.service.PollService;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link BtcPriceWidgetConfigureActivity BtcPriceWidgetConfigureActivity}
 */
public class BtcPriceWidget extends AppWidgetProvider
{
    private final static String TAG = "BitmarketWidget";
    public final static String TOGGLE_POLLSERVICE = "PollService";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        PollService.setServiceAlarm(context, true);

        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.btc_price_widget);

            updateAppWidget(context, appWidgetManager, appWidgetId);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }


    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i(TAG, "onReceive action: " + intent.getAction());
        if(intent.getAction().equals(TOGGLE_POLLSERVICE)) {

            RemoteViews views = setPrices(context);

            // Push update for this widget to the home screen
            ComponentName thisWidget = new ComponentName(context, BtcPriceWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            manager.updateAppWidget(thisWidget, views);
        }
        super.onReceive(context, intent);
    }


    @Override
    public void onEnabled(Context context) {
        PollService.setServiceAlarm(context, true);
    }

    @Override
    public void onDisabled(Context context) {
        if (PollService.isServiceAlarmOn(context)){
            PollService.setServiceAlarm(context, false);
        }

    }

    private static RemoteViews  setPrices(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.btc_price_widget);

        String[] number =  MarketData.getStoredPrice(context).split(MarketData.PRICE_SEPARATOR);;
        String highNumber =  MarketData.getStoredHigh(context);
        String lowNumber =  MarketData.getStoredLow(context);


        String[] price = number[0].split("\\.");
        String[] high = highNumber.split("\\.");
        String[] low = lowNumber.split("\\.");

        int color =  context.getResources().getColor(R.color.white);
        if (number.length > 2 && !number[1].isEmpty()){
            if ( Float.valueOf(number[0]) > Float.valueOf(number[1]) ){
                color = context.getResources().getColor(R.color.green1);

                if (number.length > 3 && !number[2].isEmpty() && Float.valueOf(number[1]) > Float.valueOf(number[2])){
                    color = context.getResources().getColor(R.color.green2);
                }
            }
            else if( Float.valueOf(number[0]) < Float.valueOf(number[1]) ){
                color = context.getResources().getColor(R.color.red1);

                if (number.length >= 3 && !number[2].isEmpty() && Float.valueOf(number[1]) < Float.valueOf(number[2])){
                    color = context.getResources().getColor(R.color.red2);
                }
            }
        }


        views.setTextColor(R.id.bitmarket_price, color);

        if (price[0].isEmpty()){
            price[0] = "-";
        }

        views.setTextViewText(R.id.bitmarket_price, price[0] + " zł");
        views.setTextViewText(R.id.bitmarket_high, high[0] + " zł");
        views.setTextViewText(R.id.bitmarket_low, low[0] + " zł");

        return views;
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object

        RemoteViews views = setPrices(context);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

