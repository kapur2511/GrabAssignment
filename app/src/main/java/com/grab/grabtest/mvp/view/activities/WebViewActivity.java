/*
 * Copyright (C) 24-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

package com.grab.grabtest.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.grab.grabtest.mvp.view.fragments.WebViewFragment;

import static com.grab.grabtest.utils.Constants.IMAGE_URL;
import static com.grab.grabtest.utils.Constants.NEWS_URL;

public class WebViewActivity extends BaseActivity {


    @Override
    protected Fragment viewFragment() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_URL, intent.getStringExtra(IMAGE_URL));
        bundle.putString(NEWS_URL, intent.getStringExtra(NEWS_URL));
        return buildFragment(getIntent().getExtras(), WebViewFragment.class);
    }
}
