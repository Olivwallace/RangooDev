package com.example.rangoo.Interfaces;

import android.net.Uri;

public interface UriImageCallback {
    public void onSuccess(Uri uri);
    public void onError(String error);
}
