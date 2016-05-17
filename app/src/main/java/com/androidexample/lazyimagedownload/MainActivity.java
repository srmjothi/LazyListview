package com.androidexample.lazyimagedownload;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by JO265962 on 5/17/2016.
 */
public class MainActivity extends Activity {
    
    JsonParse jsondata;
    private static String url = "https://dl.dropboxusercontent.com/u/746330/facts.json";
    String TAG = "JsonListview";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Parse Json from URL using Async Task
        jsondata = new JsonParse(this);
        jsondata.gethttpjson();
    }

    @Override
    public void onDestroy()
    {
    	// Remove adapter refference from list
        jsondata.list.setAdapter(null);
        super.onDestroy();
    }
}