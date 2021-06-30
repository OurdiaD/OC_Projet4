package com.lamzone.mareu;

import android.widget.DatePicker;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.service.meeting.MeetingApiService;
import com.lamzone.mareu.ui.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
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

    @Test
    public void filter_date(){
        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.filter_date)).perform(click());
        onView(withId(R.id.mtrl_picker_header_toggle)).perform(click());
        String today = new SimpleDateFormat("M/d/yy").format(System.currentTimeMillis());
        onView(withText(today)).perform(replaceText("07/23/2021"));
        onView(withText("07/23/2021")).perform(closeSoftKeyboard());
        /*onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(2021, 7, 23));*/
        /*onView(withId(R.id.date_picker_actions))
                .perform(PickerActions.setDate(2021, 7, 23));*/
        onView(withText("OK")).perform(click());
        onView(ViewMatchers.withId(R.id.meeting_list))
                .check(matches(hasChildCount(2)));
        onView(allOf(withId(R.id.meeting), withText(containsString("07/23/2021")),
                withParent(withParent(withId(R.id.meeting_list))),
                isDisplayed()));
    }

    @Test
    public void filter_room(){
        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.filter_room)).perform(click());
        onView(withText("Reunion A"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        onView(ViewMatchers.withId(R.id.meeting_list))
                .check(matches(hasChildCount(2)));
        onView(allOf(withId(R.id.meeting), withText(containsString("Reunion A")),
                withParent(withParent(withId(R.id.meeting_list))),
                isDisplayed()));
    }

    public void filter_all(){

    }
}

