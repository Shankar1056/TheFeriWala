package apextechies.theferiwala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.adapter.ViewAllPriceNameAdapter;
import apextechies.theferiwala.interfaces.ClickPosition;
import apextechies.theferiwala.model.ViewAllSubCat;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by shankar on 23/11/17.
 */

public class ViewAllWithNamePrice extends AppCompatActivity {
    private RecyclerView viewall_PriceName;
    private ArrayList<ViewAllSubCat> list = new ArrayList<>();
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewaalwithnameprice);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        getData();
        initWidget();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void getData() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText("View All");
        list = getIntent().getParcelableArrayListExtra("list");
        name = getIntent().getStringExtra("name");



    }

    private void initWidget() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText(name);

        viewall_PriceName = (RecyclerView) findViewById(R.id.viewall_PriceName);
        viewall_PriceName.setLayoutManager(new GridLayoutManager(ViewAllWithNamePrice.this, 2));

        ViewAllPriceNameAdapter adapter = new ViewAllPriceNameAdapter(ViewAllWithNamePrice.this, list, new ClickPosition() {
            @Override
            public void pos(int position) {
                Toast.makeText(ViewAllWithNamePrice.this, "" + position, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ViewAllWithNamePrice.this, ProductDescription.class).putParcelableArrayListExtra("list", list).putExtra("pos", position)
                        .putExtra("from", "viewall"));
            }

            @Override
            public void pos(int position, String name, int count) {

            }
        });
        viewall_PriceName.setAdapter(adapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.cartLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewAllWithNamePrice.this, MyCart.class));
            }
        });
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
