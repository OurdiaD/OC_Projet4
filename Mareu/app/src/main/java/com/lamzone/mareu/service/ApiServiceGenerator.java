package com.lamzone.mareu.service;

import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ApiServiceGenerator {

    public static List<Room> DUMMY_ROOM = Arrays.asList(
        new Room(1, "Reunion A", "#ecd9d0"),
        new Room(2, "Reunion B", "#afceb8"),
        new Room(3, "Reunion C", "#8c9ea5"),
        new Room(4, "Reunion D", "#8d8ca5"),
        new Room(5, "Reunion E", "#a5978c"),
        new Room(6, "Reunion F", "#afcecc"),
        new Room(7, "Reunion G", "#ceafb6"),
        new Room(8, "Reunion H", "#ceafc9"),
        new Room(9, "Reunion I", "#afc4ce"),
        new Room(10, "Reunion J", "#b6ceaf")
    );

    public static List<User> DUMMY_USER = Arrays.asList(
        new User(1, "maxime@lamzone.com"),
        new User(2, "paul@lamzone.com"),
        new User(3, "amandine@lamzone.com"),
        new User(4, "alex@lamzone.com"),
        new User(5, "caroline@lamzone.com"),
        new User(6, "viviane@lamzone.com"),
        new User(7, "luc@lamzone.com")
    );

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
        new Meeting(1, "Peach", DUMMY_ROOM.get(0), 1621288800000L, "14:00", Arrays.asList(
                DUMMY_USER.get(0),
                DUMMY_USER.get(1),
                DUMMY_USER.get(2)
                )),
        new Meeting(2, "Mario", DUMMY_ROOM.get(1), 1626991200000L ,"13:00", Arrays.asList(
                DUMMY_USER.get(3),
                DUMMY_USER.get(4),
                DUMMY_USER.get(5)
        )),
        new Meeting(3, "Luigi", DUMMY_ROOM.get(2),  1633039200000L,"16:00", Arrays.asList(
                DUMMY_USER.get(6),
                DUMMY_USER.get(2),
                DUMMY_USER.get(4)
        ))
    );

    public static List<Meeting> generateMeeting() {
        return new ArrayList<>(DUMMY_MEETING);
    }
    public static List<User> generateUsers() {
        return new ArrayList<>(DUMMY_USER);
    }
    public static List<Room> generateRooms() {
        return new ArrayList<>(DUMMY_ROOM);
    }
}