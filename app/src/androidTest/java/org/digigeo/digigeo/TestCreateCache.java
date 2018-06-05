package org.digigeo.digigeo;

import android.support.test.rule.ActivityTestRule;

import org.digigeo.digigeo.Entity.Cache;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.not;


public class TestCreateCache {

    @Rule
    public ActivityTestRule<main> activityTestRule = new ActivityTestRule<>(main.class);

    @Test
    public void createCache() {
        main activity = activityTestRule.getActivity();

        onView(withText(R.string.create))
                .perform(click());

        onView(withId(R.id.editCacheName))
                .perform(typeText("Test Cache Name"));

        onView(withId(R.id.editCacheContent))
                .perform(typeText("This is the text that is going to be" +
                        "revealed from opening a cache."));

        closeSoftKeyboard();

        onView(withId(R.id.submitBtn))
                .perform(click());

        onView(withText(R.string.cache_created)).
                inRoot(withDecorView(not(activity.getWindow().getDecorView()))).
                check(matches(isDisplayed()));
    }

    @Test
    public void alertDialogueTest() {
        onView(withText(R.string.create))
                .perform(click());

        onView(withId(R.id.submitBtn))
                .perform(click());

        onView(withText("Can't create an empty cache\nFill out the form and try again"))
                .check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
    }

    @Test
    public void testDescContents() {
        Cache testCache = new Cache();
        assertEquals(0, testCache.describeContents());
    }

}
