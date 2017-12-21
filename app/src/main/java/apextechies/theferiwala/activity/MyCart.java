package apextechies.theferiwala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.adapter.MyCartAdapter;
import apextechies.theferiwala.common.ClsGeneral;
import apextechies.theferiwala.common.PreferenceHelper;
import apextechies.theferiwala.interfaces.ClickPosition;
import apextechies.theferiwala.interfaces.OnTaskCompleted;
import apextechies.theferiwala.model.MyCartModel;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.Utility;
import apextechies.theferiwala.utilz.WebServices;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Shankar on 12/21/2017.
 */

public class MyCart extends AppCompatActivity {
    private RecyclerView rv_mycart;
    private ArrayList<MyCartModel> list = new ArrayList<>();
    private  MyCartAdapter myCartAdapter;
    private RelativeLayout botomlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycart);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        initWidgit();
        getMyCart();
    }

    private void getMyCart() {
        Utility.showDailog(MyCart.this, getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(MyCart.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utility.closeDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equals("true")) {
                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                String cart_id = jsonObject.optString("cart_id");
                                String productId = jsonObject.optString("productId");
                                String productName = jsonObject.optString("productName");
                                String productDetail = jsonObject.optString("productDetail");
                                String discountPrice = jsonObject.optString("discountPrice");
                                String specification = jsonObject.optString("specification");
                                String detail = jsonObject.optString("detail");
                                String cartCount = jsonObject.optString("cartCount");
                                String image = jsonObject.optString("image");
                                String userId = jsonObject.optString("userId");

                                list.add(new MyCartModel(cart_id, productId, productName, productDetail, discountPrice, specification, detail,
                                        cartCount, userId, image));
                            }

                             myCartAdapter = new MyCartAdapter(MyCart.this, list, new ClickPosition() {
                                @Override
                                public void pos(int position) {

                                }

                                @Override
                                public void pos(int position, String name, int count) {

                                    if (name.equals("update"))
                                    {
                                        addCartApi(position,count,false);
                                    }
                                    else if (name.equals("delete"))
                                    {

                                        addCartApi(position,count,true);
                                    }
                                }
                            });
                            rv_mycart.setAdapter(myCartAdapter);
                            if (list.size()>0)
                            {
                                botomlayout.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                botomlayout.setVisibility(View.GONE);
                            }
                            setTotalPrice(list);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        nameValuePairs.add(new BasicNameValuePair("userId", ClsGeneral.getPreferences(MyCart.this, PreferenceHelper.ID)));
        web.setData(nameValuePairs);
        web.setReqType("post");
        web.execute(WebServices.GETCART);
    }

    private void setTotalPrice(ArrayList<MyCartModel> list) {
        int price =0 ;
        for (int i=0;i<list.size();i++)
        {
            if (price==0)
            {
                price = getPrice(list.get(i).getDiscountPrice(),list.get(i).getCartCount());
            }
            else
            {
                price = price + getPrice(list.get(i).getDiscountPrice(),list.get(i).getCartCount());
            }
        }

        TextView amount = (TextView)findViewById(R.id.amount);
        amount.setText(""+price);
    }

    private int getPrice(String discountPrice, String cartCount) {

        int price, count;
        price = Integer.parseInt(discountPrice);
        count = Integer.parseInt(cartCount);
        return (price*count);
    }


    private void addCartApi(final int position, int count, final boolean s) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(MyCart.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equals("true")) {
                            if (s){
                                list.remove(position);
                            }

                           myCartAdapter.notifyDataSetChanged();

                            setTotalPrice(list);

                           if (list.size()>0)
                           {
                               botomlayout.setVisibility(View.VISIBLE);
                           }
                           else
                           {
                               botomlayout.setVisibility(View.GONE);
                           }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
            nameValuePairs.add(new BasicNameValuePair("productId", list.get(position).getProductId()));
            nameValuePairs.add(new BasicNameValuePair("productName", list.get(position).getProductName()));
            nameValuePairs.add(new BasicNameValuePair("productDetail", list.get(position).getDetail()));
            nameValuePairs.add(new BasicNameValuePair("discountPrice", list.get(position).getDiscountPrice()));
            nameValuePairs.add(new BasicNameValuePair("specification", list.get(position).getSpecification()));
            nameValuePairs.add(new BasicNameValuePair("detail", list.get(position).getDetail()));
            nameValuePairs.add(new BasicNameValuePair("image", list.get(position).getImage()));
        nameValuePairs.add(new BasicNameValuePair("cartCount", ""+count));
        nameValuePairs.add(new BasicNameValuePair("userId", ClsGeneral.getPreferences(MyCart.this, PreferenceHelper.ID)));
        web.setReqType("post");
        web.setData(nameValuePairs);
        web.execute(WebServices.ADDUPDATECART);
    }


    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText("My Cart");
        rv_mycart = (RecyclerView) findViewById(R.id.rv_mycart);
        rv_mycart.setLayoutManager(new LinearLayoutManager(MyCart.this));
        botomlayout = (RelativeLayout) findViewById(R.id.botomlayout);
        findViewById(R.id.proceedtopay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCart.this, AddAddress.class));
            }
        });

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
