package apextechies.theferiwala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import apextechies.theferiwala.R;
import apextechies.theferiwala.adapter.ProductListByCatIdAdapter;
import apextechies.theferiwala.interfaces.ClickPosition;
import apextechies.theferiwala.interfaces.OnTaskCompleted;
import apextechies.theferiwala.model.ProductByCatIdModel;
import apextechies.theferiwala.model.ProductListByCatIdModel;
import apextechies.theferiwala.utilz.Download_web;
import apextechies.theferiwala.utilz.Utility;
import apextechies.theferiwala.utilz.WebServices;

/**
 * Created by Shankar on 2/14/2018.
 */

public class SearchActivity extends AppCompatActivity {
    private RecyclerView rv_search;
    private ArrayList<ProductByCatIdModel> list = new ArrayList<>();
    private EditText toolbartext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initWidgit();

    }

    private void callApi(String s) {
        Utility.showDailog(SearchActivity.this, getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(SearchActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Utility.closeDialog();
                if (response.length() > 0) {
                    Gson gson = new Gson();
                    ProductListByCatIdModel catIdModel = gson.fromJson(response, ProductListByCatIdModel.class);
                    if (catIdModel.getStatus().equals("true")) {
                        list.clear();
                        list = catIdModel.getData();
                        ProductListByCatIdAdapter adapter = new ProductListByCatIdAdapter(SearchActivity.this, list, new ClickPosition() {
                            @Override
                            public void pos(int position) {
                                Toast.makeText(SearchActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SearchActivity.this, ProductDescription.class).putParcelableArrayListExtra("list", list).putExtra("pos", position)
                                        .putExtra("from", "main"));
                            }

                            @Override
                            public void pos(int position, String name, int count) {

                            }
                        });
                        rv_search.setAdapter(adapter);
                    } else {

                    }
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("content", s));
        web.setData(nameValuePairs);
        web.setReqType("post");
        web.execute(WebServices.SEARCPRODUCT);
    }

    private void initWidgit() {
        toolbartext = (EditText) findViewById(R.id.toolbartext);
        rv_search = (RecyclerView) findViewById(R.id.rv_search);
        rv_search.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));

        toolbartext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count>0){
                    callApi(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
