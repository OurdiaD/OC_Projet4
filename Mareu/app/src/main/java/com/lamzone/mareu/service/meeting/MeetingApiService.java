package com.lamzone.mareu.service.meeting;

import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.model.User;

import java.util.List;

public interface MeetingApiService {

    void createMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    List<Meeting> getMeeting();


    List<Room> getRooms();
}
