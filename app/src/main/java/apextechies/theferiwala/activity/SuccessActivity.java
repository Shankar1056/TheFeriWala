package apextechies.theferiwala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import apextechies.theferiwala.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Shankar on 12/26/2017.
 */

public class SuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initWidgit();
    }

    private void initWidgit() {
        TextView myorderText = (TextView)findViewById(R.id.myorderText);
        try
        {
            String orderid = getIntent().getStringExtra("data");
            myorderText.setText("Order Id : " +orderid);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       findViewById(R.id.myorderButton).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(SuccessActivity.this,MyOrder.class));
               finishAffinity();
           }
       });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SuccessActivity.this,MainActivity.class));
        finish();
    }
}
