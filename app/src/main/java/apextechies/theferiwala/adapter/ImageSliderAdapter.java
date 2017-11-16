package apextechies.theferiwala.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.model.BannerSlider;
import apextechies.theferiwala.model.BannerSliderImage;


public class ImageSliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater _Inflater;
    private ArrayList<BannerSliderImage> bannerSliders;

    public ImageSliderAdapter(Context context, ArrayList<BannerSliderImage> bannerSliders) {
        this.context = context;
        this.bannerSliders = bannerSliders;
        this._Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bannerSliders.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View pager = null;
        pager = _Inflater.inflate(R.layout.displayimage_slider, null);
        ImageView ivMusicImage = (ImageView) pager.findViewById(R.id.ivMusicImage);
        container.addView(pager, 0);
        Picasso.with(context).load("http://theferiwala.com/myadmin/"+bannerSliders.get(position).getImage()).into(ivMusicImage);
        return pager;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
