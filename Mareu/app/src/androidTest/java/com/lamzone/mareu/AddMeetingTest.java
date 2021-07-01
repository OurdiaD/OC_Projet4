package com.lamzone.mareu;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.android.material.chip.Chip;
import com.lamzone.mareu.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;


public class AddMeetingTest {
    String today;
    @Rule
    public ActivityScenarioRule<MainActivity> activitySenario
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        onView(withId(R.id.add_meeting)).perform(click());
        onView(withId(R.id.add_meeting_view)).check(matches(isDisplayed()));
    }

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

        onView(withId(R.id.participantsSelected)).perform(typeText("laura@lamzone.com "));

        onView(withId(R.id.recipient_group_FL))
                .check(matches(hasChildCount(2)))
                .check(matches(withChild(withText("maxime@lamzone.com"))))
                .check(matches(withChild(withText("laura@lamzone.com"))));
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
        date_added();
        room_added();
        onView(withId(R.id.subject)).perform(typeText("Arthas"));
        users_added();
        onView(withId(R.id.subject)).perform(closeSoftKeyboard());
        onView(withId(R.id.save_meeting)).perform(click());
        onView(withId(R.id.meeting_list)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.meeting_list))
                .check(matches(hasChildCount(5)));
        onView(allOf(withId(R.id.meeting), withText("Reunion A - "+today+" - 10:10 - Arthas"),
                withParent(withParent(withId(R.id.meeting_list))),
                isDisplayed()));
        onView(allOf(withId(R.id.meeting), withText("maxime@lamzone.com, laura@lamzone.com"),
                withParent(withParent(withId(R.id.meeting_list))),
                isDisplayed()));
    }

    @Test
    public void back_home(){
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        onView(ViewMatchers.withId(R.id.meeting_list))
                .check(matches(isDisplayed()));
    }
}
