package apextechies.theferiwala.model;

import java.util.ArrayList;

/**
 * Created by shankar on 23/11/17.
 */

public class ViewAllModel {
    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<DataListModel> getData() {
        return data;
    }

    private String status,msg;
    private ArrayList<DataListModel> data;
}
