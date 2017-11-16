package apextechies.theferiwala.model;

import java.util.ArrayList;

/**
 * Created by shankar on 16/11/17.
 */

public class CategorySubCategoryModel {
    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<CategoryModel> getData() {
        return data;
    }

    private String status,msg;
    ArrayList<CategoryModel> data;
}
