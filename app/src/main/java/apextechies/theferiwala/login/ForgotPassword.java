package apextechies.theferiwala.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.interfaces.OnTaskCompleted;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.Utility;
import apextechies.theferiwala.utilz.WebServices;

/**
 * Created by Shankar on 2/14/2018.
 */

public class ForgotPassword extends AppCompatActivity {
    private LinearLayout cartLayout;
    private EditText input_email,input_password,confirm_password;
    private Button text_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        initWidgit();
        clicklistener();
    }

    private void clicklistener() {
        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_email.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(ForgotPassword.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utility.isValidEmail1(input_email.getText().toString())){
                    Toast.makeText(ForgotPassword.this, "Enter correct Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (input_password.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(ForgotPassword.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirm_password.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(ForgotPassword.this, "Confirm password please", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!confirm_password.getText().toString().equalsIgnoreCase(input_password.getText().toString())){
                    Toast.makeText(ForgotPassword.this, "Your password & and confirm password not correct", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    resetPassword();
                }

            }
        });
    }

    private void resetPassword() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(ForgotPassword.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                if (response.length()>0){
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equalsIgnoreCase("true")){
                            Toast.makeText(ForgotPassword.this, "Updated", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
        nameValuePairs.add(new BasicNameValuePair("email", input_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", input_password.getText().toString()));
        web.setData(nameValuePairs);
        web.setReqType("post");
        web.execute(WebServices.FOROTPASSWORD);
    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText("Reset Password");
        cartLayout = (LinearLayout)findViewById(R.id.cartLayout);
        cartLayout.setVisibility(View.INVISIBLE);

        input_email = (EditText)findViewById(R.id.input_email);
        input_password = (EditText)findViewById(R.id.input_password);
        confirm_password = (EditText)findViewById(R.id.confirm_password);
        text_login = (Button)findViewById(R.id.text_login);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
