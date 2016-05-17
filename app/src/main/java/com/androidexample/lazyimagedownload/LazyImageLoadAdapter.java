package com.androidexample.lazyimagedownload;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JO265962 on 5/17/2016.
 */
//Adapter class extends with BaseAdapter and implements with OnClickListener
public class LazyImageLoadAdapter extends BaseAdapter implements OnClickListener{
    
    private Activity activity;
    private String[] data;
    private String[] data_title;
    private String[] data_desc;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
    ArrayList<String> mStrings_img;
    ArrayList<String> mStrings_title;
    ArrayList<String> mStrings_desc;
    // JSON Node names
    private static final String TAG_ROWS = "rows";
    private static final String TAG_TITLE = "title";
    private static final String TAG_DESC = "description";
    private static final String TAG_IMG = "imageHref";
    String TAG = "LazyImageLoadAdapter";


    public LazyImageLoadAdapter(Activity a, String d) {
        activity = a;

        //Get image URL from JSON
        mStrings_img = ParseJSON_img(d);
        String[] myArray = new String[mStrings_img.size()];
        data=mStrings_img.toArray(myArray);
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        // Create ImageLoader object to download and show image in list
        // Call ImageLoader constructor to initialize FileCache
        imageLoader = new ImageLoader(activity.getApplicationContext());

        //Get Description from JSON
        mStrings_desc = ParseJSON_desc(d);
        String[] myArray_desc = new String[mStrings_desc.size()];
        data_desc=mStrings_desc.toArray(myArray_desc);

        //Get Title from JSON
        mStrings_title = ParseJSON_title(d);
        String[] myArray_title = new String[mStrings_title.size()];
        data_title=mStrings_title.toArray(myArray_title);
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
         
        public TextView text;
        public TextView text1;
        public TextView textWide;
        public ImageView image;
 
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	
        View vi=convertView;
        ViewHolder holder;
         
        if(convertView==null){
             
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.listview_row, null);
             
            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.text);
            holder.text1=(TextView)vi.findViewById(R.id.text1);
            holder.image=(ImageView)vi.findViewById(R.id.image);
             
           /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else 
            holder=(ViewHolder)vi.getTag();
        
        
        holder.text.setText(data_title[position]);
        holder.text1.setText(data_desc[position]);
        ImageView image = holder.image;
        
        //DisplayImage function from ImageLoader Class
        imageLoader.DisplayImage(data[position], image);
        
        /******** Set Item Click Listner for LayoutInflater for each row ***********/
        vi.setOnClickListener(new OnItemClickListener(position));
        return vi;
    }

    private ArrayList<String> ParseJSON_img(String json) {
        if (json != null) {
            try {
                ArrayList<String> imagear = new ArrayList<String>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONArray rows = jsonObj.getJSONArray(TAG_ROWS);

                // looping through All Students
                for (int i = 0; i < rows.length(); i++) {
                    JSONObject c = rows.getJSONObject(i);
                    String imgref = c.getString(TAG_IMG);
                    imagear.add(imgref);
                }
                return imagear;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }

    //Get Description from JSON
    private ArrayList<String> ParseJSON_desc(String json) {
        if (json != null) {
            try {
                ArrayList<String> descar = new ArrayList<String>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONArray rows = jsonObj.getJSONArray(TAG_ROWS);

                // looping through All Students
                for (int i = 0; i < rows.length(); i++) {
                    JSONObject c = rows.getJSONObject(i);
                    String desc = c.getString(TAG_DESC);
                    descar.add(desc);
                }
                return descar;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }

    //Get Title from JSON
    private ArrayList<String> ParseJSON_title(String json) {
        if (json != null) {
            try {
                ArrayList<String> titlear = new ArrayList<String>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONArray rows = jsonObj.getJSONArray(TAG_ROWS);

                // looping through All Students
                for (int i = 0; i < rows.length(); i++) {
                    JSONObject c = rows.getJSONObject(i);
                    String title = c.getString(TAG_TITLE);
                    titlear.add(title);
                }
                return titlear;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }

    @Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}
	
    
    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements OnClickListener{           
        private int mPosition;
        
       OnItemClickListener(int position){
        	 mPosition = position;
        }
        
        @Override
        public void onClick(View arg0) {
        	MainActivity sct = (MainActivity)activity;
            Log.d(TAG, "onClick: "+ data[mPosition]);
            Toast.makeText(sct,
                    "Image URL : "+data[mPosition],
                    Toast.LENGTH_LONG)
                    .show();
        }               
    }   
}