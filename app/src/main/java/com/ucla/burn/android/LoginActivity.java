package com.ucla.burn.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ucla.burn.android.data.BurnDAO;
import com.ucla.burn.android.data.Callback;
import com.ucla.burn.android.data.Facebook;
import com.ucla.burn.android.model.User;

public class LoginActivity extends Activity {
    private Session session;
    private CallbackManager callbackManager = CallbackManager.Factory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = Session.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

        // Configure Facebook login button
        LoginButton loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        if (loginButton != null) {
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Facebook.getUser(loginResult.getAccessToken(), new Callback<User>() {
                        @Override
                        public void onResponse(final User fbUser) {
                            BurnDAO.findUser(fbUser.getId(), new Callback<User>() {
                                @Override
                                public void onResponse(final User burnUser) {
                                    if (burnUser != null) {
                                        // Pre-existing user
                                        session.setCurrentUser(getApplicationContext(), burnUser);
                                        startActivity(new Intent(getApplicationContext(),
                                                ShellActivity.class));
                                        finish();
                                    } else {
                                        // New user
                                        BurnDAO.createUser(fbUser, new Callback<Void>() {
                                            @Override
                                            public void onResponse(Void aVoid) {
                                                session.setCurrentUser(
                                                        getApplicationContext(), fbUser);
                                                // TODO: Perform onboarding
                                                startActivity(new Intent(getApplicationContext(),
                                                        ShellActivity.class));
                                                finish();
                                            }

                                            @Override
                                            public void onFailed(Exception e) {
                                                e.printStackTrace();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailed(Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }

                        @Override
                        public void onFailed(Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    error.printStackTrace();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data); // Notify Facebook callback
    }
}
