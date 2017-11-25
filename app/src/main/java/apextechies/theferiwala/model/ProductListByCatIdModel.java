package apextechies.theferiwala.model;

import java.util.ArrayList;

/**
 * Created by Shankar on 11/24/2017.
 */

public class ProductListByCatIdModel {

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<ProductByCatIdModel> getData() {
        return data;
    }

    private String status,msg;
    private ArrayList<ProductByCatIdModel> data;
}
