package apextechies.theferiwala.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import apextechies.theferiwala.R;

/**
 * Created by Shankar on 2/21/2018.
 */

public class AboutUs extends AppCompatActivity {
    private LinearLayout cartLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);

        initWidit();

    }

    private void initWidit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText("About Us");
        cartLayout = (LinearLayout) findViewById(R.id.cartLayout);
        cartLayout.setVisibility(View.INVISIBLE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
