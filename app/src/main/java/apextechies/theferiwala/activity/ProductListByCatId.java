package apextechies.theferiwala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.adapter.ProductListByCatIdAdapter;
import apextechies.theferiwala.interfaces.ClickPosition;
import apextechies.theferiwala.interfaces.OnTaskCompleted;
import apextechies.theferiwala.model.ProductByCatIdModel;
import apextechies.theferiwala.model.ProductListByCatIdModel;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.WebServices;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Shankar on 11/24/2017.
 */

public class ProductListByCatId extends AppCompatActivity {
    private RecyclerView viewall_PriceName;
    private ArrayList<ProductByCatIdModel> list = new ArrayList<>();
    private String  catId, catName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewaalwithnameprice);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Celias.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initWidget();
        getIntentData();
        getData();
    }

    private void getIntentData() {
        catId = getIntent().getStringExtra("id");
        catName = getIntent().getStringExtra("name");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText(catName);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void getData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(ProductListByCatId.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Log.i("response",response);
                Gson gson = new Gson();
                ProductListByCatIdModel catIdModel = gson.fromJson(response,ProductListByCatIdModel.class);
                if (catIdModel.getStatus().equals("true"))
                {
                    list = catIdModel.getData();
                    ProductListByCatIdAdapter adapter = new ProductListByCatIdAdapter(ProductListByCatId.this, list, new ClickPosition() {
                        @Override
                        public void pos(int position) {
                            Toast.makeText(ProductListByCatId.this, "" + position, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ProductListByCatId.this, ProductDescription.class).putParcelableArrayListExtra("list", list).putExtra("pos", position)
                                    .putExtra("from", "main"));
                        }
                    });
                    viewall_PriceName.setAdapter(adapter);
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("id",catId));
        nameValuePairs.add(new BasicNameValuePair("name",catName));
        web.setData(nameValuePairs);
        web.setReqType("post");
        web.execute(WebServices.GETPRODUCTBYID);
    }


    private void initWidget() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");


        viewall_PriceName = (RecyclerView) findViewById(R.id.viewall_PriceName);
        viewall_PriceName.setLayoutManager(new GridLayoutManager(ProductListByCatId.this, 2));
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
