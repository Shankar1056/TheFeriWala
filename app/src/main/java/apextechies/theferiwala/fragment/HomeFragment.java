package apextechies.theferiwala.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.activity.ProductDescription;
import apextechies.theferiwala.activity.ViewAllWithNamePrice;
import apextechies.theferiwala.adapter.BestOfferAdapter;
import apextechies.theferiwala.adapter.ImageSliderAdapter;
import apextechies.theferiwala.adapter.MensAdapter;
import apextechies.theferiwala.adapter.NewArrivals;
import apextechies.theferiwala.adapter.TodaysDealAdapter;
import apextechies.theferiwala.adapter.TrendingProductAdapter;
import apextechies.theferiwala.interfaces.ClickPosition;
import apextechies.theferiwala.interfaces.OnTaskCompleted;
import apextechies.theferiwala.model.BannerSlider;
import apextechies.theferiwala.model.DataListModel;
import apextechies.theferiwala.model.ViewAllModel;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.Utility;
import apextechies.theferiwala.utilz.WebServices;

/**
 * Created by shankar on 21/11/17.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    private CirclePageIndicator indicator;
    private ViewPager viewPager;
    private RecyclerView BO_RecyclerView,NA_RecyclerView,M_RecyclerView,TD_RecyclerView,TP_RecyclerView;
    private TextView BO_Text,BO_ViewAll;
    private TextView NA_Text,NA_ViewAll;
    private TextView M_Text,M_ViewAll;
    private TextView TD_Text,TD_ViewAll;
    private TextView TP_Text,TP_ViewAll;
    private ArrayList<DataListModel> list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWidgit(view);
        clickListener();
        getBannerSlider();
        getAllHomePageData();
    }


    private void clickListener() {
        BO_ViewAll.setOnClickListener(this);
        NA_ViewAll.setOnClickListener(this);
        M_ViewAll.setOnClickListener(this);
        TD_ViewAll.setOnClickListener(this);
        TP_ViewAll.setOnClickListener(this);
    }


    private void initWidgit(View view) {
        indicator = (CirclePageIndicator)view.findViewById(R.id.indicator);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(10);
        viewPager.setCurrentItem(1);

        BO_Text = (TextView)view.findViewById(R.id.BO_Text);
        BO_ViewAll = (TextView)view.findViewById(R.id.BO_ViewAll);
        NA_Text = (TextView)view.findViewById(R.id.NA_Text);
        NA_ViewAll = (TextView)view.findViewById(R.id.NA_ViewAll);
        M_Text = (TextView)view.findViewById(R.id.M_Text);
        M_ViewAll = (TextView)view.findViewById(R.id.M_ViewAll);
        TD_Text = (TextView)view.findViewById(R.id.TD_Text);
        TD_ViewAll = (TextView)view.findViewById(R.id.TD_ViewAll);
        TP_Text = (TextView)view.findViewById(R.id.TP_Text);
        TP_ViewAll = (TextView)view.findViewById(R.id.TP_ViewAll);

        BO_RecyclerView = (RecyclerView)view.findViewById(R.id.BO_RecyclerView);
        BO_RecyclerView.setNestedScrollingEnabled(false);
        BO_RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        NA_RecyclerView = (RecyclerView)view.findViewById(R.id.NA_RecyclerView);
        NA_RecyclerView.setNestedScrollingEnabled(false);
        NA_RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        M_RecyclerView = (RecyclerView)view.findViewById(R.id.M_RecyclerView);
        M_RecyclerView.setNestedScrollingEnabled(false);
        M_RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        TD_RecyclerView = (RecyclerView)view.findViewById(R.id.TD_RecyclerView);
        TD_RecyclerView.setNestedScrollingEnabled(false);
        TD_RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        TP_RecyclerView = (RecyclerView)view.findViewById(R.id.TP_RecyclerView);
        TP_RecyclerView.setNestedScrollingEnabled(false);
        TP_RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

    }


    private void getBannerSlider() {
        Download_web web = new Download_web(getActivity(), new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Gson gson = new Gson();
                BannerSlider bannerSlider = gson.fromJson(response,BannerSlider.class);
                if (bannerSlider.getStatus().equals("true"))
                {
                    ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(getActivity(), bannerSlider.getData());
                    viewPager.setAdapter(imageSliderAdapter);
                    indicator.setViewPager(viewPager);
                }
                else
                {
                    Toast.makeText(getActivity(), ""+bannerSlider.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        web.setReqType("get");
        web.execute(WebServices.GETSLIDER);

    }
    private void getAllHomePageData() {
        Utility.showDailog(getActivity(),getResources().getString(R.string.pleasewait));
        Download_web web = new Download_web(getActivity(), new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
             Utility.closeDialog();
                Gson gson = new Gson();
                ViewAllModel viewAllModel = gson.fromJson(response,ViewAllModel.class);
                if (viewAllModel.getStatus().equals("true"))
                {
                    setvalueInTextView(viewAllModel.getData());
                    list = viewAllModel.getData();
                }
                else
                {
                    Toast.makeText(getActivity(), ""+viewAllModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        web.setReqType("get");
        web.execute(WebServices.GETHOMEPRODUCT);
    }

    private void setvalueInTextView(ArrayList<DataListModel> data) {
        try {
            if (data.size() > 0) {
                BO_Text.setText(data.get(0).getCname());
                BestOfferAdapter adapter = new BestOfferAdapter(getActivity(), data.get(0).getSubcategories(), new ClickPosition() {
                    @Override
                    public void pos(int position) {
                        startActivity(new Intent(getActivity(), ProductDescription.class).putExtra("list",list.get(0).getSubcategories()).putExtra("pos",position)
                                .putExtra("from", "viewall"));

                    }

                    @Override
                    public void pos(int position, String name, int count) {

                    }
                });
                BO_RecyclerView.setAdapter(adapter);
            }
            if (data.size() > 1) {
                NA_Text.setText(data.get(1).getCname());
                NewArrivals adapter = new NewArrivals(getActivity(), data.get(1).getSubcategories(), new ClickPosition() {
                    @Override
                    public void pos(int position) {
                        startActivity(new Intent(getActivity(), ProductDescription.class).putExtra("list",list.get(1).getSubcategories()).putExtra("pos",position)
                                .putExtra("from", "viewall"));

                    }

                    @Override
                    public void pos(int position, String name, int count) {

                    }
                });
                NA_RecyclerView.setAdapter(adapter);
            }
            if (data.size() > 2) {
                M_Text.setText(data.get(2).getCname());
                MensAdapter adapter = new MensAdapter(getActivity(), data.get(2).getSubcategories(), new ClickPosition() {
                    @Override
                    public void pos(int position) {
                        startActivity(new Intent(getActivity(), ProductDescription.class).putExtra("list",list.get(2).getSubcategories()).putExtra("pos",position)
                                .putExtra("from", "viewall"));

                    }

                    @Override
                    public void pos(int position, String name, int count) {

                    }
                });
                M_RecyclerView.setAdapter(adapter);
            }
            if (data.size() > 3) {
                TD_Text.setText(data.get(3).getCname());
                TodaysDealAdapter adapter = new TodaysDealAdapter(getActivity(), data.get(3).getSubcategories(), new ClickPosition() {
                    @Override
                    public void pos(int position) {
                        startActivity(new Intent(getActivity(), ProductDescription.class).putExtra("list",list.get(3).getSubcategories()).putExtra("pos",position)
                                .putExtra("from", "viewall"));

                    }

                    @Override
                    public void pos(int position, String name, int count) {

                    }
                });
                TD_RecyclerView.setAdapter(adapter);
            }
            if (data.size() > 4) {
                TP_Text.setText(data.get(4).getCname());
                TrendingProductAdapter adapter = new TrendingProductAdapter(getActivity(), data.get(4).getSubcategories(), new ClickPosition() {
                    @Override
                    public void pos(int position) {
                        startActivity(new Intent(getActivity(), ProductDescription.class).putExtra("list",list.get(4).getSubcategories()).putExtra("pos",position)
                                .putExtra("from", "viewall"));


                    }

                    @Override
                    public void pos(int position, String name, int count) {

                    }
                });
                TP_RecyclerView.setAdapter(adapter);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.BO_ViewAll:
                startActivity(new Intent(getActivity(), ViewAllWithNamePrice.class).putParcelableArrayListExtra("list",list.get(0).getSubcategories()).putExtra("name",list.get(0).getCname()));
                break;
            case R.id.NA_ViewAll:
                startActivity(new Intent(getActivity(), ViewAllWithNamePrice.class).putParcelableArrayListExtra("list",list.get(1).getSubcategories()).putExtra("name",list.get(1).getCname()));
                break;
            case R.id.M_ViewAll:
                startActivity(new Intent(getActivity(), ViewAllWithNamePrice.class).putParcelableArrayListExtra("list",list.get(2).getSubcategories()).putExtra("name",list.get(2).getCname()));
                break;
            case R.id.TD_ViewAll:
                startActivity(new Intent(getActivity(), ViewAllWithNamePrice.class).putParcelableArrayListExtra("list",list.get(3).getSubcategories()).putExtra("name",list.get(3).getCname()));
                break;
            case R.id.TP_ViewAll:
                startActivity(new Intent(getActivity(), ViewAllWithNamePrice.class).putParcelableArrayListExtra("list",list.get(4).getSubcategories()).putExtra("name",list.get(4).getCname()));
                break;
        }
    }
}
