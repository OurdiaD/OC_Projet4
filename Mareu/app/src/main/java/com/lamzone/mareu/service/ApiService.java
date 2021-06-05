package com.lamzone.mareu.service;

import com.lamzone.mareu.model.Meeting;

import java.util.List;

public interface ApiService {

    void createMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    List<Meeting> getMeeting();
}
