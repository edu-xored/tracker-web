package com.edu.xored.tracker;

public class Issue {

    private long hash;
    private String summary;
    private String description;
    private String status;

    public Issue(long hash, String summary, String description, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
