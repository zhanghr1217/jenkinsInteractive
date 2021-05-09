package com.zut.interactivetools.bean;

public class TextCardInfoBean {
    private String title;
    private String description;
    private String url;
    private String btntxt;

    public TextCardInfoBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBtntxt() {
        return btntxt;
    }

    public void setBtntxt(String btntxt) {
        this.btntxt = btntxt;
    }

    @Override
    public String toString() {
        return "TextCardInfoBean{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", btntxt='" + btntxt + '\'' +
                '}';
    }
}
