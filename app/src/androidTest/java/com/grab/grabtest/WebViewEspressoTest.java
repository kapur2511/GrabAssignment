/*
 * Copyright (C) 24-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

package com.grab.grabtest;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.grab.grabtest.mvp.view.activities.NewsListActivity;
import com.grab.grabtest.mvp.view.activities.WebViewActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class WebViewEspressoTest {

    @Rule
    public ActivityTestRule<WebViewActivity> rule = new ActivityTestRule(WebViewActivity.class);

    @Test
    public void webViewVisible(){
        onView(withChild(withId(R.id.web_view))).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void coverImageVisible(){
        onView(withChild(withId(R.id.img_news_detail))).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
