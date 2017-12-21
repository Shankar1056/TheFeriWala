package apextechies.theferiwala.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.activity.MainActivity;
import apextechies.theferiwala.common.ClsGeneral;
import apextechies.theferiwala.common.PreferenceHelper;
import apextechies.theferiwala.interfaces.OnTaskCompleted;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.Utility;
import apextechies.theferiwala.utilz.WebServices;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by Shankar on 12/19/2017.
 */

public class SignUp extends AppCompatActivity {

    private EditText input_name, input_email, input_password, input_phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        initWidgit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initWidgit() {
        input_name = (EditText) findViewById(R.id.input_name);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        input_phone = (EditText) findViewById(R.id.input_phone);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, LoginActivity.class));
                finish();
            }
        });

        findViewById(R.id.button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_name.getText().toString().trim().length() == 0) {
                    Toast.makeText(SignUp.this, "Enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (input_email.getText().toString().trim().length() == 0) {
                    Toast.makeText(SignUp.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utility.isValidEmail1(input_email.getText().toString().trim())) {
                    Toast.makeText(SignUp.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (input_password.getText().toString().trim().length() == 0) {
                    Toast.makeText(SignUp.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (input_phone.getText().toString().trim().length() == 0) {
                    Toast.makeText(SignUp.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (input_phone.getText().toString().trim().length() < 10) {
                    Toast.makeText(SignUp.this, "Enter 10 digits mobile number", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    doSignUp();
                }
            }
        });
    }

    private void doSignUp() {
        Utility.showDailog(SignUp.this, getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(SignUp.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utility.closeDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("true")) {
                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jo = array.getJSONObject(i);
                                String id = jo.optString("id");
                                String email = jo.optString("email");
                                String name = jo.optString("name");
                                String mobile = jo.optString("mobile");
                                ClsGeneral.setPreferences(SignUp.this, PreferenceHelper.ID, id);
                                ClsGeneral.setPreferences(SignUp.this, PreferenceHelper.NAME, name);
                                ClsGeneral.setPreferences(SignUp.this, PreferenceHelper.EMAIL, email);
                                ClsGeneral.setPreferences(SignUp.this, PreferenceHelper.MOBILE, mobile);
                            }
                            startActivity(new Intent(SignUp.this, MainActivity.class));
                            finish();


                        } else {
                            Toast.makeText(SignUp.this, "" + object.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        nameValuePairs.add(new BasicNameValuePair("name", input_name.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("email", input_email.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("pswd", input_password.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("mobile", input_phone.getText().toString().trim()));
        web.setData(nameValuePairs);
        web.setReqType("post");
        web.execute(WebServices.GETSIGNUP);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUp.this, LoginActivity.class));
        finish();
    }
}
