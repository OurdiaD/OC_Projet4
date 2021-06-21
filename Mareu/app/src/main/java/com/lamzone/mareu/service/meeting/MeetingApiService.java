package com.lamzone.mareu.service.meeting;

import androidx.annotation.Nullable;

import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.model.User;

import java.util.List;

public interface MeetingApiService {

    void createMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    List<Meeting> getMeeting();

    List<Room> getRooms();

    List<Meeting> filterMeeting(@Nullable String date, @Nullable String hour, @Nullable String room);
}
