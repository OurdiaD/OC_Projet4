package com.lamzone.mareu.model;

public class User {

    private long id;
    private String mail;

    public User(long id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    public User(String mail) {
        this.id = System.currentTimeMillis();
        this.mail = mail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return mail ;
    }
}
