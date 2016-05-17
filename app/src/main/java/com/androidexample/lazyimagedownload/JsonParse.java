package com.androidexample.lazyimagedownload;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JO265962 on 5/17/2016.
 */
public class JsonParse extends MainActivity {
    // URL to get contacts JSON
    private static String url = "https://dl.dropboxusercontent.com/u/746330/facts.json";

    // JSON Node names
    private static final String TAG_ROWS = "rows";
    private static final String TAG_TITLE = "title";
    private static final String TAG_DESC = "description";
    private static final String TAG_IMG = "imageHref";
    public Activity activity;
    ListView list;
    LazyImageLoadAdapter adapter;
    String titleString;

    //Constructor with no parameter
    public JsonParse(Activity _activity) {
        this.activity = _activity;
    }

    public void gethttpjson() {
        Log.d(TAG, "gethttpjson: ");
        new GetRows().execute();
    }

     /**
     * Async task class to get json by making HTTP call
     */
    private class GetRows extends AsyncTask<Void, Void, String> {

        // Hashmap for ListView
        ArrayList<HashMap<String, String>> rowList;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // Creating service handler class instance
            WebRequest webreq = new WebRequest();
            // Making a request to url and getting response
            String jsonStr = webreq.makeWebServiceCall(url, WebRequest.GET);
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            rowList = ParseJSON(result);
            Log.d("Response: onPostExecute", "> " + result);

            //Update ActionBar with title from Json
            titleString = ParseJSON_Titlebar(result);
            activity.getActionBar().setTitle(titleString);

            list = (ListView)activity.findViewById(R.id.list);

            // Create custom adapter for listview
            adapter=new LazyImageLoadAdapter(activity, result);

            //Set adapter to listview
            list.setAdapter(adapter);

            Button b=(Button)activity.findViewById(R.id.button1);
            b.setOnClickListener(listener);
        }
    }

    //Get Title from JSON
    private String ParseJSON_Titlebar(String json) {
        if (json != null) {
            try {
                ArrayList<String> titlear = new ArrayList<String>();

                JSONObject jsonObj = new JSONObject(json);

                Log.d(TAG, "ParseJSON: key" + jsonObj.getString("title"));

                return jsonObj.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }

    private ArrayList<HashMap<String, String>> ParseJSON(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObj = new JSONObject(json);

                Log.d(TAG, "ParseJSON: key" + jsonObj.getString("title"));
                // Getting JSON Array node
                JSONArray rows = jsonObj.getJSONArray(TAG_ROWS);

                // looping through All Students
                for (int i = 0; i < rows.length(); i++) {
                    JSONObject c = rows.getJSONObject(i);

                    String title = c.getString(TAG_TITLE);
                    String desc = c.getString(TAG_DESC);
                    String imgref = c.getString(TAG_IMG);

                    // tmp hashmap for single row
                    HashMap<String, String> row = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    row.put(TAG_TITLE, title);
                    row.put(TAG_DESC, desc);
                    row.put(TAG_IMG, imgref);

                    // adding student to students list
                    rowList.add(row);
                }
                return rowList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }
    public View.OnClickListener listener=new View.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            Log.d(TAG, "onClick: Refresh Button");
            //Refresh cache directory downloaded images
            adapter.imageLoader.clearCache();
            adapter.notifyDataSetChanged();
        }
    };
}
