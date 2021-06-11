package com.lamzone.mareu.service;

import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.model.User;

import java.util.ArrayList;
import java.util.List;

public class MeetingApiService implements ApiService{

    private final List<Meeting> meetings = MeetingApiServiceGenerator.generateMeeting();
    private final List<User> users = MeetingApiServiceGenerator.generateUsers();
    private final List<Room> rooms = MeetingApiServiceGenerator.generateRooms();

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public List<Meeting> getMeeting() {
        return meetings;
    }
    @Override
    public List<User> getUsers() {
        return users;
    }
    @Override
    public List<Room> getRooms() {
        return rooms;
    }

/*
    public static List<String> getListString(List<Object> data){
        List<String> list = new ArrayList<>();
        for (Object obj : data){
            if (obj instanceof User){
                list.add(((User) obj).getMail());
            } else if (obj instanceof Room){
                list.add(((Room) obj).getRoom());
            }
        }
        return list;
    }*/
}
