package org.digigeo.digigeo;
import android.Manifest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class Testing_Map {

    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);
    @Rule public GrantPermissionRule permissionRuleCourse = GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION);
    @Rule public ActivityTestRule<main> activityTestRule = new ActivityTestRule<>(main.class);

    //checking if the map is displayed
    @Test
    public void checkMapLoaded() throws InterruptedException{
        onView(withText(R.string.map))
                .perform(click());
        Thread.sleep(3000); //wait for map to load
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    //test navigating to the cache list fragment
    @Test
    public void canGoToCacheList() throws InterruptedException{
        Thread.sleep(3000); //wait for map to load
        onView(withText(R.string.list)).perform(click());
        onView(withText(R.string.map)).perform(click());
    }


    @Test
    public void canCreateCacheAndFindOnMap() throws InterruptedException, UiObjectNotFoundException{
        onView(withText(R.string.map)).perform(click());
        Thread.sleep(3000); //wait for map to load
        onView(withText(R.string.create)).perform(click());
        onView(withId(R.id.editCacheName)).perform(replaceText("EspressoTest")).perform(closeSoftKeyboard());
        onView(withId(R.id.editCacheContent)).perform(replaceText("Testing Espresso Content")).perform(closeSoftKeyboard());
        onView(withId(R.id.submitBtn)).perform(click());
        onView(withText(R.string.map)).perform(click());
        Thread.sleep(3000); //wait for map to load
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("EspressoTest"));
        marker.click();
    }

}
