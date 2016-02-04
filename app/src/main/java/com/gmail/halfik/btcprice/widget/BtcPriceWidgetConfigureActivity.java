package com.gmail.halfik.btcprice.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.gmail.halfik.btcprice.R;
import com.gmail.halfik.btcprice.service.PollService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The configuration screen for the {@link BtcPriceWidget BtcPriceWidget} AppWidget.
 */
public class BtcPriceWidgetConfigureActivity extends Activity
{
    private final static String TAG = "configureActivity";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    Spinner mSpinnerTime;
    HashMap<Integer, String> mTimeItems = new HashMap<Integer, String>();

    public BtcPriceWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.btc_price_widget_configure);
        mSpinnerTime = (Spinner) findViewById(R.id.time_spinner);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        mTimeItems.put(5, "5min");
        mTimeItems.put(15, "15min");
        mTimeItems.put(30, "30min");
        mTimeItems.put(60, "60min");


        String[] items = new String[]{"5min", "15min", "30min", "60min"};



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        mSpinnerTime.setAdapter(adapter);

    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = BtcPriceWidgetConfigureActivity.this;

            SharedPreferences settings = getSharedPreferences(PollService.PREF_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();


            String selectedTimeValue = mSpinnerTime.getSelectedItem().toString();
            Integer selectedTime = 5;

            for (Map.Entry<Integer, String> entry : mTimeItems.entrySet()) {
                Integer key = entry.getKey();
                String value = entry.getValue();

                if (value.equals(selectedTimeValue)){
                    selectedTime = key;
                }
            }

            editor.putInt(PollService.SETTING_SERVICE_TIME, selectedTime);
            editor.commit();

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            BtcPriceWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();

            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);

            Intent intent = new Intent(context,BtcPriceWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
            // since it seems the onUpdate() is only fired on that:
            int[] ids = {mAppWidgetId};
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
            sendBroadcast(intent);

            finish();
        }
    };


}

