package com.lamzone.mareu.model;

public class Room {
    private long id;
    private String room;
    private String color;

    public Room(long id, String room, String color) {
        this.id = id;
        this.room = room;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return room ;
    }
}
