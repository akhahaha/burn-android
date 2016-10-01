package com.ucla.burn.android.data;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.ucla.burn.android.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Facebook API wrapper
 * Created by Alan on 10/1/2016.
 */
public class Facebook {
    /**
     * Gets a Burn User from a Facebook access token
     *
     * @param accessToken User's Facebook access token
     */
    public static void getUser(AccessToken accessToken, final Callback<User> callback) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject data, GraphResponse graphResponse) {
                        try {
                            User user = new User();
                            user.setId(data.getString("id"));
                            user.setDisplayName(data.getString("first_name"));
                            callback.onResponse(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
