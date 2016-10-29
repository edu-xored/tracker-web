package edu.xored.tracker;

import java.time.LocalDateTime;

public class Comment {

    private String author;
    private LocalDateTime createdDateTime;
    private String content;

    public Comment() {
        author = "Anonymous";
        createdDateTime = LocalDateTime.now();
    }

    public Comment(String author, String content) {
        this.author = author;
        this.createdDateTime = LocalDateTime.now();
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
