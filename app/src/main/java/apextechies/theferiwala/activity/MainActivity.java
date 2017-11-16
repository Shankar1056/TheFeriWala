package apextechies.theferiwala.activity;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import apextechies.theferiwala.adapter.TodaysDealAdapter;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apextechies.theferiwala.R;
import apextechies.theferiwala.adapter.CustomExpandableListAdapter;
import apextechies.theferiwala.adapter.ImageSliderAdapter;
import apextechies.theferiwala.model.BannerSlider;
import apextechies.theferiwala.model.CategoryModel;
import apextechies.theferiwala.model.CategorySubCategoryModel;
import apextechies.theferiwala.model.SubCategoryModel;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.OnTaskCompleted;
import apextechies.theferiwala.utilz.WebServices;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private CirclePageIndicator indicator;
    private ViewPager viewPager;
    private RecyclerView rv_TodaysDeals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        navigationMapping();
        initWidgit();

    }
    private void navigationMapping() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    private void initWidgit() {
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(10);
        viewPager.setCurrentItem(1);
        rv_TodaysDeals = (RecyclerView)findViewById(R.id.rv_TodaysDeals);
        rv_TodaysDeals.setHasFixedSize(true);
        rv_TodaysDeals.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);


        expandableClickListener();
        getCategorySubCategory();
        getBannerSlider();
        setValueInTodaysDeal();
       // getTodaysDeal();
    }

    private void setValueInTodaysDeal() {

        ArrayList<String> todaydealList = new ArrayList<>();
        todaydealList.add("http://theferiwala.com/jk-images/pagedesign_image/n79imvy3x0wqdoc20170831121747.jpg");
        todaydealList.add("http://theferiwala.com/jk-images/pagedesign_image/ij7s81ctuxf5ehf20170831193619.jpg");
        todaydealList.add("http://theferiwala.com/jk-images/pagedesign_image/3ykfztq5vamnfmi20170831193618.jpg");
        todaydealList.add("http://theferiwala.com/jk-images/pagedesign_image/livoknhf0exp2au20170831121746.jpg");
        todaydealList.add("http://theferiwala.com/jk-images/pagedesign_image/9h4snrffe5cxdyk20170831193618.jpg");
        todaydealList.add("http://theferiwala.com/jk-images/pagedesign_image/jotc4aong2kyknm20170831122202.jpg");
        TodaysDealAdapter adapter = new TodaysDealAdapter(MainActivity.this,todaydealList);
        rv_TodaysDeals.setAdapter(adapter);
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
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
    }


    private void getCategorySubCategory() {

        Download_web web = new Download_web(MainActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Gson gson = new Gson();
                CategorySubCategoryModel catSubCatModel = gson.fromJson(response,CategorySubCategoryModel.class);
                if (catSubCatModel.getStatus().equals("true"))
                {
                    expandableListDetail = getData(catSubCatModel.getData());
                    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                    expandableListAdapter = new CustomExpandableListAdapter(MainActivity.this, expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                }
                else
                {
                    Toast.makeText(MainActivity.this, ""+catSubCatModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        web.setReqType("get");
        //StartAsyncTaskInParallel(web,WebServices.CATEGORY);
        web.execute(WebServices.CATEGORY);
    }

    private void getBannerSlider() {
        Download_web web = new Download_web(MainActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Gson gson = new Gson();
                BannerSlider bannerSlider = gson.fromJson(response,BannerSlider.class);
                if (bannerSlider.getStatus().equals("true"))
                {
                    ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(MainActivity.this, bannerSlider.getData());
                    viewPager.setAdapter(imageSliderAdapter);
                    indicator.setViewPager(viewPager);
                }
                else
                {
                    Toast.makeText(MainActivity.this, ""+bannerSlider.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        web.setReqType("get");
       // StartAsyncTaskInParallel(web,WebServices.GETSLIDER);
        web.execute(WebServices.GETSLIDER);

    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(Download_web web, String getslider) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                web.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,getslider);
            else
                web.execute(getslider);

    }

    private HashMap<String, List<String>> getData(ArrayList<CategoryModel> data) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
       for (int i=0;i<data.size();i++)
       {
           expandableListDetail.put(data.get(i).getCname(),getsubcatname(data.get(i).getSubcategories()));
       }


        return expandableListDetail;
    }

    private List<String> getsubcatname(ArrayList<SubCategoryModel> subcategories) {
        List<String> subcatname = new ArrayList<>();
        for (int i=0;i<subcategories.size();i++)
        {
            subcatname.add(subcategories.get(i).getSubcname());
        }
        return subcatname;
    }

}