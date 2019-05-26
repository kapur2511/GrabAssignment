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

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    public static SharedPreferences provideSharedPreferences(Context context){
        return context.getSharedPreferences("Grab",Context.MODE_PRIVATE);
    }

}
