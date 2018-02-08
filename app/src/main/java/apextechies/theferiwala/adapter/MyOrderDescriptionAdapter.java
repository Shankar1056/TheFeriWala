package apextechies.theferiwala.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.common.PreferenceHelper;
import apextechies.theferiwala.interfaces.ClickPosition;
import apextechies.theferiwala.model.MyOrderModel;

/**
 * Created by Shankar on 12/25/2017.
 */

public class MyOrderDescriptionAdapter extends RecyclerView.Adapter<MyOrderDescriptionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MyOrderModel> imageList;
    private ClickPosition clickPosition;

    public MyOrderDescriptionAdapter(Context context, ArrayList<MyOrderModel> imageList, ClickPosition clickPosition) {
        this.context = context;
        this.imageList = imageList;
        this.clickPosition = clickPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorderdesc_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

if (imageList.get(position).getProd_image()!=null && imageList.get(position).getProd_image().length()>0) {
    Picasso.with(context).load(PreferenceHelper.IMAGE_URL+imageList.get(position).getProd_image()).into(holder.prodImage);
}
        holder.prodName.setText(imageList.get(position).getProduct_name());
        holder.prodQuantity.setText("Qty : "+imageList.get(position).getQuantity());
        holder.prodPrice.setText("Amount : "+imageList.get(position).getProduct_price());
        holder.orderStatus.setText(imageList.get(position).getDelivery_status());

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

        private ImageView prodImage;
        private TextView prodName,prodQuantity,prodPrice,orderStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            prodName =(TextView) itemView.findViewById(R.id.prodName);
            prodQuantity =(TextView) itemView.findViewById(R.id.prodQuantity);
            prodPrice =(TextView) itemView.findViewById(R.id.prodPrice);
            prodImage =(ImageView) itemView.findViewById(R.id.prodImage);
            orderStatus =(TextView) itemView.findViewById(R.id.orderStatus);

        }
    }
}



