package com.ucla.burn.android.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import firebomb.annotation.Entity;
import firebomb.annotation.GeneratedValue;
import firebomb.annotation.Id;
import firebomb.annotation.Ignore;
import firebomb.annotation.ManyToMany;
import firebomb.annotation.ManyToOne;
import firebomb.annotation.NonNull;
import firebomb.annotation.OneToMany;
import firebomb.annotation.Property;

@Entity
public class Conversation {
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm zzz";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, Locale.US);

    private String id;
    private User owner;
    private String title;
    private String primaryName;
    private String secondaryName;
    private boolean isCompleted = false;
    private Date lastActive;
    private List<Message> messages = new ArrayList<>();
    private List<User> upvotingUsers = new ArrayList<>();

    @Id
    @GeneratedValue
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(foreignIndexName = "conversations")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @NonNull
    public String getPrimaryName() {
        return primaryName;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    @NonNull
    public String getSecondaryName() {
        return secondaryName;
    }

    public void setSecondaryName(String secondaryName) {
        this.secondaryName = secondaryName;
    }

    @NonNull
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Ignore
    public Date getLastActive() {
        return lastActive;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    @Property("lastActive")
    @NonNull
    public String getLastActiveString() {
        if (lastActive == null) {
            return null;
        }
        return SDF.format(lastActive);
    }

    public void setLastActiveString(String lastActiveString) {
        try {
            this.lastActive = SDF.parse(lastActiveString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OneToMany(foreignFieldName = "conversation")
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        message.setIndex(messages.size());
        messages.add(message);
    }

    public int getScore() {
        return upvotingUsers.size();
    }

    public boolean hasVote(User user) {
        return upvotingUsers.contains(user);
    }

    public void setVote(User user, boolean isUpVote) {
        if (isUpVote) {
            upvotingUsers.add(user);
        } else {
            upvotingUsers.remove(user);
        }
    }

    @ManyToMany(foreignIndexName = "conversationUpvotes")
    public List<User> getUpvotingUsers() {
        return upvotingUsers;
    }

    public void setUpvotingUsers(List<User> upvotingUsers) {
        this.upvotingUsers = upvotingUsers;
    }
}
