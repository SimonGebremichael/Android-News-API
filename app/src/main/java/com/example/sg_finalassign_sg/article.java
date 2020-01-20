package com.example.sg_finalassign_sg;

public class article {

    private String title;
    private String author;
    private String publishDate;
    private String Descriptiong;
    private String Link;
    private String Url;
    private String content;
    private String source;
    private String id;
    private String name;

    public article(String title, String author, String publishDate, String Descriptionn, String Link, String Url, String content, String source){
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.Descriptiong = Descriptionn;
        this.Link = Link;
        this.Url = Url;
        this.content = content;
        this.source = source; }

    public article(){}

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getDescriptiong() {
        return Descriptiong;
    }

    public String getLink() {
        return Link;
    }

    public String getUrl() {
        return Url;
    }

    public String getContent() {
        return content;
    }

    public String getSource() {
        return source;
    }
}
