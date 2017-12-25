package apextechies.theferiwala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.adapter.MyOrderAdapter;
import apextechies.theferiwala.interfaces.ClickPosition;
import apextechies.theferiwala.interfaces.OnTaskCompleted;
import apextechies.theferiwala.model.MyOrderModel;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.Utility;
import apextechies.theferiwala.utilz.WebServices;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Shankar on 12/25/2017.
 */

public class MyOrder extends AppCompatActivity {

    private RecyclerView rv_myOrder;
    private ArrayList<MyOrderModel> list = new ArrayList<>();
    private ArrayList<MyOrderModel> modellist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorder);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initWidgit();
        callApi();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void callApi() {
        Utility.showDailog(MyOrder.this, getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(MyOrder.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utility.closeDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equals("true")) {
                            modellist.clear();
                            list.clear();

                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jo = array.getJSONObject(i);
                                String id = jo.optString("id");
                                String sellerid = jo.optString("sellerid");
                                String payment_mode = jo.optString("payment_mode");
                                String payment_status = jo.optString("payment_status");
                                String oid = jo.optString("oid");
                                String login_id = jo.optString("login_id");
                                String quantity = jo.optString("quantity");
                                String product_name = jo.optString("product_name");
                                String product_price = jo.optString("product_price");
                                String prod_image = jo.optString("prod_image");
                                String date = jo.optString("date");
                                String time = jo.optString("time");
                                String delivery_status = jo.optString("delivery_status");

                                modellist.add(new MyOrderModel(id, sellerid, payment_mode, payment_status, oid,  login_id, quantity,
                                        product_name, product_price, prod_image,date, time, delivery_status));

                                setAnotherList(id, sellerid, payment_mode, payment_status, oid,  login_id, quantity,
                                        product_name, product_price, prod_image, date, time, delivery_status);

                            }


                            MyOrderAdapter adapter = new MyOrderAdapter(MyOrder.this, list, new ClickPosition() {
                                @Override
                                public void pos(int position) {
                                    ArrayList<MyOrderModel> lst = new ArrayList<>();
                                    for (int i=0;i<modellist.size();i++)
                                    {
                                        if (modellist.get(i).getOid().equals(list.get(i).getOid()))
                                        {
                                            MyOrderModel a = modellist.get(i);
                                            lst.add(new MyOrderModel(a.getId(),a.getSellerid(),a.getPayment_mode(),a.getPayment_status(),a.getOid(),
                                                    a.getLogin_id(),a.getQuantity(),a.getProduct_name(),a.getProduct_price(),a.getProd_image() ,a.getDate(),
                                                    a.getTime(),a.getDelivery_status()));
                                        }
                                    }
                                    startActivity(new Intent(MyOrder.this, MyOrderDescription.class).putParcelableArrayListExtra("list", lst).putExtra("pos", position));
                                }

                                @Override
                                public void pos(int position, String name, int count) {

                                }
                            });

                            rv_myOrder.setAdapter(adapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        // nameValuePairs.add(new BasicNameValuePair("userId", ClsGeneral.getPreferences(MyOrder.this, PreferenceHelper.ID)));
        nameValuePairs.add(new BasicNameValuePair("userId", "63"));
        web.setData(nameValuePairs);
        web.setReqType("post");
        web.execute(WebServices.UPDATEMYORDER);
    }

    private void setAnotherList(String id, String sellerid, String payment_mode, String payment_status, String oid,  String login_id, String quantity, String product_name, String product_price, String prod_image, String date, String time, String delivery_status) {

        if (list.size() == 0) {
            list.add(new MyOrderModel(id, sellerid, payment_mode, payment_status, oid,  login_id, quantity,
                    product_name, product_price, prod_image,date, time, delivery_status));
        } else {
            for (int j = 0; j < list.size(); j++) {
                if (!list.get(j).getOid().equals(oid)) {
                    list.add(new MyOrderModel(id, sellerid, payment_mode, payment_status, oid,  login_id, quantity,
                            product_name, product_price, prod_image,date, time, delivery_status));
                }
            }

        }

    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText("My Order");
        rv_myOrder = (RecyclerView) findViewById(R.id.rv_myOrder);
        rv_myOrder.setLayoutManager(new LinearLayoutManager(MyOrder.this));
    }
}
