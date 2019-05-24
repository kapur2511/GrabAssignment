/*
 * Copyright (C) 23-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

package com.grab.grabtest.mvp.view.viewmodel;

import com.grab.grabtest.mvp.model.ArticlesModel;

public class NewsViewModel implements ListItem{

    private String publishedAt;
    private String author;
    private String urlToImage;
    private String description;
    private String title;
    private String articleUrl;

    public NewsViewModel(ArticlesModel articlesModel){
        this.publishedAt = articlesModel.getPublishedAt();
        this.author      = articlesModel.getAuthor();
        this.urlToImage  = articlesModel.getUrlToImage();
        this.description = articlesModel.getDescription();
        this.title       = articlesModel.getTitle();
        this.articleUrl  = articlesModel.getUrl();
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    @Override
    public String toString() {
        return "NewsViewModel{" +
                "publishedAt='" + publishedAt + '\'' +
                ", author='" + author + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", articleUrl='" + articleUrl + '\'' +
                '}';
    }
}
