package apextechies.theferiwala.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shankar on 12/25/2017.
 */

public class MyOrderModel implements Parcelable{

    protected MyOrderModel(Parcel in) {
        id = in.readString();
        sellerid = in.readString();
        payment_mode = in.readString();
        payment_status = in.readString();
        oid = in.readString();
        login_id = in.readString();
        quantity = in.readString();
        product_name = in.readString();
        product_price = in.readString();
        prod_image = in.readString();
        date = in.readString();
        time = in.readString();
        delivery_status = in.readString();
    }

    public static final Creator<MyOrderModel> CREATOR = new Creator<MyOrderModel>() {
        @Override
        public MyOrderModel createFromParcel(Parcel in) {
            return new MyOrderModel(in);
        }

        @Override
        public MyOrderModel[] newArray(int size) {
            return new MyOrderModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }


    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProd_image() {
        return prod_image;
    }

    public void setProd_image(String prod_image) {
        this.prod_image = prod_image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    private String id,sellerid,payment_mode,payment_status,oid,login_id,quantity,product_name,product_price,prod_image,date,time,delivery_status;

    public MyOrderModel(String id, String sellerid, String payment_mode, String payment_status, String oid, String  login_id,
                        String quantity, String product_name, String product_price, String prod_image, String date, String time, String delivery_status)
    {
        this.id = id;
        this.sellerid = sellerid;
        this.payment_mode = payment_mode;
        this.payment_status = payment_status;
        this.oid = oid;
        this.login_id = login_id;
        this.quantity = quantity;
        this.product_name = product_name;
        this.product_price = product_price;
        this.prod_image = prod_image;
        this.date = date;
        this.time = time;
        this.delivery_status = delivery_status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(sellerid);
        dest.writeString(payment_mode);
        dest.writeString(payment_status);
        dest.writeString(oid);
        dest.writeString(login_id);
        dest.writeString(quantity);
        dest.writeString(product_name);
        dest.writeString(product_price);
        dest.writeString(prod_image);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(delivery_status);
    }
}
