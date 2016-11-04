package edu.xored.tracker;

public class User {
    private String name;
    private String eMail;

    public User() {
        this(null, null);
    }

    public User(String name, String eMail) {
        this.name = name;
        this.eMail = eMail;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}