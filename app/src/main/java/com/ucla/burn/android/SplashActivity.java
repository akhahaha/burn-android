package com.ucla.burn.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ucla.burn.android.data.Callback;

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Session.initialize(getApplicationContext());
        final Session session = Session.getInstance();

        // Verify login status
        session.restoreSession(getApplicationContext(), new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean success) {
                if (success && session.isActive()) {
                    // Logged in
                    startActivity(new Intent(getApplicationContext(), ShellActivity.class));
                    finish();
                } else {
                    // Not logged in
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }
}
