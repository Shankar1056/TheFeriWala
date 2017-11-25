package apextechies.theferiwala.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.common.PreferenceHelper;
import apextechies.theferiwala.model.ProductByCatIdModel;
import apextechies.theferiwala.model.ViewAllSubCat;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Shankar on 11/24/2017.
 */

public class ProductDescription extends AppCompatActivity {
    private ImageView prodImage,plusbutton, minusbutton;
    private TextView productname, productdetails,amount,count,specification,desc,addtocarttext;
    private LinearLayout addtocart;
    private ArrayList<ViewAllSubCat> viewalllist = new ArrayList<>();
    private ArrayList<ProductByCatIdModel> mainlist = new ArrayList<>();
    private int position;
    private String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdescription);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Celias.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initWidget();
        getdata();
        setdata();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    private void setdata() {
        if (from.equals("main"))
        {
            TextView toolbartext = (TextView)findViewById(R.id.toolbartext);
            toolbartext.setText(mainlist.get(position).getPname());

            Picasso.with(ProductDescription.this).load(PreferenceHelper.IMAGE_URL+mainlist.get(position).getImage()).into(prodImage);
            productname.setText(mainlist.get(position).getPname());
            productdetails.setText(mainlist.get(position).getDetail());
            amount.setText(mainlist.get(position).getDis_price());
            specification.setText(mainlist.get(position).getSpecification());
            desc.setText(mainlist.get(position).getDetail());
        }
        else if (from.equals("viewall")) {
            TextView toolbartext = (TextView)findViewById(R.id.toolbartext);
            toolbartext.setText(viewalllist.get(position).getPname());

            Picasso.with(ProductDescription.this).load(PreferenceHelper.IMAGE_URL+viewalllist.get(position).getImage()).into(prodImage);
            productname.setText(viewalllist.get(position).getPname());
            productdetails.setText(viewalllist.get(position).getDetail());
            amount.setText(viewalllist.get(position).getDis_price());
            specification.setText(viewalllist.get(position).getSpecification());
            desc.setText(viewalllist.get(position).getDetail());
        }

    }

    private void getdata() {
        from = getIntent().getStringExtra("from");
        if (from.equals("main"))
        {
            mainlist = getIntent().getParcelableArrayListExtra("list");
        }
        else if (from.equals("viewall")) {
            viewalllist = getIntent().getParcelableArrayListExtra("list");
        }
        position = getIntent().getIntExtra("pos",0);
    }

    private void initWidget() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");

        prodImage = (ImageView)findViewById(R.id.prodImage);
        plusbutton = (ImageView)findViewById(R.id.plusbutton);
        minusbutton = (ImageView)findViewById(R.id.minusbutton);
        productname = (TextView) findViewById(R.id.productname);
        productdetails = (TextView) findViewById(R.id.productdetails);
        amount = (TextView) findViewById(R.id.amount);
        count = (TextView) findViewById(R.id.count);
        specification = (TextView) findViewById(R.id.specification);
        desc = (TextView) findViewById(R.id.desc);
        addtocarttext = (TextView) findViewById(R.id.addtocarttext);
        addtocart = (LinearLayout) findViewById(R.id.addtocart);
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
}
