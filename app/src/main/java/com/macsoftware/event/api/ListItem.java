package com.macsoftware.event.api;

import java.util.Collection;

public class ListItem {
    private String text;
    private String link;

    public ListItem(String text, String link) {
        this.text = text;
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

}