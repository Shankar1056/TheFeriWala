package apextechies.theferiwala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.common.ClsGeneral;
import apextechies.theferiwala.common.PreferenceHelper;
import apextechies.theferiwala.interfaces.OnTaskCompleted;
import apextechies.theferiwala.model.ProductByCatIdModel;
import apextechies.theferiwala.model.ViewAllSubCat;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.Utility;
import apextechies.theferiwala.utilz.WebServices;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Shankar on 11/24/2017.
 */

public class ProductDescription extends AppCompatActivity implements View.OnClickListener {
    private ImageView prodImage;
    private TextView productname, productdetails, amount, count, specification, desc, addtocarttext;
    private ArrayList<ViewAllSubCat> viewalllist = new ArrayList<>();
    private ArrayList<ProductByCatIdModel> mainlist = new ArrayList<>();
    private int position, totalcount;
    private String from;
    private LinearLayout addtocart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdescription);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initWidget();
        getdata();
        setdata();
        totalcount = getcount();
        getcartCount();
    }

    private void getcartCount() {
        Utility.showDailog(ProductDescription.this, getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(ProductDescription.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utility.closeDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equals("true")) {
                                String data = object.optString("data");
                                count.setText(data);
                            totalcount = Integer.parseInt(data);
                            addtocart.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            addtocart.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        if (from.equals("main")) {
            nameValuePairs.add(new BasicNameValuePair("productId", mainlist.get(position).getProduct_id()));
        } else if (from.equals("viewall")) {
            nameValuePairs.add(new BasicNameValuePair("productId", viewalllist.get(position).getProduct_id()));
        }
        nameValuePairs.add(new BasicNameValuePair("userId", ClsGeneral.getPreferences(ProductDescription.this, PreferenceHelper.ID)));
        web.setReqType("post");
        web.setData(nameValuePairs);
        web.execute(WebServices.GETCARTCOUNT);
    }

    private int getcount() {
        return Integer.parseInt(count.getText().toString().trim());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setdata() {
        if (from.equals("main")) {
            TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
            toolbartext.setText(mainlist.get(position).getPname());

            Picasso.with(ProductDescription.this).load(PreferenceHelper.IMAGE_URL + mainlist.get(position).getImage()).into(prodImage);
            productname.setText(mainlist.get(position).getPname());
            productdetails.setText(mainlist.get(position).getDetail());
            amount.setText(mainlist.get(position).getDis_price());
            specification.setText(mainlist.get(position).getSpecification());
            desc.setText(mainlist.get(position).getDetail());
        } else if (from.equals("viewall")) {
            TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
            toolbartext.setText(viewalllist.get(position).getPname());

            Picasso.with(ProductDescription.this).load(PreferenceHelper.IMAGE_URL + viewalllist.get(position).getImage()).into(prodImage);
            productname.setText(viewalllist.get(position).getPname());
            productdetails.setText(viewalllist.get(position).getDetail());
            amount.setText(viewalllist.get(position).getDis_price());
            specification.setText(viewalllist.get(position).getSpecification());
            desc.setText(viewalllist.get(position).getDetail());
        }

    }

    private void getdata() {
        from = getIntent().getStringExtra("from");
        if (from.equals("main")) {
            mainlist = getIntent().getParcelableArrayListExtra("list");
        } else if (from.equals("viewall")) {
            viewalllist = getIntent().getParcelableArrayListExtra("list");
        }
        position = getIntent().getIntExtra("pos", 0);
    }

    private void initWidget() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");

        prodImage = (ImageView) findViewById(R.id.prodImage);
        findViewById(R.id.plusbutton).setOnClickListener(this);
        findViewById(R.id.minusbutton).setOnClickListener(this);
        productname = (TextView) findViewById(R.id.productname);
        productdetails = (TextView) findViewById(R.id.productdetails);
        amount = (TextView) findViewById(R.id.amount);
        count = (TextView) findViewById(R.id.count);
        specification = (TextView) findViewById(R.id.specification);
        desc = (TextView) findViewById(R.id.desc);
        addtocarttext = (TextView) findViewById(R.id.addtocarttext);
        addtocart = (LinearLayout) findViewById(R.id.addtocart);
        addtocart.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.cartLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDescription.this, MyCart.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plusbutton:
                totalcount = totalcount + 1;
                setCountTexte(totalcount);
                break;

            case R.id.minusbutton:
                if (totalcount > 0) {
                    totalcount = totalcount - 1;
                    setCountTexte(totalcount);
                } else {

                }
                break;

            case R.id.addtocart:
                if (totalcount > 0) {
                    addCartApi();
                } else {
                    Toast.makeText(this, "Add item to your cart", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void setCountTexte(int totalcount) {
        count.setText("" + totalcount);
        if (totalcount > 0) {
            addtocart.setVisibility(View.VISIBLE);
        } else {
            addtocart.setVisibility(View.GONE);
        }

    }

    private void addCartApi() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(ProductDescription.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equals("true")) {
                            startActivity(new Intent(ProductDescription.this, MyCart.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        if (from.equals("main")) {
            nameValuePairs.add(new BasicNameValuePair("productId", mainlist.get(position).getProduct_id()));
            nameValuePairs.add(new BasicNameValuePair("productName", mainlist.get(position).getPname()));
            nameValuePairs.add(new BasicNameValuePair("productDetail", mainlist.get(position).getDetail()));
            nameValuePairs.add(new BasicNameValuePair("discountPrice", mainlist.get(position).getDis_price()));
            nameValuePairs.add(new BasicNameValuePair("specification", mainlist.get(position).getSpecification()));
            nameValuePairs.add(new BasicNameValuePair("detail", mainlist.get(position).getDetail()));
            nameValuePairs.add(new BasicNameValuePair("image", mainlist.get(position).getImage()));
        } else if (from.equals("viewall")) {
            nameValuePairs.add(new BasicNameValuePair("productId", viewalllist.get(position).getProduct_id()));
            nameValuePairs.add(new BasicNameValuePair("productName", viewalllist.get(position).getPname()));
            nameValuePairs.add(new BasicNameValuePair("productDetail", viewalllist.get(position).getDetail()));
            nameValuePairs.add(new BasicNameValuePair("discountPrice", viewalllist.get(position).getDis_price()));
            nameValuePairs.add(new BasicNameValuePair("specification", viewalllist.get(position).getSpecification()));
            nameValuePairs.add(new BasicNameValuePair("detail", viewalllist.get(position).getDetail()));
            nameValuePairs.add(new BasicNameValuePair("image", viewalllist.get(position).getImage()));
        }
        nameValuePairs.add(new BasicNameValuePair("cartCount", count.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("userId", ClsGeneral.getPreferences(ProductDescription.this, PreferenceHelper.ID)));
        web.setReqType("post");
        web.setData(nameValuePairs);
        web.execute(WebServices.ADDUPDATECART);
    }
}
