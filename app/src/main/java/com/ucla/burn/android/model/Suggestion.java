package com.ucla.burn.android.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import firebomb.annotation.Entity;
import firebomb.annotation.GeneratedValue;
import firebomb.annotation.Id;
import firebomb.annotation.Ignore;
import firebomb.annotation.ManyToOne;
import firebomb.annotation.NonNull;

@Entity
public class Suggestion {
    private String id;
    private Message message;
    private User suggester;
    private String text;
    private Date created;
    private Set<String> upvotingUserIds = new HashSet<>();

    @Id
    @GeneratedValue
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(foreignIndexName = "suggestions")
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @ManyToOne(foreignIndexName = "suggestions")
    public User getSuggester() {
        return suggester;
    }

    public void setSuggester(User suggester) {
        this.suggester = suggester;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NonNull
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Ignore
    public int getScore() {
        return upvotingUserIds.size();
    }

    public Set<String> getUpvotingUserIds() {
        return upvotingUserIds;
    }

    public void setUpvotingUsers(Set<String> upvotingUserIds) {
        this.upvotingUserIds = upvotingUserIds;
        if (upvotingUserIds == null) {
            upvotingUserIds = new HashSet<>();
        }
    }
}
