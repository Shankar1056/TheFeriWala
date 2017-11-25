package apextechies.theferiwala.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.model.ProductByCatIdModel;
import apextechies.theferiwala.common.PreferenceHelper;
import apextechies.theferiwala.interfaces.ClickPosition;

/**
 * Created by Shankar on 11/24/2017.
 */

public class ProductListByCatIdAdapter extends RecyclerView.Adapter<ProductListByCatIdAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ProductByCatIdModel> imageList;
    private ClickPosition clickPosition;

    public ProductListByCatIdAdapter(Context context, ArrayList<ProductByCatIdModel> imageList, ClickPosition clickPosition) {
        this.context = context;
        this.imageList = imageList;
        this.clickPosition = clickPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewallpricename_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        Picasso.with(context).load(PreferenceHelper.IMAGE_URL+imageList.get(position).getImage()).into(holder.image);
        holder.oldprice.setText(imageList.get(position).getPrice());
        holder.currentprice.setText(imageList.get(position).getDis_price());
        holder.productName.setText(imageList.get(position).getPname());

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

        private ImageView image;
        private TextView oldprice,currentprice,productName;

        public ViewHolder(View itemView) {
            super(itemView);
            image =(ImageView) itemView.findViewById(R.id.image);
            oldprice =(TextView) itemView.findViewById(R.id.oldprice);
            currentprice =(TextView) itemView.findViewById(R.id.currentprice);
            productName =(TextView) itemView.findViewById(R.id.productName);
            oldprice.setPaintFlags(oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }
}

