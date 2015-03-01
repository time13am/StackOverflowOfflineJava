package org.herman.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    public int id;
    public String title;
    public String body;
    public List<Comment> comments;
    
    public Question(int id, String title){
        this.id = id;
        this.title = title;
        this.body = null;
        this.comments = null;
    }
    public Question(int id, String body, List<Comment> comments){
        this.id = id;
        this.title = null;
        this.body = body;
        this.comments = comments;
    }
    public Question(int id, String title, String body, List<Comment> comments){
        this.id = id;
        this.title = title;
        this.body = body;
        this.comments = comments;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
