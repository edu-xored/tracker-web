package edu.xored.tracker;

import java.time.LocalDateTime;

public class Comment {

    private String author;
    private LocalDateTime createdDateTime;
    private String content;

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment() {
        createdDateTime = LocalDateTime.now();
    }

    public Comment(String author) {
        this();

        this.author = author;
    }

    public Comment(String author, String content) {
        this(author);

        this.content = content;
    }
}
