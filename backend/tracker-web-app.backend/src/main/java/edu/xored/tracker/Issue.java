package edu.xored.tracker;

import java.util.ArrayList;
import java.util.Collection;

public class Issue {

    private long hash;
    private String summary;
    private String description;
    private Status status;
    private Collection<Comment> comments = new ArrayList<>();

    public Issue(long hash, String summary, String description, Status status) {
        this.hash = hash;
        this.summary = summary;
        this.description = description;
        this.status = status;
    }

    public long getHash() {
        return hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Collection<Comment> getAllComments() {
        return comments;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void addComments(Collection<Comment> comments){
        this.comments.addAll(comments);
    }

    public enum Status {
        OPEN, CLOSED;
    }
}
