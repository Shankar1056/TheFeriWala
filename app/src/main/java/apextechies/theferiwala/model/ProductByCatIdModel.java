package apextechies.theferiwala.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shankar on 11/24/2017.
 */

public class ProductByCatIdModel implements Parcelable{
    protected ProductByCatIdModel(Parcel in) {
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

    public static final Creator<ProductByCatIdModel> CREATOR = new Creator<ProductByCatIdModel>() {
        @Override
        public ProductByCatIdModel createFromParcel(Parcel in) {
            return new ProductByCatIdModel(in);
        }

        @Override
        public ProductByCatIdModel[] newArray(int size) {
            return new ProductByCatIdModel[size];
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(sellerid);
        dest.writeString(pname);
        dest.writeString(company_name);
        dest.writeString(detail);
        dest.writeString(image);
        dest.writeString(price);
        dest.writeString(cid);
        dest.writeString(dis_price);
        dest.writeString(discount);
        dest.writeString(color);
        dest.writeString(warrenty);
        dest.writeString(specification);
        dest.writeString(highlights);
        dest.writeString(qnt);
        dest.writeString(product_id);
        dest.writeString(mode);
        dest.writeString(catid);
        dest.writeString(subid);
        dest.writeString(shipping);
        dest.writeString(status);
    }
}
