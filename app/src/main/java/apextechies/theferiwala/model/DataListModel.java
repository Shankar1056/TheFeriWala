package apextechies.theferiwala.model;

import java.util.ArrayList;

/**
 * Created by shankar on 23/11/17.
 */

public class DataListModel {

    public String getId() {
        return id;
    }

    public String getCname() {
        return cname;
    }

    public ArrayList<ViewAllSubCat> getSubcategories() {
        return subcategories;
    }

    private String id,cname;
    private ArrayList<ViewAllSubCat> subcategories;
}
