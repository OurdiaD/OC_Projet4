package com.lamzone.mareu.di;

import com.lamzone.mareu.service.meeting.MeetingApiService;
import com.lamzone.mareu.service.meeting.MainMeetingApiService;
import com.lamzone.mareu.service.user.MainUserApiService;
import com.lamzone.mareu.service.user.UserApiService;

public class DI {

    private static final MeetingApiService service = new MainMeetingApiService();

    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    public static MeetingApiService getNewInstanceApiService() {
        return new MainMeetingApiService();
    }

    private static final UserApiService Userservice = new MainUserApiService();

    public static UserApiService getUserApiService() {
        return Userservice;
    }

    public static UserApiService getNewInstanceUserApiService() {
        return new MainUserApiService();
    }
}
