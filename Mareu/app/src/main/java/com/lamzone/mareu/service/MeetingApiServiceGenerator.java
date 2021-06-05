package com.lamzone.mareu.service;

import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MeetingApiServiceGenerator {

    public static List<User> DUMMY_USER = Arrays.asList(
        new User(1, "maxime"),
        new User(2, "paul"),
        new User(3, "amandine"),
        new User(4, "alex"),
        new User(5, "caroline"),
        new User(6, "viviane"),
        new User(7, "luc")
    );

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
        new Meeting(1, "sujet1", "salle1", "14:00", Arrays.asList(
                DUMMY_USER.get(0),
                DUMMY_USER.get(1),
                DUMMY_USER.get(2)
                )),
        new Meeting(2, "sujet2", "salle2", "13:00", Arrays.asList(
                DUMMY_USER.get(3),
                DUMMY_USER.get(4),
                DUMMY_USER.get(5)
        )),
        new Meeting(3, "sujet3", "salle3", "16:00", Arrays.asList(
                DUMMY_USER.get(6),
                DUMMY_USER.get(2),
                DUMMY_USER.get(4)
        ))
    );

    static List<Meeting> generateMeeting() {
        return new ArrayList<>(DUMMY_MEETING);
    }
}