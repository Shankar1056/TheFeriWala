package apextechies.theferiwala.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apextechies.theferiwala.R;
import apextechies.theferiwala.adapter.CustomExpandableListAdapter;
import apextechies.theferiwala.common.ClsGeneral;
import apextechies.theferiwala.common.PreferenceHelper;
import apextechies.theferiwala.fragment.HomeFragment;
import apextechies.theferiwala.interfaces.OnTaskCompleted;
import apextechies.theferiwala.login.LoginActivity;
import apextechies.theferiwala.model.CategoryModel;
import apextechies.theferiwala.model.CategorySubCategoryModel;
import apextechies.theferiwala.model.SubCategoryModel;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.WebServices;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    ArrayList<SubCategoryModel> list = new ArrayList<>();
    private CategorySubCategoryModel catSubCatModel;
    private ImageView search_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        navigationMapping();
        initWidgit();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void navigationMapping() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText("TheFeriWala");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    private void initWidgit() {

        search_icon = (ImageView)findViewById(R.id.search_icon);
        search_icon.setVisibility(View.VISIBLE);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        findViewById(R.id.cartLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyCart.class));
            }
        });

        expandableClickListener();
        getCategorySubCategory();

        // getTodaysDeal();
        gotoHomeFragment();

        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });
    }

    private void gotoHomeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new HomeFragment();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }


    private void expandableClickListener() {
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();


            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
               /* Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/

                String subcatid = catSubCatModel.getData().get(groupPosition).getSubcategories().get(childPosition).getSubcid();
                String subcatname = catSubCatModel.getData().get(groupPosition).getSubcategories().get(childPosition).getSubcname();
                //Toast.makeText(MainActivity.this, "" + subcatid, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ProductListByCatId.class).putExtra("id", subcatid).putExtra("name", subcatname));


                return false;
            }
        });
    }


    private void getCategorySubCategory() {

        Download_web web = new Download_web(MainActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Gson gson = new Gson();
                catSubCatModel = gson.fromJson(response, CategorySubCategoryModel.class);
                if (catSubCatModel.getStatus().equals("true")) {
                    expandableListDetail = getData(catSubCatModel.getData());
                    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                    expandableListAdapter = new CustomExpandableListAdapter(MainActivity.this, expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "" + catSubCatModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        web.setReqType("get");
        //StartAsyncTaskInParallel(web,WebServices.CATEGORY);
        web.execute(WebServices.CATEGORY);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(Download_web web, String getslider) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            web.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, getslider);
        else
            web.execute(getslider);

    }

    private HashMap<String, List<String>> getData(ArrayList<CategoryModel> data) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        for (int i = 0; i < data.size(); i++) {
            expandableListDetail.put(data.get(i).getCname(), getsubcatname(data.get(i).getSubcategories()));
        }


        return expandableListDetail;
    }

    private List<String> getsubcatname(ArrayList<SubCategoryModel> subcategories) {
        List<String> subcatname = new ArrayList<>();
        for (int i = 0; i < subcategories.size(); i++) {
            subcatname.add(subcategories.get(i).getSubcname());
        }
        return subcatname;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.myorder) {
            startActivity(new Intent(MainActivity.this, MyOrder.class));
            finish();
            return true;
        }
        if (id == R.id.logout) {
            ClsGeneral.setPreferences(MainActivity.this, PreferenceHelper.ID,"");
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finishAffinity();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }




}