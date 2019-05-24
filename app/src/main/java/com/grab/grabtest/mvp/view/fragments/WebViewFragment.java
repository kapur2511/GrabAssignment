/*
 * Copyright (C) 24-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

package com.grab.grabtest.mvp.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.grab.grabtest.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.grab.grabtest.utils.Constants.IMAGE_URL;
import static com.grab.grabtest.utils.Constants.NEWS_URL;

public class WebViewFragment extends Fragment {

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        imageURL = args.getString(IMAGE_URL);
        newsURL  = args.getString(NEWS_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("LifeCycle","onCreateView");
        View fragmentView = inflater.inflate(R.layout.fragment_webview, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCachePath(getContext().getCacheDir().getPath());
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.loadUrl(newsURL);
        if(!imageURL.isEmpty())
            Picasso.get().load(imageURL).fit().into(imageViewCover);
        return fragmentView;
    }

    private String imageURL, newsURL;
    private Unbinder unbinder;

    @BindView(R.id.img_news_detail)
    ImageView imageViewCover;

    @BindView(R.id.web_view)
    WebView webView;
}
