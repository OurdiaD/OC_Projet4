package com.lamzone.mareu.service;

import com.lamzone.mareu.model.Meeting;

import java.util.List;

public class MeetingApiService implements ApiService{

    private final List<Meeting> meetings = MeetingApiServiceGenerator.generateMeeting();

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
}
