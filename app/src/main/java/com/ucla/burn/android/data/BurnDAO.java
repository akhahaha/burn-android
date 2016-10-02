package com.ucla.burn.android.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ucla.burn.android.model.Conversation;
import com.ucla.burn.android.model.Message;
import com.ucla.burn.android.model.Suggestion;
import com.ucla.burn.android.model.User;

import java.util.ArrayList;
import java.util.List;

import firebomb.Firebomb;
import firebomb.database.FirebaseData;
import java8.util.concurrent.CompletableFuture;
import java8.util.function.Consumer;
import java8.util.function.Function;

public class BurnDAO {
    public static void upsertUser(User user, final Callback<Void> callback) {
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

    public static void getUser(String userId, final Callback<User> callback) {
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

    public static void pushMessage(Message message, final Callback<Message> callback) {
        message.setId(null);
        Firebomb.getInstance().persist(message).thenAccept(new Consumer<Message>() {
            @Override
            public void accept(Message message) {
                if (callback != null) callback.onResponse(message);
            }
        }).exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                if (callback != null) callback.onFailed(new Exception(throwable));
                return null;
            }
        });
    }

    public static void updateMessage(Message message, final Callback<Message> callback) {
        if (message.getId() == null) {
            if (callback != null) callback.onFailed(new Exception("Update cannot have null Id."));
            return;
        }

        Firebomb.getInstance().persist(message).thenAccept(new Consumer<Message>() {
            @Override
            public void accept(Message message) {
                if (callback != null) callback.onResponse(message);
            }
        }).exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                if (callback != null) callback.onFailed(new Exception(throwable));
                return null;
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

    public static void pushSuggestion(Suggestion suggestion, final Callback<Suggestion> callback) {
        suggestion.setId(null);
        Firebomb.getInstance().persist(suggestion).thenAccept(new Consumer<Suggestion>() {
            @Override
            public void accept(Suggestion suggestion) {
                if (callback != null) callback.onResponse(suggestion);
            }
        }).exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                if (callback != null) callback.onFailed(new Exception(throwable));
                return null;
            }
        });
    }

    public static void updateSuggestion(Suggestion suggestion,
                                        final Callback<Suggestion> callback) {
        if (suggestion.getId() == null) {
            if (callback != null) callback.onFailed(new Exception("Update cannot have null Id."));
            return;
        }

        Firebomb.getInstance().persist(suggestion).thenAccept(new Consumer<Suggestion>() {
            @Override
            public void accept(Suggestion suggestion) {
                if (callback != null) callback.onResponse(suggestion);
            }
        }).exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                if (callback != null) callback.onFailed(new Exception(throwable));
                return null;
            }
        });
    }

    public static void getSuggestion(String id, final Callback<Suggestion> callback) {
        Firebomb.getInstance().find(Suggestion.class, id)
                .thenAccept(new Consumer<Suggestion>() {
                    @Override
                    public void accept(Suggestion suggestion) {
                        if (callback != null) callback.onResponse(suggestion);
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

    public static void getConversationMessages(String conversationId,
                                               final Callback<List<Message>> callback) {
        Firebomb.getInstance().find(Conversation.class, conversationId)
                .thenAccept(new Consumer<Conversation>() {
                    @Override
                    public void accept(Conversation conversation) {
                        if (conversation == null || conversation.getMessages().isEmpty()) {
                            if (callback != null) callback.onResponse(new ArrayList<Message>());
                            return;
                        }

                        final List<Message> messages = new ArrayList<>();
                        CompletableFuture<?>[] messagePromises =
                                new CompletableFuture<?>[conversation.getMessages().size()];
                        for (int i = 0; i < conversation.getMessages().size(); i++) {
                            messagePromises[i] = Firebomb.getInstance().find(Message.class,
                                    conversation.getMessages().get(i).getId())
                                    .thenAccept(new Consumer<Message>() {
                                        @Override
                                        public void accept(Message message) {
                                            messages.add(message);
                                        }
                                    }).exceptionally(new Function<Throwable, Void>() {
                                        @Override
                                        public Void apply(Throwable throwable) {
                                            throwable.printStackTrace();
                                            return null;
                                        }
                                    });
                        }

                        CompletableFuture.allOf(messagePromises)
                                .thenAccept(new Consumer<Void>() {
                                    @Override
                                    public void accept(Void aVoid) {
                                        if (callback != null) callback.onResponse(messages);
                                    }
                                });
                    }
                });
    }

    public static void getConversationSuggestions(String conversationId,
                                                  final Callback<List<Suggestion>> callback) {
        Firebomb.getInstance().find(Conversation.class, conversationId)
                .thenCompose(new Function<Conversation, CompletableFuture<Message>>() {
                    @Override
                    public CompletableFuture<Message> apply(Conversation conversation) {
                        if (conversation == null || conversation.getMessages().isEmpty()) {
                            if (callback != null) callback.onResponse(new ArrayList<Suggestion>());
                            return null;
                        }

                        // Get last message Id
                        String messageId = conversation.getMessages()
                                .get(conversation.getMessages().size() - 1).getId();
                        return Firebomb.getInstance().find(Message.class, messageId);
                    }
                })
                .thenAccept(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) {
                        if (message.isContext() || message.getSuggestions().isEmpty()) {
                            if (callback != null) callback.onResponse(new ArrayList<Suggestion>());
                            return;
                        }

                        final List<Suggestion> suggestions = new ArrayList<Suggestion>();
                        CompletableFuture<?>[] suggestionPromises =
                                new CompletableFuture<?>[message.getSuggestions().size()];
                        for (int i = 0; i < message.getSuggestions().size(); i++) {
                            suggestionPromises[i] = Firebomb.getInstance().find(Suggestion.class,
                                    message.getSuggestions().get(i).getId())
                                    .thenAccept(new Consumer<Suggestion>() {
                                        @Override
                                        public void accept(Suggestion suggestion) {
                                            suggestions.add(suggestion);
                                        }
                                    });
                        }

                        CompletableFuture.allOf(suggestionPromises)
                                .thenAccept(new Consumer<Void>() {
                                    @Override
                                    public void accept(Void aVoid) {
                                        if (callback != null) callback.onResponse(suggestions);
                                    }
                                });
                    }
                });
    }
}
