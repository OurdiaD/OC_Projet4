package com.lamzone.mareu;

import android.icu.util.ULocale;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class ListMeetingTest {

    private static int itemCount;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void setUp() {
        MeetingApiService service =  DI.getNewInstanceApiService();
        List<Meeting> meetings = service.getMeeting();
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
        onView(withId(R.id.add_meeting_view)).check(matches(isDisplayed()));
    }

    @Test
    public void filter_date(){
        onView(withId(R.id.filter)).perform(click());
        selectdate();
        onView(ViewMatchers.withId(R.id.meeting_list))
                .check(matches(hasChildCount(2)));
        onView(allOf(withId(R.id.meeting), withText(containsString("23/07/2021")),
                withParent(withParent(withId(R.id.meeting_list))),
                isDisplayed()));
    }

    @Test
    public void filter_room(){
        onView(withId(R.id.filter)).perform(click());
        selectroom();
        onView(ViewMatchers.withId(R.id.meeting_list))
                .check(matches(hasChildCount(2)));
        onView(allOf(withId(R.id.meeting), withText(containsString("Reunion A")),
                withParent(withParent(withId(R.id.meeting_list))),
                isDisplayed()));
    }

    @Test
    public void filter_all(){
        onView(withId(R.id.filter)).perform(click());
        selectroom();
        selectdate();
        onView(ViewMatchers.withId(R.id.meeting_list))
                .check(matches(hasChildCount(1)));
        onView(allOf(withId(R.id.meeting), withText(containsString("Reunion A - 23/07/2021")),
                withParent(withParent(withId(R.id.meeting_list))),
                isDisplayed()));
    }

    public void selectdate(){
        onView(withId(R.id.filter_date)).perform(click());
        onView(withId(R.id.mtrl_picker_header_toggle)).perform(click());

        String language = ApplicationProvider.getApplicationContext().getResources().getConfiguration().getLocales().get(0).getLanguage();
        String patern;
        String text;
        if (language.equals("fr")){
            patern = "dd/MM/yyyy";
            text ="23/07/2021";
        } else {
            patern = "M/d/yy";
            text ="07/23/2021";
        }

        String today = new SimpleDateFormat(patern).format(System.currentTimeMillis());
        onView(withText(today)).perform(replaceText(text));
        onView(withText(text)).perform(closeSoftKeyboard());
        onView(withText("OK")).perform(click());
    }

    public void selectroom(){
        onView(withId(R.id.filter_room)).perform(click());
        onView(withText("Reunion A"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
    }
}

