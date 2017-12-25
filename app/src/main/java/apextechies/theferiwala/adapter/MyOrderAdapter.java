package apextechies.theferiwala.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.interfaces.ClickPosition;
import apextechies.theferiwala.model.MyOrderModel;

/**
 * Created by Shankar on 12/25/2017.
 */

public class MyOrderAdapter  extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MyOrderModel> imageList;
    private ClickPosition clickPosition;

    public MyOrderAdapter(Context context, ArrayList<MyOrderModel> imageList, ClickPosition clickPosition) {
        this.context = context;
        this.imageList = imageList;
        this.clickPosition = clickPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorder_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.orderId.setText("OrderId : "+imageList.get(position).getOid());
        holder.orderDate.setText(imageList.get(position).getDate());

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

        private TextView orderId,orderDate;
        private LinearLayout orderlayout;

        public ViewHolder(View itemView) {
            super(itemView);
            orderId =(TextView) itemView.findViewById(R.id.orderId);
            orderDate =(TextView) itemView.findViewById(R.id.orderDate);
            orderlayout =(LinearLayout) itemView.findViewById(R.id.orderlayout);

        }
    }
}


