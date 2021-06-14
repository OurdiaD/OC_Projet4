package com.lamzone.mareu.service.meeting;

import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.model.User;
import com.lamzone.mareu.service.ApiServiceGenerator;

import java.util.ArrayList;
import java.util.List;

public class MainMeetingApiService implements MeetingApiService {

    private final List<Meeting> meetings = ApiServiceGenerator.generateMeeting();

    private final List<Room> rooms = ApiServiceGenerator.generateRooms();

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
    public List<Room> getRooms() {
        return rooms;
    }


    public static List<String> getListString(List<?> data){
        List<String> list = new ArrayList<>();
        for (Object obj : data){
            if (obj instanceof User){
                list.add(((User) obj).getMail());
            } else if (obj instanceof Room){
                list.add(((Room) obj).getRoom());
            }
        }
        return list;
    }
}
