package com.example.rangoo.Utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.rangoo.Activities.DetailsActivity;
import com.example.rangoo.Activities.EmptyHomeActivity;
import com.example.rangoo.Activities.HomeListActivity;
import com.example.rangoo.Activities.ProfileActivity;
import com.example.rangoo.Activities.WeekMenuActivity;
import com.example.rangoo.Interfaces.GetListCallback;
import com.example.rangoo.Model.Food;
import com.example.rangoo.Network.FirebaseNetwork;
import com.example.rangoo.R;


import java.util.ArrayList;

public class GoTo {

    private static String UID;
    private static ArrayList<Food> list;

    public static void setUID(String uid){ UID = uid; }

    public static void setList(ArrayList<Food> foods) { list = foods; }

    public  static void homeView(Activity activity){

        setUID(SharedPreferecesSingleton.getInstance(activity.getApplicationContext()).getUserID());

        FirebaseNetwork.getUserList(UID, new GetListCallback() {

                @Override
                public void onSuccess(ArrayList<Food> list) {
                    if(list.size() > 0){
                        setList(list);
                        activity.startActivity(new Intent(activity, HomeListActivity.class)
                                .putExtra(activity.getString(R.string.USER_LIST), list).putExtra("USER_ID", UID));
                    }
                    activity.finish();
                }

                @Override
                public void onError(String error) {
                    activity.startActivity(new Intent(activity, EmptyHomeActivity.class).putExtra("USER_ID", UID));
                    activity.finish();
                    Log.e("USER_LIST_ERROR", error);
                }}
        );
    }

    private static void homeViewEmpty(Activity activity){
        activity.startActivity(new Intent(activity, EmptyHomeActivity.class));
    }

    private static void homeViewList(Activity activity){
        activity.startActivity(new Intent(activity, HomeListActivity.class));
    }

    public static void signInView(Activity activity){
        SharedPreferecesSingleton.getInstance(activity.getApplicationContext()).setIsLoggedIn(false);
        if (activity.getPackageName().equals("com.example.rangoo.admin")){
            activity.startActivity(new Intent(activity, LoginActivity.class));
        }else{
            activity.startActivity(new Intent(activity, LoginActivity.class));
        }

        activity.finish();
    }

    public static void weekMenu(Activity activity, ArrayList<Food> list){
        activity.startActivity(new Intent(activity, WeekMenuActivity.class)
                .putExtra(activity.getString(R.string.USER_ID), UID)
                .putExtra(activity.getString(R.string.USER_LIST), list));
    }

    public static void detailsView(Activity activity, Food food){
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(activity.getString(R.string._FOOD_TO_DETAIL), food);
        activity.startActivity(intent);
    }

    public static void profileView(Activity activity){
        setUID(SharedPreferecesSingleton.getInstance(activity.getApplicationContext()).getUserID());
        activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra(activity.getString(R.string.USER_ID), UID));
    }

}
