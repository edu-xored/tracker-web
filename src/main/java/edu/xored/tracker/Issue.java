package edu.xored.tracker;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Issue {

    private String hash;
    private String summary;
    private String description;
    private User author;
    private Status status;
    private LocalDateTime createdDateTime;

    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    public Issue() {
    }

    public Issue(String hash, String summary, String description, Status status) {
        this.hash = hash;
        this.summary = summary;
        this.description = description;
        this.status = status;
        this.createdDateTime = LocalDateTime.now();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public void addComment(Comment comment) {
        if (comment != null) {
            comments.add(comment);
        }
    }

    public void addComments(Collection<Comment> comments) {
        if (comments != null) {
            this.comments.addAll(comments);
        }
    }

    public Issue updateIssue(Issue other) {
        if (other.getSummary() != null) {
            setSummary(other.getSummary());
        }
        if (other.getDescription() != null) {
            setDescription(other.getDescription());
        }
        if (other.getAuthor() != null) {
            setAuthor(other.getAuthor());
        }
        if (other.getStatus() != null) {
            setStatus(other.getStatus());
        }
        if (other.getCreatedDateTime() != null) {
            setCreatedDateTime(other.getCreatedDateTime());
        }
        if (other.getComments() != null) {
            addComments(other.getComments());
        }
        return this;
    }

    public enum Status {
        OPEN, RESOLVED;
    }
}
