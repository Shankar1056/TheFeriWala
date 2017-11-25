package apextechies.theferiwala.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.common.PreferenceHelper;
import apextechies.theferiwala.interfaces.ClickPosition;
import apextechies.theferiwala.model.ViewAllSubCat;

/**
 * Created by shankar on 23/11/17.
 */

public class NewArrivals extends RecyclerView.Adapter<NewArrivals.ViewHolder> {

    private Context context;
    private ArrayList<ViewAllSubCat> imageList;
    private ClickPosition clickPosition;

    public NewArrivals(Context context, ArrayList<ViewAllSubCat> imageList, ClickPosition clickPosition) {
        this.context = context;
        this.imageList = imageList;
        this.clickPosition = clickPosition;
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
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickPosition.pos(position);
        }
    });
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
