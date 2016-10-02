package com.ucla.burn.android.data;

import android.telecom.Call;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ucla.burn.android.model.Conversation;
import com.ucla.burn.android.model.Message;
import com.ucla.burn.android.model.User;

import java.util.List;

import firebomb.Firebomb;
import firebomb.database.FirebaseData;
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

    public static void pushConversation(Conversation conversation,
                                        final Callback<Conversation> callback) {
        conversation.setId(null);
        Firebomb.getInstance().persist(conversation).thenAccept(new Consumer<Conversation>() {
            @Override
            public void accept(Conversation conversation) {
                if (callback != null) callback.onResponse(conversation);
            }
        }).exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                if (callback != null) callback.onFailed(new Exception(throwable));
                return null;
            }
        });
    }

    public static void updateConversation(Conversation conversation,
                                          final Callback<Conversation> callback) {
        if (conversation.getId() == null) {
            if (callback != null) callback.onFailed(new Exception("Update cannot have null Id."));
            return;
        }

        Firebomb.getInstance().persist(conversation).thenAccept(new Consumer<Conversation>() {
            @Override
            public void accept(Conversation conversation) {
                if (callback != null) callback.onResponse(conversation);
            }
        }).exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                if (callback != null) callback.onFailed(new Exception(throwable));
                return null;
            }
        });
    }

    public static void getConversation(String id, final Callback<Conversation> callback) {
        Firebomb.getInstance().find(Conversation.class, id)
                .thenAccept(new Consumer<Conversation>() {
                    @Override
                    public void accept(Conversation conversation) {
                        if (callback != null) callback.onResponse(conversation);
                    }
                })
                .exceptionally(new Function<Throwable, Void>() {
                    @Override
                    public Void apply(Throwable throwable) {
                        if (callback != null) callback.onFailed(new Exception(throwable));
                        return null;
                    }
                });
    }

    public static void getConversations(final Callback<List<Conversation>> callback) {
        FirebaseDatabase.getInstance().getReference().child("v1/conversations")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (callback != null) try {
                            callback.onResponse(Firebomb.parseEntityList(
                                    Conversation.class, (new FirebaseData(dataSnapshot)).getChildren()));
                        } catch (InstantiationException e) {
                            if (callback != null) callback.onFailed(e);
                        } catch (IllegalAccessException e) {
                            if (callback != null) callback.onFailed(e);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (callback != null) callback.onFailed(databaseError.toException());
                    }
                });
    }

    public static void getMessage(String id, final Callback<Message> callback) {
        Firebomb.getInstance().find(Message.class, id)
                .thenAccept(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) {
                        if (callback != null) callback.onResponse(message);
                    }
                })
                .exceptionally(new Function<Throwable, Void>() {
                    @Override
                    public Void apply(Throwable throwable) {
                        if (callback != null) callback.onFailed(new Exception(throwable));
                        return null;
                    }
                });
    }
}
