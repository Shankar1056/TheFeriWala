package apextechies.theferiwala.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.common.PreferenceHelper;
import apextechies.theferiwala.model.ViewAllSubCat;

/**
 * Created by shankar on 16/11/17.
 */

public class TodaysDealAdapter extends RecyclerView.Adapter<TodaysDealAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ViewAllSubCat> imageList;

    public TodaysDealAdapter(Context context, ArrayList<ViewAllSubCat> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todaysdeal_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        Picasso.with(context).load(PreferenceHelper.IMAGE_URL+imageList.get(position).getImage()).into(holder.todaysdealImageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView todaysdealImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            todaysdealImageView =(ImageView) itemView.findViewById(R.id.todaysdealImageView);

        }
    }
}
