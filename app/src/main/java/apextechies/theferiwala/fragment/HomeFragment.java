package apextechies.theferiwala.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.adapter.ImageSliderAdapter;
import apextechies.theferiwala.adapter.TodaysDealAdapter;
import apextechies.theferiwala.model.BannerSlider;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.OnTaskCompleted;
import apextechies.theferiwala.utilz.WebServices;

/**
 * Created by shankar on 21/11/17.
 */

public class HomeFragment extends Fragment {
    private CirclePageIndicator indicator;
    private ViewPager viewPager;
    private RecyclerView rv_TodaysDeals;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWidgit(view);
        getBannerSlider();
        setValueInTodaysDeal();
    }

    private void initWidgit(View view) {
        indicator = (CirclePageIndicator)view.findViewById(R.id.indicator);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(10);
        viewPager.setCurrentItem(1);
        rv_TodaysDeals = (RecyclerView)view.findViewById(R.id.rv_TodaysDeals);
        rv_TodaysDeals.setHasFixedSize(true);
        rv_TodaysDeals.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

    }
    private void setValueInTodaysDeal() {

        ArrayList<String> todaydealList = new ArrayList<>();
        todaydealList.add("http://theferiwala.com/jk-images/pagedesign_image/n79imvy3x0wqdoc20170831121747.jpg");
        todaydealList.add("http://theferiwala.com/jk-images/pagedesign_image/ij7s81ctuxf5ehf20170831193619.jpg");
        todaydealList.add("http://theferiwala.com/jk-images/pagedesign_image/3ykfztq5vamnfmi20170831193618.jpg");
        todaydealList.add("http://theferiwala.com/jk-images/pagedesign_image/livoknhf0exp2au20170831121746.jpg");
        todaydealList.add("http://theferiwala.com/jk-images/pagedesign_image/9h4snrffe5cxdyk20170831193618.jpg");
        todaydealList.add("http://theferiwala.com/jk-images/pagedesign_image/jotc4aong2kyknm20170831122202.jpg");
        TodaysDealAdapter adapter = new TodaysDealAdapter(getActivity(),todaydealList);
        rv_TodaysDeals.setAdapter(adapter);
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
        // StartAsyncTaskInParallel(web,WebServices.GETSLIDER);
        web.execute(WebServices.GETSLIDER);

    }
}
