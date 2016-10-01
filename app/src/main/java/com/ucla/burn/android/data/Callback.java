package com.ucla.burn.android.data;

/**
 * Callback interface
 * Created by Alan on 8/2/2016.
 */
public interface Callback<T> {
    void onResponse(T response);

    void onFailed(Exception e);
}
