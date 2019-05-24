/*
 * Copyright (C) 23-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

package com.grab.grabtest.mvp.view.viewmodel;

import java.util.List;

public class NewsListViewModel {

    private int totalResult;
    private List<NewsViewModel> newsViewModels;

    public void setNewsViewModels(List<NewsViewModel> newsViewModels) {
        this.newsViewModels = newsViewModels;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public List<NewsViewModel> getNewsViewModels() {
        return newsViewModels;
    }

    @Override
    public String toString() {
        return "NewsListViewModel{" +
                "totalResult=" + totalResult +
                ", newsViewModels=" + newsViewModels +
                '}';
    }
}
