package com.ucla.burn.android.model;

import java.util.List;

public class Message {
    private boolean id;
    private Conversation conversation;
    private int index;
    private boolean isContext;
    private boolean isPrimary;
    private String text;
    private String imgUrl;
    private String selectedSuggestionId;
    private List<Suggestion> suggestions;

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isContext() {
        return isContext;
    }

    public void setContext(boolean context) {
        isContext = context;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSelectedSuggestionId() {
        return selectedSuggestionId;
    }

    public void setSelectedSuggestionId(String selectedSuggestionId) {
        this.selectedSuggestionId = selectedSuggestionId;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }
}
