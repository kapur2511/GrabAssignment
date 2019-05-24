/*
 * Copyright (C) 23-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

package com.grab.grabtest;

import android.app.Activity;
import android.app.Application;

import com.grab.grabtest.di.component.ApplicationComponent;
import com.grab.grabtest.di.component.DaggerApplicationComponent;

import java.io.File;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class GrabApplication extends Application implements HasActivityInjector {


    @Override
    public void onCreate() {
        super.onCreate();
        File cacheFile = new File(getCacheDir(), "responses");
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .cache(cacheFile).application(this)
                .build();
        applicationComponent.inject(this);
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;
}
