package edu.xored.tracker;

import java.time.LocalDateTime;

public class Comment {

    private String author;
    private String content;
    private LocalDateTime createdDateTime;

    public Comment() {
        author = "Anonymous";
        createdDateTime = LocalDateTime.now();
    }

    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
        this.createdDateTime = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }
}
