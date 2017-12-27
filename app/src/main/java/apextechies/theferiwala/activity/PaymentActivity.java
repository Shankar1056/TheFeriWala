package apextechies.theferiwala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import apextechies.theferiwala.R;
import apextechies.theferiwala.common.ClsGeneral;
import apextechies.theferiwala.common.PreferenceHelper;
import apextechies.theferiwala.interfaces.OnTaskCompleted;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.Utility;
import apextechies.theferiwala.utilz.WebServices;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Shankar on 12/26/2017.
 */

public class PaymentActivity extends AppCompatActivity {
    private String today_date, today_time;
    private String price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        price = getIntent().getStringExtra("price");
        initWidgit();
        getDateTime();
        placeOder();
    }

    private void placeOder() {
        findViewById(R.id.placeorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPayment();
            }
        });

    }

    private void doPayment() {

        Utility.showDailog(PaymentActivity.this, getResources().getString(R.string.pleasewait));
        final ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(PaymentActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utility.closeDialog();
                if (response.length() > 0) {

                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equals("true"))
                        {

                            String data = object.optString("data");
                            startActivity(new Intent(PaymentActivity.this, SuccessActivity.class).putExtra("data",data));
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("userId", ClsGeneral.getPreferences(PaymentActivity.this, PreferenceHelper.ID)));
        nameValuePairs.add(new BasicNameValuePair("paymentmethod", "COD"));
        nameValuePairs.add(new BasicNameValuePair("date", today_date));
        nameValuePairs.add(new BasicNameValuePair("time", today_time));
        web.setData(nameValuePairs);
        web.setReqType("post");
        web.execute(WebServices.PLACEORDER);


    }

    private void getDateTime() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        Date todayWithZeroDate = null;

        try {
            todayWithZeroDate = formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        today_date = "" + (todayWithZeroDate.getDate() + "/" + todayWithZeroDate.getMonth() + "/" + (1900 + todayWithZeroDate.getYear()));
        int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        if (hours > 12) {
            hours = hours - 12;
            today_time = "" + (hours + ":" + minute + " pm");
        } else {
            today_time = "" + (hours + ":" + minute + " am");
        }

    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText("Payment Screen");

        TextView totalpayable = (TextView)findViewById(R.id.totalpayable);
        TextView subtotal = (TextView)findViewById(R.id.subtotal);

        totalpayable.setText("₹"+price);
        subtotal.setText("₹"+price);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
