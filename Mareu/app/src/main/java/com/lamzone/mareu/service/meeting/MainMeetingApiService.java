package com.lamzone.mareu.service.meeting;

import androidx.annotation.Nullable;

import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.model.User;
import com.lamzone.mareu.service.ApiServiceGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public List<Meeting> filterMeeting(@Nullable String date, @Nullable String room) {
        List<Meeting> meetingsFilter = new ArrayList<>(meetings);
        if (date != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (Meeting meeting : meetings){
                //if (!Objects.equals(dateFormat.parse(date), dateFormat.parse(meeting.getDate()))){
                if (!Objects.equals(date, meeting.getDate())){
                    meetingsFilter.remove(meeting);
                }
            }
        }
        if (room != null){
            for (Meeting meeting : meetings){
                if (!Objects.equals(room, meeting.getLocation().getRoom())){
                    meetingsFilter.remove(meeting);
                }
            }
        }
        return meetingsFilter;
    }
}
