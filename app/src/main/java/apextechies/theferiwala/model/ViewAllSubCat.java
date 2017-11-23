package apextechies.theferiwala.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shankar on 23/11/17.
 */

public class ViewAllSubCat implements Parcelable {

    protected ViewAllSubCat(Parcel in) {
        id = in.readString();
        sellerid = in.readString();
        pname = in.readString();
        company_name = in.readString();
        detail = in.readString();
        image = in.readString();
        price = in.readString();
        cid = in.readString();
        dis_price = in.readString();
        discount = in.readString();
        color = in.readString();
        warrenty = in.readString();
        specification = in.readString();
        highlights = in.readString();
        qnt = in.readString();
        product_id = in.readString();
        mode = in.readString();
        catid = in.readString();
        subid = in.readString();
        shipping = in.readString();
        status = in.readString();
    }

    public static final Creator<ViewAllSubCat> CREATOR = new Creator<ViewAllSubCat>() {
        @Override
        public ViewAllSubCat createFromParcel(Parcel in) {
            return new ViewAllSubCat(in);
        }

        @Override
        public ViewAllSubCat[] newArray(int size) {
            return new ViewAllSubCat[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getSellerid() {
        return sellerid;
    }

    public String getPname() {
        return pname;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getDetail() {
        return detail;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getCid() {
        return cid;
    }

    public String getDis_price() {
        return dis_price;
    }

    public String getDiscount() {
        return discount;
    }

    public String getColor() {
        return color;
    }

    public String getWarrenty() {
        return warrenty;
    }

    public String getSpecification() {
        return specification;
    }

    public String getHighlights() {
        return highlights;
    }

    public String getQnt() {
        return qnt;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getMode() {
        return mode;
    }

    public String getCatid() {
        return catid;
    }

    public String getSubid() {
        return subid;
    }

    public String getShipping() {
        return shipping;
    }

    public String getStatus() {
        return status;
    }

    private String id,sellerid,pname,company_name,detail,image,price,cid,dis_price,discount,color,
            warrenty,specification,highlights,qnt,product_id,mode,catid,subid,shipping,status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(sellerid);
        parcel.writeString(pname);
        parcel.writeString(company_name);
        parcel.writeString(detail);
        parcel.writeString(image);
        parcel.writeString(price);
        parcel.writeString(cid);
        parcel.writeString(dis_price);
        parcel.writeString(discount);
        parcel.writeString(color);
        parcel.writeString(warrenty);
        parcel.writeString(specification);
        parcel.writeString(highlights);
        parcel.writeString(qnt);
        parcel.writeString(product_id);
        parcel.writeString(mode);
        parcel.writeString(catid);
        parcel.writeString(subid);
        parcel.writeString(shipping);
        parcel.writeString(status);
    }
}
