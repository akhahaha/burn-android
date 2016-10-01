package com.ucla.burn.android.model;

import java.util.Date;
import java.util.Set;

public class Suggestion {
    private String id;
    private Message message;
    private User suggester;
    private String text;
    private Date created;
    private Set<String> upvotingUserIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getSuggester() {
        return suggester;
    }

    public void setSuggester(User suggester) {
        this.suggester = suggester;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Set<String> getUpvotingUserIds() {
        return upvotingUserIds;
    }

    public void setUpvotingUsers(Set<String> upvotingUserIds) {
        this.upvotingUserIds = upvotingUserIds;
    }
}
