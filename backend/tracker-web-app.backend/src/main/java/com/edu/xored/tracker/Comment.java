package com.edu.xored.tracker;

import java.time.LocalDateTime;

public class Comment {

    //region Fields

    private String author;
    private LocalDateTime createdDateTime;
    private String content;

    //endregion Fields

    //region Get

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public String getContent() {
        return content;
    }

    //endregion Get

    //region Set

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //endregion Set

    //region Constructors

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

    //endregion Constructors

}
