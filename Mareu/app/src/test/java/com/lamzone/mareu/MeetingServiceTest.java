package com.lamzone.mareu;

import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.service.ApiServiceGenerator;
import com.lamzone.mareu.service.meeting.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

import static com.lamzone.mareu.service.ApiServiceGenerator.DUMMY_ROOM;
import static com.lamzone.mareu.service.ApiServiceGenerator.DUMMY_USER;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingApiService service;
    private static int sizeBaseList;
    private List<Meeting> baseList;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
        baseList = ApiServiceGenerator.DUMMY_MEETING;
        sizeBaseList = baseList.size();

    }

    @Test
    public void getAllMeetingTest() {
        List<Meeting> meetings = service.getMeeting();
        assertTrue(meetings.size() == sizeBaseList && meetings.containsAll(baseList));
    }

    @Test
    public void deleteMeetingTest() {
        Meeting meetingToDelete = service.getMeeting().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeeting().contains(meetingToDelete));
    }

    @Test
    public void createMeetingTest() {
        Meeting meeting = new Meeting(
                System.currentTimeMillis(),
                "Link",
                DUMMY_ROOM.get(5),
                "18/06/2021"/*1621288800000L*/,
                "12:00",
                Arrays.asList(DUMMY_USER.get(5), DUMMY_USER.get(1), DUMMY_USER.get(2))
        );
        service.createMeeting(meeting);
        List<Meeting> meetings = service.getMeeting();
        assertTrue(sizeBaseList + 1 == meetings.size() && meetings.contains(meeting));
    }

    @Test
    public void filterRoomTest(){
        Meeting meetingSearch = service.getMeeting().get(0);
        List<Meeting> result = service.filterMeeting(
                null,
                null,
                meetingSearch.getLocation().getRoom());
        assertTrue(result.size() == 2 && result.contains(meetingSearch));
    }

    @Test
    public void filterDateTest(){
        Meeting meetingSearch = service.getMeeting().get(0);
        List<Meeting> result = service.filterMeeting(
                meetingSearch.getDate(),
                null,
                null);
        assertTrue(result.size() == 1 && result.contains(meetingSearch));
    }

    @Test
    public void filterAllTest(){
        Meeting meetingSearch = service.getMeeting().get(0);
        List<Meeting> result = service.filterMeeting(
                meetingSearch.getDate(),
                null,
                meetingSearch.getLocation().getRoom());
        assertTrue(result.size() == 1 && result.contains(meetingSearch));
    }

    @Test
    public void getRoomsTest(){
        List<Room> rooms = service.getRooms();
        List<Room> expectedRooms = DUMMY_ROOM;

        assertTrue(expectedRooms.size() == rooms.size() && rooms.containsAll(expectedRooms));
    }
}
