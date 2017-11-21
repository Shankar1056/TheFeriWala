package apextechies.theferiwala.model;

import java.util.ArrayList;

/**
 * Created by shankar on 16/11/17.
 */

public class CategoryModel {
    public String getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }

    public ArrayList<SubCategoryModel> getSubcategories() {
        return subcategories;
    }

    private String cid,cname;
    private ArrayList<SubCategoryModel> subcategories;

}
