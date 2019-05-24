/*
 * Copyright (C) 23-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

/*
 * @author: kshitiz.kapur
 */

package com.grab.grabtest.mvp.view.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.grab.grabtest.mvp.view.fragments.NewsListFragment;


public class NewsListActivity extends BaseActivity {

    @Override
    protected Fragment viewFragment() {
        Bundle bundle = new Bundle();
        return buildFragment(bundle, NewsListFragment.class);
    }
}
