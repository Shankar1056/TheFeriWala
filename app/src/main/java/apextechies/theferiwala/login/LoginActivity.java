package apextechies.theferiwala.login;

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


/**
 * Created by Shankar on 12/19/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText input_email,input_password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initWidgit();
    }

    private void initWidgit() {
        input_email = (EditText)findViewById(R.id.input_email);
        input_password = (EditText)findViewById(R.id.input_password);
        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUp.class));
                finish();
            }
        });

        findViewById(R.id.text_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (input_email.getText().toString().trim().length()==0)
                {
                    Toast.makeText(LoginActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utility.isValidEmail1(input_email.getText().toString().trim()))
                {
                    Toast.makeText(LoginActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (input_password.getText().toString().trim().length()==0)
                {
                    Toast.makeText(LoginActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    doSignUp();
                }
            }
        });
    }

    private void doSignUp() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(LoginActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                if (response.length()>0)
                {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("true"))
                        {
                            JSONArray array = object.getJSONArray("data");
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject jo = array.getJSONObject(i);
                                String id = jo.optString("id");
                                String email = jo.optString("email");
                                String name = jo.optString("name");
                                String mobile = jo.optString("mobile");
                                ClsGeneral.setPreferences(LoginActivity.this, PreferenceHelper.ID,id);
                                ClsGeneral.setPreferences(LoginActivity.this, PreferenceHelper.NAME,name);
                                ClsGeneral.setPreferences(LoginActivity.this, PreferenceHelper.EMAIL,email);
                                ClsGeneral.setPreferences(LoginActivity.this, PreferenceHelper.MOBILE,mobile);
                            }
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();


                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, ""+object.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        nameValuePairs.add(new BasicNameValuePair("email", input_email.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("pswd", input_password.getText().toString().trim()));
        web.setData(nameValuePairs);
        web.setReqType("post");
        web.execute(WebServices.GETSIGNIN);
    }
}
