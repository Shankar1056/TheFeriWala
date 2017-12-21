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
import apextechies.theferiwala.model.MyCartModel;

/**
 * Created by Shankar on 12/21/2017.
 */

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MyCartModel> imageList;
    private ClickPosition clickPosition;

    public MyCartAdapter(Context context, ArrayList<MyCartModel> imageList, ClickPosition clickPosition) {
        this.context = context;
        this.imageList = imageList;
        this.clickPosition = clickPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        try {
            Picasso.with(context).load(PreferenceHelper.IMAGE_URL + imageList.get(position).getImage()).into(holder.cartimage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        holder.cartprod_name.setText(imageList.get(position).getProductName());
        holder.quantity.setText("Qty : "+imageList.get(position).getCartCount());
        holder.cartamount.setText(imageList.get(position).getDiscountPrice());
        holder.count.setText(imageList.get(position).getCartCount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition.pos(position);
            }
        });

        holder.plusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCartModel model = imageList.get(position);
                int count = Integer.parseInt(imageList.get(position).getCartCount());
                model.setCartCount(""+(count+1));
                clickPosition.pos(position, "update", (count+1));
            }
        });

        holder.minusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCartModel model = imageList.get(position);
                int count = Integer.parseInt(imageList.get(position).getCartCount());
                model.setCartCount(""+(count-1));
                if (count-1 == 0)
                {
                    clickPosition.pos(position, "delete", (count - 1));
                }
                else {
                    clickPosition.pos(position, "update", (count - 1));
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition.pos(position, "delete", 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView cartimage, delete,plusbutton,minusbutton;
        private TextView cartprod_name,quantity,cartamount,count;

        public ViewHolder(View itemView) {
            super(itemView);
            cartimage =(ImageView) itemView.findViewById(R.id.cartimage);
            delete =(ImageView) itemView.findViewById(R.id.delete);
            plusbutton =(ImageView) itemView.findViewById(R.id.plusbutton);
            minusbutton =(ImageView) itemView.findViewById(R.id.minusbutton);
            cartprod_name =(TextView) itemView.findViewById(R.id.cartprod_name);
            quantity =(TextView) itemView.findViewById(R.id.quantity);
            cartamount =(TextView) itemView.findViewById(R.id.cartamount);
            count =(TextView) itemView.findViewById(R.id.count);
            /*oldprice.setPaintFlags(oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);*/

        }
    }
}


