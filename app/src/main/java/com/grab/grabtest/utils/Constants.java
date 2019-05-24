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

package com.grab.grabtest.utils;

public class Constants {

    public static final String API_KEY              = "a01bad23a8de49028eb4c1227bdc3513";
    public static final String BASE_URL             = "https://newsapi.org/v2/";
    public static final String PREF_CURRENT_TIME    = "pref.current.data";
    public static final String PREF_CACHE_DATA      = "pref.cache.data";
    public static final String PREF_CURRENT_PAGE    = "pref.current.page";
    public static final String PREF_NEXT_PAGE_TOKEN = "pref.next.page.token";
    public static final String PREF_TOTAL_PAGES     = "pref.total.pages";
    public static final int    RESPONSE_PER_PAGE    = 20;

    public static final int    SHOULD_USE_CACHE     = 100;
    public static final int    SHOULD_NOT_USE_CACHE = 101;
    public static final int    NO_CACHE_AND_INTERNET= 102;
    public static final int    ERROR                = 103;
}
