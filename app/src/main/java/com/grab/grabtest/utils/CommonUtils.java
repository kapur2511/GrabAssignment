/*
 * Copyright (C) 24-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

package com.grab.grabtest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.grab.grabtest.mvp.view.viewmodel.ListItem;

import java.util.List;

import static com.grab.grabtest.utils.Constants.ERROR;
import static com.grab.grabtest.utils.Constants.NO_CACHE_AND_INTERNET;
import static com.grab.grabtest.utils.Constants.SHOULD_NOT_USE_CACHE;
import static com.grab.grabtest.utils.Constants.SHOULD_USE_CACHE;

public class CommonUtils {

    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static int shouldUseCache(long lastFetchTime, List<ListItem> cacheData, boolean isConnected){
        if(isConnected && ((System.currentTimeMillis() - lastFetchTime) > 300000))
            return SHOULD_NOT_USE_CACHE;
        else if(isConnected && ((System.currentTimeMillis() - lastFetchTime) < 300000) && cacheData!=null && cacheData.size()>0)
            return SHOULD_USE_CACHE;
        else if(isConnected && ((System.currentTimeMillis() - lastFetchTime) < 300000) && (cacheData == null || cacheData.size() == 0))
            return SHOULD_NOT_USE_CACHE;
        else if(!isConnected && cacheData!=null && cacheData.size()>0)
            return SHOULD_USE_CACHE;
        else if(!isConnected)
            return NO_CACHE_AND_INTERNET;
        else
            return ERROR;
    }

    private static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

}
