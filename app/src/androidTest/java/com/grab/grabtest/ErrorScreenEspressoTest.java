/*
 * Copyright (C) 24-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

/*
 * @author: kshitiz.kapur
 */

package com.grab.grabtest;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.grab.grabtest.mvp.view.activities.NewsListActivity;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static com.grab.grabtest.utils.Constants.BASE_URL;

@RunWith(AndroidJUnit4.class)
public class ErrorScreenEspressoTest {

    //Before running this test please clear the data or do a fresh install as no api call will be made on old build(if you're reopening the app within 5 minutes) and these tests will fail because data will be loaded from cache.
    @Rule
    public ActivityTestRule<NewsListActivity> rule = new ActivityTestRule(NewsListActivity.class);

    @BeforeClass
    public static void setWrongBaseUrl(){
        BASE_URL = BASE_URL.replace("api","dd");
    }

    @Test
    public void errorUIEnabled(){
        onView(withChild(withId(R.id.cl_error))).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void clickRetry(){
        onView(withChild(withId(R.id.btn_error))).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(Matchers.allOf(withParent(withId(R.id.cl_error)), withId(R.id.btn_error))).perform(click());
        onView(withChild(withId(R.id.cl_content))).
                check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
