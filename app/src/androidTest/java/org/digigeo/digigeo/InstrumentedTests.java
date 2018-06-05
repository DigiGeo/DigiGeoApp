package org.digigeo.digigeo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.digigeo.digigeo.TestUtils.waitFor;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTests {
    @Rule
    public ActivityTestRule<main> menuActivityTestRule =
            new ActivityTestRule<>(main.class, true, true);

    @Test
    public void moveThroughTabs() {
        onView(withText("List")).perform(click());
        onView(isRoot()).perform(waitFor(1000));
        onView(withText("Create")).perform(click());
        onView(isRoot()).perform(waitFor(1000));
        onView(withText("Map")).perform(click());
        onView(isRoot()).perform(waitFor(1000));
    }

    @Test
    public void clickOpen() {
        onView(withText("List")).perform(click());
        onView(withId(R.id.my_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, TestUtils.clickChildViewWithId(R.id.openCache)));
    }
}
