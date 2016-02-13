package com.gmail.halfik.btcprice.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.danielnilsson9.colorpickerview.dialog.ColorPickerDialogFragment;
import com.gmail.halfik.btcprice.R;
import com.gmail.halfik.btcprice.model.DataStorage;
import com.gmail.halfik.btcprice.service.PollService;

import org.w3c.dom.Text;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The configuration screen for the {@link BtcPriceWidget BtcPriceWidget} AppWidget.
 */
public class BtcPriceWidgetConfigureActivity extends Activity
    implements ColorPickerDialogFragment.ColorPickerDialogListener
{
    private final static String TAG = "configureActivity";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    Spinner mSpinnerTime;
    Spinner mSpinnerFontSize;
    HashMap<Integer, String> mTimeItems = new HashMap<Integer, String>();
    HashMap<Integer, String> mFontSizeItems = new HashMap<Integer, String>();

    TextView mPriceUpPrimaryColor;
    TextView mPriceUpSecondaryColor;
    TextView mPriceDownPrimaryColor;
    TextView mPriceDownSecondaryColor;
    TextView mDefaultFontColor;

    public BtcPriceWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        final Context mContext = this;

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.btc_price_widget_configure);
        mSpinnerTime = (Spinner) findViewById(R.id.time_spinner);
        mSpinnerFontSize = (Spinner) findViewById(R.id.font_size_spinner);

        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        mDefaultFontColor  = (TextView) findViewById(R.id.defaultFontColor);
        mPriceUpPrimaryColor = (TextView) findViewById(R.id.priceUpPrimaryColor);
        mPriceUpSecondaryColor = (TextView) findViewById(R.id.priceUpSecondaryColor);
        mPriceDownPrimaryColor = (TextView) findViewById(R.id.priceDownPrimaryColor);
        mPriceDownSecondaryColor = (TextView) findViewById(R.id.priceDownSecondaryColor);

        mDefaultFontColor.setBackgroundColor(DataStorage.getStoredDefaultFontColor(mContext));
        mDefaultFontColor.setOnClickListener(new Button.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        android.app.FragmentManager manager = getFragmentManager();
                                                        FragmentTransaction ft = manager.beginTransaction();
                                                        Fragment prev = manager.findFragmentByTag("color");
                                                        if (prev != null) {
                                                            ft.remove(prev);
                                                        }

                                                        // Create and show the dialog.
                                                        DialogFragment newFragment = ColorPickerDialogFragment.newInstance(
                                                                5, null, null, DataStorage.getStoredDefaultFontColor(mContext), true
                                                        );
                                                        newFragment.show(ft, "color");
                                                    }
                                                }
        );

        mPriceUpPrimaryColor.setBackgroundColor(DataStorage.getStoredPriceUpPrimaryColor(mContext));
        mPriceUpPrimaryColor.setOnClickListener(new Button.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        android.app.FragmentManager manager = getFragmentManager();
                                                        FragmentTransaction ft = manager.beginTransaction();
                                                        Fragment prev = manager.findFragmentByTag("color");
                                                        if (prev != null) {
                                                            ft.remove(prev);
                                                        }

                                                        // Create and show the dialog.
                                                        DialogFragment newFragment = ColorPickerDialogFragment.newInstance(
                                                                1, null, null, DataStorage.getStoredPriceUpPrimaryColor(mContext), true
                                                        );
                                                        newFragment.show(ft, "color");
                                                    }
                                                }
        );

        mPriceUpSecondaryColor.setBackgroundColor(DataStorage.getStoredPriceUpSecondaryColor(mContext));
        mPriceUpSecondaryColor.setOnClickListener(new Button.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          android.app.FragmentManager manager = getFragmentManager();
                                                          FragmentTransaction ft = manager.beginTransaction();
                                                          Fragment prev = manager.findFragmentByTag("color");
                                                          if (prev != null) {
                                                              ft.remove(prev);
                                                          }

                                                          // Create and show the dialog.
                                                          DialogFragment newFragment = ColorPickerDialogFragment.newInstance(
                                                                  2, null, null, DataStorage.getStoredPriceUpSecondaryColor(mContext), true
                                                          );
                                                          newFragment.show(ft, "color");
                                                      }
                                                  }
        );

        mPriceDownPrimaryColor.setBackgroundColor(DataStorage.getStoredPriceDownPrimaryColor(mContext));
        mPriceDownPrimaryColor.setOnClickListener(new Button.OnClickListener(){
                                                      @Override
                                                      public void onClick(View v) {
                                                          android.app.FragmentManager manager = getFragmentManager();
                                                          FragmentTransaction ft = manager.beginTransaction();
                                                          Fragment prev = manager.findFragmentByTag("color");
                                                          if (prev != null) {
                                                              ft.remove(prev);
                                                          }

                                                          // Create and show the dialog.
                                                          DialogFragment newFragment = ColorPickerDialogFragment.newInstance(
                                                                  3,  null, null, DataStorage.getStoredPriceDownPrimaryColor(mContext), true
                                                          );
                                                          newFragment.show(ft, "color");
                                                      }
                                                  }
        );

        mPriceDownSecondaryColor.setBackgroundColor(DataStorage.getStoredPriceDownSecondaryColor(mContext));
        mPriceDownSecondaryColor.setOnClickListener(new Button.OnClickListener(){
                                                      @Override
                                                      public void onClick(View v) {
                                                          android.app.FragmentManager manager = getFragmentManager();
                                                          FragmentTransaction ft = manager.beginTransaction();
                                                          Fragment prev = manager.findFragmentByTag("color");
                                                          if (prev != null) {
                                                              ft.remove(prev);
                                                          }

                                                          // Create and show the dialog.
                                                          DialogFragment newFragment = ColorPickerDialogFragment.newInstance(
                                                                  4,  null, null, DataStorage.getStoredPriceDownSecondaryColor(mContext), true
                                                          );
                                                          newFragment.show(ft, "color");
                                                      }
                                                  }
        );

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

        mFontSizeItems.put(8, getResources().getString(R.string.font_small));
        mFontSizeItems.put(10, getResources().getString(R.string.font_average));
        mFontSizeItems.put(15, getResources().getString(R.string.font_big));


        String[] items = new String[]{"5min", "15min", "30min", "60min"};
        String[] fontSizes = mFontSizeItems.values().toArray(new String[0]);


        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ArrayAdapter<String> fontSizeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fontSizes);

        mSpinnerTime.setAdapter(timeAdapter);
        mSpinnerFontSize.setAdapter(fontSizeAdapter);

        mSpinnerFontSize.setSelection(1);
        mSpinnerTime.setSelection(2);
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

            String selectedFontSizeValue = mSpinnerFontSize.getSelectedItem().toString();
            Integer fontSize = 10;

            for (Map.Entry<Integer, String> entry : mFontSizeItems.entrySet()) {
                Integer key = entry.getKey();
                String value = entry.getValue();

                if (value.equals(selectedFontSizeValue)){
                    fontSize = key;
                }
            }

            editor.putInt(PollService.SETTING_SERVICE_TIME, selectedTime);
            editor.commit();

            DataStorage.setFontSize(context, fontSize);

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


    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case 1:
                mPriceUpPrimaryColor.setBackgroundColor(color);
                DataStorage.setPriceUpPrimaryColor(this, color);
                break;
            case 2:
                mPriceUpSecondaryColor.setBackgroundColor(color);
                DataStorage.setPriceUpSecondaryyColor(this, color);
                break;
            case 3:
                mPriceDownPrimaryColor.setBackgroundColor(color);
                DataStorage.setPriceDownPrimaryColor(this, color);
                break;
            case 4:
                mPriceDownSecondaryColor.setBackgroundColor(color);
                DataStorage.setPriceDownSecondaryColor(this, color);
                break;
            case 5:
                mDefaultFontColor.setBackgroundColor(color);
                DataStorage.setDefaultFontColor(this, color);
                break;
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}

