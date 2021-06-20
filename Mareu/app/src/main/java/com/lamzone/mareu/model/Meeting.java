package com.lamzone.mareu.model;

import java.util.List;

public class Meeting {

    private long id;

    private String subject;

    private Room location;

    private String date;

    private String hour;

    private List<User> participants;

    public Meeting(long id, String subject, Room location, String date, String hour, List<User> participants) {
        this.id = id;
        this.subject = subject;
        this.location = location;
        this.date = date;
        this.hour = hour;
        this.participants = participants;
    }

    public Meeting(String subject, Room location, String hour, List<User> participants) {
        this.id = System.currentTimeMillis();
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

    public Room getLocation() {
        return location;
    }

    public void setLocation(Room location) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
