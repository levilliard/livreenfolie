package com.mbds.livreenfolie.model;

import androidx.annotation.NonNull;

public class Livre implements Comparable<Livre> {
    private String uri;
    private String lang;
    private boolean isDuplicate;
    private String date;
    private String time;
    private String dateTime;
    private String url;
    private String title;
    private String sourceUri;
    private String body;
    private String image;
    private String altDateTime;
    private String summarized;
    private String categories;

    public String getAltCategories() {
        return altCategories;
    }

    public void setAltCategories(String altCategories) {
        this.altCategories = altCategories;
    }

    private String altCategories;

    public String getSummarized() {
        return summarized;
    }

    public void setSummarized(String summarized) {
        this.summarized = summarized;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getAltDateTime() {
        return altDateTime;
    }

    public void setAltDateTime(String altDateTime) {
        this.altDateTime = altDateTime;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceUri() {
        return sourceUri;
    }

    public void setSourceUri(String sourceUri) {
        this.sourceUri = sourceUri;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int compareTo(@NonNull Livre obj2) {
        return obj2.dateTime.compareToIgnoreCase(this.dateTime);
    }
}
