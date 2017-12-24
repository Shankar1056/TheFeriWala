package apextechies.theferiwala.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.interfaces.OnTaskCompleted;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.WebServices;

/**
 * Created by Shankar on 12/25/2017.
 */

public class MyOrder extends AppCompatActivity {

    private RecyclerView rv_myOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorder);
        initWidgit();
        callApi();
    }

    private void callApi() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(MyOrder.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                if (response.length()>0)
                {

                }

            }
        });
       // nameValuePairs.add(new BasicNameValuePair("userId", ClsGeneral.getPreferences(MyOrder.this, PreferenceHelper.ID)));
        nameValuePairs.add(new BasicNameValuePair("userId","63"));
        web.setData(nameValuePairs);
        web.setReqType("post");
        web.execute(WebServices.UPDATEMYORDER);
    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText("My Cart");
        rv_myOrder = (RecyclerView) findViewById(R.id.rv_myOrder);
        rv_myOrder.setLayoutManager(new LinearLayoutManager(MyOrder.this));
    }
}
