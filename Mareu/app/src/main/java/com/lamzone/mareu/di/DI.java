package com.lamzone.mareu.di;

import com.lamzone.mareu.service.ApiService;
import com.lamzone.mareu.service.MeetingApiService;

public class DI {

    private static final ApiService service = new MeetingApiService();

    public static ApiService getNeighbourApiService() {
        return service;
    }

    public static ApiService getNewInstanceApiService() {
        return new MeetingApiService();
    }
}
