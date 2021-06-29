package com.lamzone.mareu;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.service.meeting.MeetingApiService;
import com.lamzone.mareu.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

import androidx.test.espresso.contrib.RecyclerViewActions;

@RunWith(AndroidJUnit4.class)
public class ListMeetingTest {

    private List<Meeting> meetings;
    private static int itemCount;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void setUp() {
        MeetingApiService service =  DI.getNewInstanceApiService();
        meetings = service.getMeeting();
        itemCount = meetings.size();
    }

    @Test
    public void meetingsList_notEmpty(){
        onView(ViewMatchers.withId(R.id.meeting_list))
                .check(matches(hasMinimumChildCount(itemCount)));
    }

    @Test
    public void meetingsList_checkData(){
       onView(allOf(withId(R.id.meeting), withText("Reunion A - 18/05/2021 - 14:00 - Peach"),
                withParent(withParent(withId(R.id.meeting_list))),
                isDisplayed()));
    }

    @Test
    public void meetingsList_deleteItem(){
        onView(withId(R.id.meeting_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, RecyclerViewItemsActions.clickChildViewWithId(R.id.delete)));

        onView(ViewMatchers.withId(R.id.meeting_list))
                .check(matches(hasChildCount(itemCount -1)));
    }

    @Test
    public void clickAdd_openActivity(){
        onView(withId(R.id.add_meeting)).perform(click());
        onView(withId(R.id.save_meeting)).check(matches(isDisplayed()));
    }
}

