package com.ucla.burn.android.model;

import java.util.ArrayList;
import java.util.List;

import firebomb.annotation.Entity;
import firebomb.annotation.Id;
import firebomb.annotation.NonNull;
import firebomb.annotation.OneToMany;

@Entity
public class User {
    private String id;
    private String displayName;
    private String email;
    private List<Conversation> conversations = new ArrayList<>();
    private List<Suggestion> suggestions = new ArrayList<>();

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(foreignFieldName = "owner")
    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    @OneToMany(foreignFieldName = "suggester")
    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }
}
