package com.example.rangoo.Interfaces;

import com.example.rangoo.Model.Food;

import java.util.ArrayList;

public interface GetListCallback {
    public void onSuccess(ArrayList<Food> list);
    public void onError(String error);
}
