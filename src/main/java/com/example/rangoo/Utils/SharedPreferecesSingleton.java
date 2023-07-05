package com.example.rangoo.Utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.rangoo.R;

public class SharedPreferecesSingleton {

    private static  SharedPreferecesSingleton instace;
    private static SharedPreferences sharedPreferences;
    private static Context appcontext;

    private SharedPreferecesSingleton(Context context){
        sharedPreferences = context.getSharedPreferences(context.getString(R.string._COM_RANGO_PREFERENCES), MODE_PRIVATE);
    }

    public static SharedPreferecesSingleton getInstance(Context context){
        if (instace == null){
            appcontext = context.getApplicationContext();
            instace = new SharedPreferecesSingleton(appcontext);
        }
        return instace;
    }

    public void setUserID(String userID){
        sharedPreferences.edit()
                .putString("UID", userID).apply();
        setIsLoggedIn(true);
    }

    public String getUserID(){
        return  sharedPreferences.getString("UID", "");
    }

    public void setIsLoggedIn(boolean status){
        sharedPreferences.edit()
                .putBoolean("isLoggedIn", status).apply();
    }

    public boolean getLoggedIn(){
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
}
