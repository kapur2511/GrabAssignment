/*
 * Copyright (C) 23-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

package com.grab.grabtest.mvp.model;

import java.util.List;

public class NewsListModel {

    private int totalResults;

    private List<ArticlesModel> articles;

    private String status;

    public int getTotalResults ()
    {
        return totalResults;
    }

    public void setTotalResults (int totalResults)
    {
        this.totalResults = totalResults;
    }

    public List<ArticlesModel> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesModel> articles) {
        this.articles = articles;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "NewsListModel [totalResults = "+totalResults+", articles = "+articles+", status = "+status+"]";
    }
}
