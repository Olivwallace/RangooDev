package com.example.rangoo.Interfaces;

import com.google.firebase.database.DataSnapshot;

public interface GetDataCallback {
    public void onSuccess(DataSnapshot data);
    public void onError(String error);
}
