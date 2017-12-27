package apextechies.theferiwala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
 * Created by Shankar on 12/21/2017.
 */

public class AddAddress extends AppCompatActivity {
    private EditText input_address,input_state,input_city,input_pincode;
    private String price;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        initWidgit();
        getData();
    }

    private void getData() {
        try {
            price = getIntent().getStringExtra("price");
        }
        catch (Exception e)
        {

        }
    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText("Add Address");

        input_address = (EditText)findViewById(R.id.input_address);
        input_state = (EditText)findViewById(R.id.input_state);
        input_city = (EditText)findViewById(R.id.input_city);
        input_pincode = (EditText)findViewById(R.id.input_pincode);

        findViewById(R.id.button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSession();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void checkSession() {
        if (input_address.getText().toString().trim().equals(""))
        {
            Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (input_city.getText().toString().trim().equals(""))
        {
            Toast.makeText(this, "Enter City", Toast.LENGTH_SHORT).show();
            return;
        }
        if (input_state.getText().toString().trim().equals(""))
        {
            Toast.makeText(this, "Enter State", Toast.LENGTH_SHORT).show();
            return;
        }
        if (input_pincode.getText().toString().trim().equals(""))
        {
            Toast.makeText(this, "Enter Pincode", Toast.LENGTH_SHORT).show();
            return;
        }

        else
        {
            saveAddress();
        }
    }

    private void saveAddress() {
        Utility.showDailog(AddAddress.this,getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(AddAddress.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utility.closeDialog();

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optString("status").equals("true"))
                    {
                       // Toast.makeText(AddAddress.this, ""+object.optString("msg"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddAddress.this,PaymentActivity.class).putExtra("price",price));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("address",input_address.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("state",input_state.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("city",input_city.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("zip",input_pincode.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("userId", ClsGeneral.getPreferences(AddAddress.this, PreferenceHelper.ID)));
        web.setData(nameValuePairs);
        web.setReqType("post");
        web.execute(WebServices.UPDATEADDRESS);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
