package com.lamzone.mareu.model;

import java.util.List;

public class Meeting {

    private long id;

    private String subject;

    private String location;

    private String hour;

    private List<User> participants;

    public Meeting(long id, String subject, String location, String hour, List<User> participants) {
        this.id = id;
        this.subject = subject;
        this.location = location;
        this.hour = hour;
        this.participants = participants;
    }

    public Meeting(String subject, String location, String hour, List<User> participants) {
        this.subject = subject;
        this.location = location;
        this.hour = hour;
        this.participants = participants;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
}
