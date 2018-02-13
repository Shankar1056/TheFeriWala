package apextechies.theferiwala.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.adapter.MyOrderDescriptionAdapter;
import apextechies.theferiwala.interfaces.ClickPosition;
import apextechies.theferiwala.model.MyOrderModel;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Shankar on 12/25/2017.
 */

public class MyOrderDescription extends AppCompatActivity {
    private RecyclerView rv_myorderdesc;
    ArrayList<MyOrderModel> list = new ArrayList<>();
    private int pos;
    private TextView orderId,orderStatus, orderDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorderdesc);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initWidgit();
        getData();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void getData() {
        list = getIntent().getParcelableArrayListExtra("list");
        pos = getIntent().getIntExtra("pos",0);

        orderId.setText("Order id: "+list.get(0).getOid());
        orderStatus.setText("Status "+list.get(0).getDelivery_status());
        orderDate.setText("Order date: "+list.get(0).getDate());

        MyOrderDescriptionAdapter adapter = new MyOrderDescriptionAdapter(MyOrderDescription.this, list, new ClickPosition() {
            @Override
            public void pos(int position) {

            }

            @Override
            public void pos(int position, String name, int count) {

            }
        });
        rv_myorderdesc.setAdapter(adapter);
    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText("Order Details");
        orderId = (TextView)findViewById(R.id.orderId);
        orderStatus = (TextView)findViewById(R.id.orderStatus);
        orderDate = (TextView)findViewById(R.id.orderDate);
        rv_myorderdesc = (RecyclerView) findViewById(R.id.rv_myorderdesc);
        rv_myorderdesc.setLayoutManager(new LinearLayoutManager(MyOrderDescription.this));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
