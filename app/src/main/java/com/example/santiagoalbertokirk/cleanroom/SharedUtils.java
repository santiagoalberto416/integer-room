package com.example.santiagoalbertokirk.cleanroom;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by santiagoalbertokirk on 21/10/17.
 */

public class SharedUtils {

    private final static String KEY_IP_DEFAULT = "ipdefault";
    private String mDefaultIP = "192.168.1.70";
    private static SharedUtils myObj;
    /**
     * Create private constructor
     */
    private SharedUtils(){

    }
    /**
     * Create a static method to get instance.
     */
    public static SharedUtils getInstance(){
        if(myObj == null){
            myObj = new SharedUtils();
        }
        return myObj;
    }

    public void setIpDefault(Context context, String ip){
        // do something here
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.file_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_IP_DEFAULT, ip );
        editor.commit();
    }

    public String getRawIP(Context context){
        // do something here
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.file_preferences), Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_IP_DEFAULT, mDefaultIP);
    }

    public String getIpDefault(Context context ){
        // do something here
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.file_preferences), Context.MODE_PRIVATE);
        return "http://"+ sharedPref.getString(KEY_IP_DEFAULT, mDefaultIP)+"/";
    }

    public boolean isValidURL(String url) {
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }
        try {
            u.toURI();
        } catch (URISyntaxException e) {
            return false;
        }
        return true;
    }
}




