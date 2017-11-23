package apextechies.theferiwala.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.adapter.ViewAllPriceNameAdapter;
import apextechies.theferiwala.interfaces.ClickPosition;
import apextechies.theferiwala.model.ViewAllSubCat;

/**
 * Created by shankar on 23/11/17.
 */

public class ViewAllWithNamePrice extends AppCompatActivity {
    private RecyclerView viewall_PriceName;
    private ArrayList<ViewAllSubCat> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewaalwithnameprice);
        getData();
        initWidget();
    }

    private void getData() {
        list =  getIntent().getParcelableArrayListExtra("list");
    }

    private void initWidget() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        TextView toolbartext = (TextView)findViewById(R.id.toolbartext);
        toolbartext.setText("TheFeriWala");

        viewall_PriceName = (RecyclerView)findViewById(R.id.viewall_PriceName);
        viewall_PriceName.setHasFixedSize(true);
        viewall_PriceName.setLayoutManager(new GridLayoutManager(ViewAllWithNamePrice.this, 2));

        ViewAllPriceNameAdapter adapter = new ViewAllPriceNameAdapter(ViewAllWithNamePrice.this,list, new ClickPosition() {
            @Override
            public void pos(int position) {
                Toast.makeText(ViewAllWithNamePrice.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
        viewall_PriceName.setAdapter(adapter);
    }
}
