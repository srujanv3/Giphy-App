package com.blogspot.svdevs.giphydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blogspot.svdevs.giphydemo.model.DataModel;
import com.blogspot.svdevs.giphydemo.ui.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataAdapter.OnItemClickListener {

    RecyclerView rView;
    List dataList = new ArrayList();
    ArrayList<DataModel> dataModelList = new ArrayList<>();
    DataAdapter dataAdapter;

    public static final String API_KEY = "hfpCBPaU59ZiHsobavu03xNLVJm715O8";
    public static final String BASE_URL = "https://api.giphy.com/v1/gifs/trending?api_key=";

    String url = BASE_URL + API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rView = findViewById(R.id.recyclerView);
        rView.setLayoutManager(new GridLayoutManager(this, 2));
        rView.addItemDecoration(new SpacesItemDecoration(10));
        rView.setHasFixedSize(true);


        //dataModelList.clear();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Access the initial array named 'data'
                    JSONArray dataArray = response.getJSONArray("data");

                    // From the accessed array parse all the objects
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject obj = dataArray.getJSONObject(i);
                        // String used for testing purpose
                        String name = obj.getString("images");

                        // Now from the parsed objects retrieve the objects with key name images
                        JSONObject obj2 = obj.getJSONObject("images");
                        // String used for testing purpose
                        String name2 = obj2.getString("downsized_medium");

                        // Now from the 'images' named objects, retrieve the desired resolution of the gif eg.(downsized_medium)
                        JSONObject obj3 = obj2.getJSONObject("downsized_medium");

                        // Now from this object, retrieve the source url of the gif that is stored in 'url' key
                        // +++++++ required data ++++++++
                        String sourceUrl = obj3.getString("url");

                        // Adding the source url in the list to test display in the listView
                        dataModelList.add(new DataModel(sourceUrl));


//                        DataModel dataModel = new DataModel(sourceUrl);
//                        //dataModel.setImageUrl(sourceUrl);
//                        dataModelList.add(dataModel);
//                        dataList.add(sourceUrl);
//                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1
//                                , dataList);
//                        //list.setAdapter(arrayAdapter);
                    }

                    dataAdapter = new DataAdapter(MainActivity.this, dataModelList);
                    rView.setAdapter(dataAdapter);
                    dataAdapter.setOnItemClickListener(MainActivity.this);


                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(objectRequest);

    }

    // Method to get the image url and pass it to the next FullViewActivity
    @Override
    public void onItemClick(int pos) {
        Intent fullView = new Intent(this, FullViewActivity.class);
        DataModel clickedItem = dataModelList.get(pos);

        fullView.putExtra("imageUrl",clickedItem.getImageUrl());
        startActivity(fullView);
    }
}

//    JSONObject obj = new JSONObject();
//                    JSONArray dataArray = response.getJSONArray("data");
//                    //JSONObject object = response.getJSONObject("downsized_medium");
//
//                    for(int i=0;i<dataArray.length();i++){
//                        obj =dataArray.getJSONObject(i).getJSONObject("downsized_medium");
//                    }
//                    //Toast.makeText(MainActivity.this, "Data = " + obj.toString(), Toast.LENGTH_LONG).show();
//                    tv.setText(obj.toString());

// if(obj.length()!=0){
//         Iterator<String> key = obj.keys();
//        while(key.hasNext()){
//        String keyName = key.next();
//        for (int j = 0; j < obj.length(); j++) {
//        JSONObject object1 = obj.getString("downsized_medium");
//        String area = object1.getString("area");
//
//        }
//        }
//        }