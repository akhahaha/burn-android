package com.ucla.burn.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.FirebaseDatabase;
import com.ucla.burn.android.data.BurnDAO;
import com.ucla.burn.android.data.Callback;
import com.ucla.burn.android.model.User;

import firebomb.Firebomb;
import firebomb.database.FirebaseManager;

/**
 * Session singleton
 * Created by Alan on 10/1/2016.
 * <p>
 * Requires initialize() to be performed at least once using an Android context.
 */
public class Session {
    private static Session ourInstance = new Session();

    public static Session getInstance() {
        if (!ourInstance.initialized) {
            new Exception("Session must be initialized with context before being used.")
                    .printStackTrace();
        }

        return ourInstance;
    }

    private Boolean initialized = false;
    private SharedPreferences preferences;
    private User currentUser;

    private Session() {
    }

    public static void initialize(Context context) {
        Firebomb.initialize(new FirebaseManager(FirebaseDatabase.getInstance()), "v1");

        ourInstance.preferences = context.getSharedPreferences(context.getString(
                R.string.pref_file_name),
                Context.MODE_PRIVATE);
        ourInstance.initialized = true;
        ourInstance.restoreSession(context);
    }

    public void saveSession(Context context) {
        SharedPreferences.Editor editor = preferences.edit();
        if (currentUser != null) {
            editor.putString(context.getString(R.string.pref_key_curr_uid),
                    currentUser.getId()).apply();
        } else {
            editor.remove(context.getString(R.string.pref_key_curr_uid));
        }
    }

    public void restoreSession(Context context, final Callback<Boolean> callback) {
        String currentUserID = preferences.getString(context.getString(R.string.pref_key_curr_uid),
                null);
        if (currentUserID == null) {
            if (callback != null) callback.onResponse(false);
            return;
        }

        BurnDAO.findUser(currentUserID, new Callback<User>() {
            @Override
            public void onResponse(User user) {
                if (user != null) {
                    currentUser = user;
                    if (callback != null) callback.onResponse(true);
                } else if (callback != null) {
                    callback.onResponse(false);
                }
            }

            @Override
            public void onFailed(Exception e) {
                if (callback != null) callback.onFailed(e);
            }
        });
    }

    public void restoreSession(Context context) {
        restoreSession(context, null);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Context context, User currentUser) {
        this.currentUser = currentUser;
        saveSession(context);
    }

    public Boolean isActive() {
        return currentUser != null;
    }
}
