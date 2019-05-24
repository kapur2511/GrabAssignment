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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class ContentScreenEspressoTest {

    @Rule
    public ActivityTestRule<NewsListActivity> rule = new ActivityTestRule(NewsListActivity.class);

    @Test
    public void contentUIEnabled(){
        onView(withChild(withId(R.id.ll_content))).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void recyclerViewUIEnabled(){
        onView(withChild(withId(R.id.rv_list)))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(Matchers.allOf(withId(R.id.rv_list), withParent(withId(R.id.frame_layout))))
                .check(new RecyclerViewItemCountAssertion(0));
    }
}
