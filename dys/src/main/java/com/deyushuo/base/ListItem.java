package com.deyushuo.base;

public class ListItem {
    public String titel;
    public String date;
    public String imageLink;
    public String link;

    public ListItem(String titel,String date, String imageLink,String link) {
        this.imageLink = imageLink;
        this.date = date;
        this.titel = titel;
        this.link = link;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getDate() {
        return date;
    }

    public String getTitel() {
        return titel;
    }

    public String getLink() { return link; }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setLink (String link) { this.link = link; }
}
