package com.lamzone.mareu;

import android.widget.DatePicker;

import com.google.android.material.datepicker.MaterialDatePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.CursorMatchers.withRowString;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
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

    @Rule
    public ActivityScenarioRule<AddMeetingActivity> activitySenario
            = new ActivityScenarioRule<>(AddMeetingActivity.class);
    public ActivityTestRule<AddMeetingActivity> mActivityRule =
            new ActivityTestRule<>(AddMeetingActivity.class);

    @Test
    public void date_added(){
        onView(withId(R.id.date_meeting)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withText("OK")).perform(click());

        String today = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());
        onView(withId(R.id.date)).check(matches(withText(today + " 10:10")));
    }

    @Test
    public void users_added(){
        onView(withId(R.id.participants_meeting)).perform(click());

        /*try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        onView(withText("maxime@lamzone.com"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

       /* onView(withId(R.id.participants_meeting)).perform(click());
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("alex@lamzone.com"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());*/

        onView(withId(R.id.participantsSelected)).check(matches(withText(containsString("maxime@lamzone.com"))));
        /*onView(withId(R.id.participants_meeting)).check(matches(withText(containsString("alex@lamzone.com"))));*/
        //getInstrumentation().getUiAutomation().executeShellCommand("input tap " + 480 + " " + Math.round(720 / 4f));
        /*onView(withText(LOCATION_TO_BE_SEARCHED)) .inRoot(withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView()))) .check(matches(isDisplayed())) .perform(click());*/
        //onView(allOf(withText("maxime@lamzone.com"))).perform(click());
        //onData(withRowString(DB.COLUMN_FIRSTNAME, "Ivan")).perform(click());
        //onData(equalTo("maxime@lamzone.com")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        //onData(instanceOf(User.class)).onChildView(withSpinnerText("maxime@lamzone.com")).perform(click());
        //onView(withSpinnerText("maxime@lamzone.com")).perform(click());
        //onView(withText("maxime@lamzone.com")).perform(click());
        /*onData(instanceOf(User.class))
        .inRoot(RootMatchers.withDecorView(not(is(activityRule.getScenario().onActivity().getActivity().getWindow().getDecorView()))))
                .perform(ViewActions.click());*/
        //onData(withText("maxime@lamzone.com")).perform(click());
        //onView(withText("maxime@lamzone.com")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).perform(click());
        //onData(anything()).atPosition(1).perform(click());
        /*onData(allOf(is(instanceOf(User.class)),
                withText("maxime@lamzone.com")))
                .inRoot(isPlatformPopup())
                .perform(click());*/
        //onView(withId(R.id.participants_meeting)).perform(click());
        //onView(withText("alex@lamzone.com")).perform(click());
        /*onData(allOf(is(instanceOf(String.class)), withText("alex@lamzone.com"))).perform(click());
        onView(withId(R.id.participants_meeting)).check(matches(withText(containsString("maxime@lamzone.com"))));
        onView(withId(R.id.participants_meeting)).check(matches(withText(containsString("alex@lamzone.com"))));*/
        //onView(withId(R.id.participants_meeting)).check(matches(withText(containsString("maxime@lamzone.com"))));
        //onData(withText("maxime@lamzone.com")).check(matches(withText(containsString("maxime@lamzone.com"))));
        /*onView(allOf(withId(R.id.item_dropdown),isDisplayed()))
                .perform(click());*/
        /*onData(anything())
                .inAdapterView(isDescendantOfA(withId(R.id.participantsSelected)))
                .atPosition(1)
                .perform(click());*/
        /*onData(instanceOf(User.class))
                .inRoot(RootMatchers.withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .perform(ViewActions.click());

        ViewInteraction appCompatTextView = onView(withText("kits"))
                .inRoot(
                        RootMatchers.withDecorView(
                                not(mActivityRule.getActivity().getWindow().getDecorView())
                        )
                ).check(matches(isDisplayed()));
        appCompatTextView.perform(click());*/
    }

    public void room_added(){
        onView(withId(R.id.room_meeting)).perform(click());
    }
}
