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

package com.grab.grabtest.di.modules;

import com.grab.grabtest.mvp.view.fragments.NewsListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NewsListActivityModule {

    @ContributesAndroidInjector()
    abstract NewsListFragment newsListFragment();
}
