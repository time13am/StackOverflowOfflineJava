package org.herman.model;

public class Comment {
    public String text;
    
    public Comment(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
