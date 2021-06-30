package com.lamzone.mareu;

import android.widget.DatePicker;

import com.google.android.material.datepicker.MaterialDatePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;


import com.lamzone.mareu.model.User;
import com.lamzone.mareu.ui.MainActivity;
import com.lamzone.mareu.ui.list_meeting.AddMeetingActivity;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.CursorMatchers.withRowString;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


public class AddMeetingTest {
    String today;
    @Rule
    public ActivityScenarioRule<AddMeetingActivity> activitySenario
            = new ActivityScenarioRule<>(AddMeetingActivity.class);

    @Test
    public void date_added(){
        onView(withId(R.id.date_meeting)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withText("OK")).perform(click());

        today = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());
        onView(withId(R.id.date)).check(matches(withText(today + " 10:10")));
    }

    @Test
    public void users_added(){
        onView(withId(R.id.participants_meeting)).perform(click());
        onView(withText("maxime@lamzone.com"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        /*onView(withId(R.id.participants_meeting)).perform(click());
        onView(withSpinnerText("alex@lamzone.com"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());*/


        onView(withId(R.id.participantsSelected)).check(matches(withText(containsString("maxime@lamzone.com"))));
        //onView(withId(R.id.participantsSelected)).check(matches(withText(containsString("alex@lamzone.com"))));
        /*try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


    }

    @Test
    public void room_added(){
        onView(withId(R.id.room_meeting)).perform(click());
        onView(withText("Reunion A"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        onView(withId(R.id.room_list)).check(matches(withText("Reunion A")));

    }

    @Test
    public void newMeeting(){
        room_added();
        users_added();
        onView(withId(R.id.subject)).perform(typeText("Arthas"));
        date_added();
        onView(withId(R.id.save_meeting)).perform(click());
        onView(withId(R.id.meeting_list)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.meeting_list))
                .check(matches(hasChildCount(4)));
        onView(allOf(withId(R.id.meeting), withText("Reunion A - "+today+" - 10:10 - Arthas"),
                withParent(withParent(withId(R.id.meeting_list))),
                isDisplayed()));
    }
}
