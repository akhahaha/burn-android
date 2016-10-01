package com.ucla.burn.android.data;

import com.ucla.burn.android.model.User;

import firebomb.Firebomb;
import java8.util.function.Consumer;
import java8.util.function.Function;

public class BurnDAO {
    public static void findUser(String userId, final Callback<User> callback) {
        Firebomb.getInstance().find(User.class, userId).thenAccept(new Consumer<User>() {
            @Override
            public void accept(User user) {
                if (callback != null) callback.onResponse(user);
            }
        }).exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                if (callback != null) callback.onFailed(new Exception(throwable));
                return null;
            }
        });
    }

    public static void createUser(User user, final Callback<Void> callback) {
        Firebomb.getInstance().persist(user).thenAccept(new Consumer<User>() {
            @Override
            public void accept(User user) {
                if (callback != null) callback.onResponse(null);
            }
        }).exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                if (callback != null) callback.onFailed(new Exception(throwable));
                return null;
            }
        });
    }
}
